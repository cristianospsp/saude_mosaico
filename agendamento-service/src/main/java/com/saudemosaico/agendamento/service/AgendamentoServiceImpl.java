package com.saudemosaico.agendamento.service;

import com.saudemosaico.agendamento.client.EspecialistaClient;
import com.saudemosaico.agendamento.domain.Agendamento;
import com.saudemosaico.agendamento.domain.Especialidade;
import com.saudemosaico.agendamento.domain.StatusAgendamento;
import com.saudemosaico.agendamento.dto.AgendamentoRequest;
import com.saudemosaico.agendamento.dto.AgendamentoResponse;
import com.saudemosaico.agendamento.dto.EspecialistaResponse;
import com.saudemosaico.agendamento.exception.AgendamentoException;
import com.saudemosaico.agendamento.exception.AgendamentoNotFoundException;
import com.saudemosaico.agendamento.repository.AgendamentoRepository;
import com.saudemosaico.agendamento.security.SanitizadorUtil;
import com.saudemosaico.agendamento.util.ValidadorAgendamentoUtil;
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
    private final EspecialistaClient especialistaClient;
    private final FeriadoService feriadoService;
    private final SanitizadorUtil sanitizadorUtil;

    @Override
    @Transactional
    public AgendamentoResponse criar(AgendamentoRequest request) {
        // Sanitizar inputs
        request.setPacienteId(sanitizadorUtil.sanitizarInput(request.getPacienteId()));
        request.setEspecialistaId(sanitizadorUtil.sanitizarInput(request.getEspecialistaId()));

        // Validar se especialista existe e está ativo
        EspecialistaResponse especialista = especialistaClient.buscarPorId(Long.valueOf(request.getEspecialistaId()))
                .orElseThrow(() -> new AgendamentoException("Especialista não encontrado"));

        if (!especialista.isAtivo()) {
            throw new AgendamentoException("Especialista não está ativo");
        }

        // Validar se especialista possui a especialidade solicitada
        if (!especialista.getEspecialidades().contains(request.getEspecialidade())) {
            throw new AgendamentoException("Especialista não atende a especialidade solicitada");
        }

        // Validar regras de agendamento
        validarAgendamento(request);

        Agendamento agendamento = new Agendamento();
        agendamento.setPacienteId(request.getPacienteId());
        agendamento.setEspecialistaId(request.getEspecialistaId());
        agendamento.setEspecialidade(String.valueOf(request.getEspecialidade()));
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
        String pacienteIdSanitizado = sanitizadorUtil.sanitizarInput(pacienteId);
        return agendamentoRepository.findByPacienteId(pacienteIdSanitizado).stream()
                .map(this::converterParaResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgendamentoResponse> listarPorEspecialista(String especialistaId) {
        String especialistaIdSanitizado = sanitizadorUtil.sanitizarInput(especialistaId);
        return agendamentoRepository.findByEspecialistaId(especialistaIdSanitizado).stream()
                .map(this::converterParaResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AgendamentoResponse atualizar(Long id, AgendamentoRequest request) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new AgendamentoNotFoundException(id));

        // Sanitizar inputs
        request.setPacienteId(sanitizadorUtil.sanitizarInput(request.getPacienteId()));
        request.setEspecialistaId(sanitizadorUtil.sanitizarInput(request.getEspecialistaId()));

        // Validar especialista novamente se houve mudança
        if (!agendamento.getEspecialistaId().equals(request.getEspecialistaId())) {
            EspecialistaResponse especialista = especialistaClient.buscarPorId(Long.valueOf(request.getEspecialistaId()))
                    .orElseThrow(() -> new AgendamentoException("Especialista não encontrado"));

            if (!especialista.isAtivo()) {
                throw new AgendamentoException("Especialista não está ativo");
            }

            if (!especialista.getEspecialidades().contains(request.getEspecialidade())) {
                throw new AgendamentoException("Especialista não atende a especialidade solicitada");
            }
        }

        if (!agendamento.getDataHoraAgendamento().equals(request.getDataHoraAgendamento())) {
            validarAgendamento(request);
        }

        agendamento.setEspecialistaId(request.getEspecialistaId());
        agendamento.setEspecialidade(String.valueOf(request.getEspecialidade()));
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

    private void validarAgendamento(AgendamentoRequest request) {
        // Validação de disponibilidade
        validarDisponibilidade(request);

        // Validações de horário e dia
        ValidadorAgendamentoUtil.validarHorarioComercial(request.getDataHoraAgendamento());
        ValidadorAgendamentoUtil.validarDiaUtil(request.getDataHoraAgendamento());
        ValidadorAgendamentoUtil.validarAntecedenciaMinima(request.getDataHoraAgendamento());

        // Validar feriados
        if (feriadoService.isFeriado(request.getDataHoraAgendamento().toLocalDate())) {
            throw new AgendamentoException("Não é permitido agendar em feriados");
        }

        // Validar intervalo entre consultas
        LocalDateTime ultimaConsulta = agendamentoRepository
                .findUltimaConsultaEspecialista(request.getEspecialistaId(),
                        request.getDataHoraAgendamento().toLocalDate());
        ValidadorAgendamentoUtil.validarIntervaloEntreConsultas(request.getDataHoraAgendamento(),
                ultimaConsulta);

        // Validar limite diário de consultas do paciente
        int consultasNoDia = agendamentoRepository
                .countByPacienteIdAndData(request.getPacienteId(),
                        request.getDataHoraAgendamento().toLocalDate());
        ValidadorAgendamentoUtil.validarLimiteDiarioConsultas(consultasNoDia);
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