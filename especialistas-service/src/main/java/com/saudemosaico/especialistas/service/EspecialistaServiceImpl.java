package com.saudemosaico.especialistas.service;

import com.saudemosaico.especialistas.domain.Especialidade;
import com.saudemosaico.especialistas.domain.Especialista;
import com.saudemosaico.especialistas.dto.EspecialistaRequest;
import com.saudemosaico.especialistas.dto.EspecialistaResponse;
import com.saudemosaico.especialistas.exception.EspecialistaException;
import com.saudemosaico.especialistas.exception.EspecialistaNaoEncontradoException;
import com.saudemosaico.especialistas.repository.EspecialistaRepository;
import com.saudemosaico.especialistas.util.ValidadorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EspecialistaServiceImpl implements EspecialistaService {

    private final EspecialistaRepository especialistaRepository;

    @Override
    @Transactional
    public EspecialistaResponse criar(EspecialistaRequest request) {
        validarNovoEspecialista(request);

        Especialista especialista = new Especialista();
        especialista.setNome(request.getNome());
        especialista.setCrm(request.getCrm());
        especialista.setCpf(request.getCpf());
        especialista.setEmail(request.getEmail());
        especialista.setTelefone(request.getTelefone());
        especialista.setEspecialidades(request.getEspecialidades());
        especialista.setAtivo(true);

        return converterParaResponse(especialistaRepository.save(especialista));
    }

    @Override
    @Transactional(readOnly = true)
    public EspecialistaResponse buscarPorId(Long id) {
        return especialistaRepository.findById(id)
                .map(this::converterParaResponse)
                .orElseThrow(() -> new EspecialistaNaoEncontradoException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public EspecialistaResponse buscarPorCrm(String crm) {
        return especialistaRepository.findByCrm(crm)
                .map(this::converterParaResponse)
                .orElseThrow(() -> new EspecialistaNaoEncontradoException(crm));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EspecialistaResponse> buscarPorEspecialidade(Especialidade especialidade) {
        return especialistaRepository.findByEspecialidade(especialidade).stream()
                .map(this::converterParaResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EspecialistaResponse> listarTodos() {
        return especialistaRepository.findAll().stream()
                .map(this::converterParaResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EspecialistaResponse> listarAtivos() {
        return especialistaRepository.findByAtivoTrue().stream()
                .map(this::converterParaResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EspecialistaResponse atualizar(Long id, EspecialistaRequest request) {
        Especialista especialista = especialistaRepository.findById(id)
                .orElseThrow(() -> new EspecialistaNaoEncontradoException(id));

        validarAtualizacaoEspecialista(request, especialista);

        especialista.setNome(request.getNome());
        especialista.setEmail(request.getEmail());
        especialista.setTelefone(request.getTelefone());
        especialista.setEspecialidades(request.getEspecialidades());

        return converterParaResponse(especialistaRepository.save(especialista));
    }

    @Override
    @Transactional
    public void inativar(Long id) {
        Especialista especialista = especialistaRepository.findById(id)
                .orElseThrow(() -> new EspecialistaNaoEncontradoException(id));

        if (!especialista.isAtivo()) {
            throw new EspecialistaException("Especialista já está inativo");
        }

        especialista.setAtivo(false);
        especialistaRepository.save(especialista);
    }

    @Override
    @Transactional
    public void reativar(Long id) {
        Especialista especialista = especialistaRepository.findById(id)
                .orElseThrow(() -> new EspecialistaNaoEncontradoException(id));

        if (especialista.isAtivo()) {
            throw new EspecialistaException("Especialista já está ativo");
        }

        especialista.setAtivo(true);
        especialistaRepository.save(especialista);
    }

    private void validarNovoEspecialista(EspecialistaRequest request) {
        // Validação de CRM
        if (!ValidadorUtil.validarCRM(request.getCrm())) {
            throw new EspecialistaException("Formato de CRM inválido");
        }
        if (especialistaRepository.existsByCrm(request.getCrm())) {
            throw new EspecialistaException("Já existe um especialista com este CRM");
        }

        // Validação de CPF
        if (!ValidadorUtil.validarCPF(request.getCpf())) {
            throw new EspecialistaException("CPF inválido");
        }
        if (especialistaRepository.existsByCpf(request.getCpf())) {
            throw new EspecialistaException("Já existe um especialista com este CPF");
        }

        // Validação de nome
        if (!ValidadorUtil.validarNome(request.getNome())) {
            throw new EspecialistaException("Nome inválido (deve ter entre 2 e 90 caracteres e conter apenas letras)");
        }

        // Validação de telefone
        if (!ValidadorUtil.validarTelefone(request.getTelefone())) {
            throw new EspecialistaException("Formato de telefone inválido");
        }

        // Validação de quantidade de especialidades
        if (!ValidadorUtil.validarQuantidadeEspecialidades(request.getEspecialidades().size())) {
            throw new EspecialistaException("O especialista deve ter entre 1 e 4 especialidades");
        }
    }

    private void validarAtualizacaoEspecialista(EspecialistaRequest request, Especialista especialista) {
        // Validação de email na atualização
        if (!especialista.getEmail().equals(request.getEmail()) &&
                especialistaRepository.existsByEmail(request.getEmail())) {
            throw new EspecialistaException("Já existe um especialista com este email");
        }

        // Validação de especialidades na atualização
        if (request.getEspecialidades() == null || request.getEspecialidades().isEmpty()) {
            throw new EspecialistaException("O especialista deve ter pelo menos uma especialidade");
        }
    }

    private EspecialistaResponse converterParaResponse(Especialista especialista) {
        EspecialistaResponse response = new EspecialistaResponse();
        response.setId(especialista.getId());
        response.setNome(especialista.getNome());
        response.setCrm(especialista.getCrm());
        response.setEmail(especialista.getEmail());
        response.setTelefone(especialista.getTelefone());
        response.setEspecialidades(especialista.getEspecialidades());
        response.setAtivo(especialista.isAtivo());
        response.setCriadoEm(especialista.getCriadoEm());
        response.setAtualizadoEm(especialista.getAtualizadoEm());
        return response;
    }
}