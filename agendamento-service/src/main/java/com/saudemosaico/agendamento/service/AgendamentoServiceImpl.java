package com.saudemosaico.agendamento.service;

import com.saudemosaico.agendamento.domain.Agendamento;
import com.saudemosaico.agendamento.domain.StatusAgendamento;
import com.saudemosaico.agendamento.dto.AgendamentoRequest;
import com.saudemosaico.agendamento.dto.AgendamentoResponse;
import com.saudemosaico.agendamento.exception.AgendamentoException;
import com.saudemosaico.agendamento.repository.AgendamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgendamentoServiceImpl implements AgendamentoService {
    
    private final AgendamentoRepository agendamentoRepository;
    
    @Override
    @Transactional
    public AgendamentoResponse criar(AgendamentoRequest request) {
        validarDisponibilidade(request);
        
        Agendamento agendamento = new Agendamento();
        agendamento.setPacienteId(request.getPacienteId());
        agendamento.setEspecialistaId(request.getEspecialistaId());
        agendamento.setEspecialidade(request.getEspecialidade());
        agendamento.setDataHoraAgendamento(request.getDataHoraAgendamento());
        agendamento.setStatus(StatusAgendamento.AGUARDANDO_CONFIRMACAO);
        
        // TODO: Implementar geração do link de videoconferência
        agendamento.setLinkVideoConferencia("https://meet.saude-mosaico.com/" + System.currentTimeMillis());
        
        return converterParaResponse(agendamentoRepository.save(agendamento));
    }
    
    private void validarDisponibilidade(AgendamentoRequest request) {
        if (agendamentoRepository.existsByEspecialistaIdAndDataHoraAgendamento(
                request.getEspecialistaId(),
                request.getDataHoraAgendamento())) {
            throw new AgendamentoException("Horário indisponível para o especialista selecionado");
        }
    }
    
    private AgendamentoResponse converterParaResponse(Agendamento agendamento) {
        AgendamentoResponse response = new AgendamentoResponse();
        response.setId(agendamento.getId());
        response.setPacienteId(agendamento.getPacienteId());
        response.setEspecialistaId(agendamento.getEspecialistaId());
        response.setEspecialidade(agendamento.getEspecialidade());
        response.setDataHoraAgendamento(agendamento.getDataHoraAgendamento());
        response.setStatus(agendamento.getStatus());
        response.setLinkVideoConferencia(agendamento.getLinkVideoConferencia());
        response.setCriadoEm(agendamento.getCriadoEm());
        response.setAtualizadoEm(agendamento.getAtualizadoEm());
        return response;
    }
    
    // Implementar os demais métodos da interface...
}
