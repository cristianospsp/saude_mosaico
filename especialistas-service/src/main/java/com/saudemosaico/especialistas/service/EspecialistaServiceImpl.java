package com.saudemosaico.especialistas.service;

import com.saudemosaico.especialistas.domain.Especialidade;
import com.saudemosaico.especialistas.domain.Especialista;
import com.saudemosaico.especialistas.dto.EspecialistaRequest;
import com.saudemosaico.especialistas.dto.EspecialistaResponse;
import com.saudemosaico.especialistas.exception.EspecialistaException;
import com.saudemosaico.especialistas.exception.EspecialistaNaoEncontradoException;
import com.saudemosaico.especialistas.repository.EspecialistaRepository;
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
            
        especialista.setAtivo(false);
        especialistaRepository.save(especialista);
    }

    @Override
    @Transactional
    public void reativar(Long id) {
        Especialista especialista = especialistaRepository.findById(id)
            .orElseThrow(() -> new EspecialistaNaoEncontradoException(id));
            
        especialista.setAtivo(true);
        especialistaRepository.save(especialista);
    }

    private void validarNovoEspecialista(EspecialistaRequest request) {
        if (especialistaRepository.existsByCrm(request.getCrm())) {
            throw new EspecialistaException("Já existe um especialista com este CRM");
        }
        
        if (especialistaRepository.existsByEmail(request.getEmail())) {
            throw new EspecialistaException("Já existe um especialista com este email");
        }
    }

    private void validarAtualizacaoEspecialista(EspecialistaRequest request, Especialista especialista) {
        if (!especialista.getEmail().equals(request.getEmail()) &&
            especialistaRepository.existsByEmail(request.getEmail())) {
            throw new EspecialistaException("Já existe um especialista com este email");
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
