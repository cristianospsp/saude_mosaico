package com.saudemosaico.agendamento.service;

import com.saudemosaico.agendamento.domain.Agendamento;
import com.saudemosaico.agendamento.domain.StatusAgendamento;
import com.saudemosaico.agendamento.dto.AgendamentoRequest;
import com.saudemosaico.agendamento.dto.AgendamentoResponse;
import com.saudemosaico.agendamento.exception.AgendamentoException;
import com.saudemosaico.agendamento.exception.AgendamentoNotFoundException;
import com.saudemosaico.agendamento.repository.AgendamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
        agendamento.setLinkVideoConferencia(gerarLinkVideoconferencia());
        
        return converterParaResponse(agendamentoRepository.save(agendamento));
    }
    
    @Override
    @Transactional(readOnly = true)
    public AgendamentoResponse buscarPorId(Long id) {
        return agendamentoRepository.findById(id)
            .map(this::converterParaResponse)
            .orElseThrow(() -> new AgendamentoNotFoundException(id));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AgendamentoResponse> listarTodos() {
        return agendamentoRepository.findAll().stream()
            .map(this::converterParaResponse)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AgendamentoResponse> listarPorPaciente(String pacienteId) {
        return agendamentoRepository.findByPacienteId(pacienteId).stream()
            .map(this::converterParaResponse)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AgendamentoResponse> listarPorEspecialista(String especialistaId) {
        return agendamentoRepository.findByEspecialistaId(especialistaId).stream()
            .map(this::converterParaResponse)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public AgendamentoResponse atualizar(Long id, AgendamentoRequest request) {
        Agendamento agendamento = agendamentoRepository.findById(id)
            .orElseThrow(() -> new AgendamentoNotFoundException(id));
            
        if (!agendamento.getDataHoraAgendamento().equals(request.getDataHoraAgendamento())) {
            validarDisponibilidade(request);
        }
        
        agendamento.setEspecialistaId(request.getEspecialistaId());
        agendamento.setEspecialidade(request.getEspecialidade());
        agendamento.setDataHoraAgendamento(request.getDataHoraAgendamento());
        
        return converterParaResponse(agendamentoRepository.save(agendamento));
    }
    
    @Override
    @Transactional
    public void deletar(Long id) {
        if (!agendamentoRepository.existsById(id)) {
            throw new AgendamentoNotFoundException(id);
        }
        agendamentoRepository.deleteById(id);
    }
    
    private void validarDisponibilidade(AgendamentoRequest request) {
        if (agendamentoRepository.existsByEspecialistaIdAndDataHoraAgendamento(
                request.getEspecialistaId(),
                request.getDataHoraAgendamento())) {
            throw new AgendamentoException("Horário indisponível para o especialista selecionado");
        }
    }
    
    private String gerarLinkVideoconferencia() {
        return "https://meet.saude-mosaico.com/" + System.currentTimeMillis();
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
}
