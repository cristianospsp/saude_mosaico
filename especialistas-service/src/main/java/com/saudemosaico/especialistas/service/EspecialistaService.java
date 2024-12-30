package com.saudemosaico.especialistas.service;

import com.saudemosaico.especialistas.domain.Especialidade;
import com.saudemosaico.especialistas.dto.EspecialistaRequest;
import com.saudemosaico.especialistas.dto.EspecialistaResponse;
import java.util.List;

public interface EspecialistaService {
    EspecialistaResponse criar(EspecialistaRequest request);
    EspecialistaResponse buscarPorId(Long id);
    EspecialistaResponse buscarPorCrm(String crm);
    List<EspecialistaResponse> buscarPorEspecialidade(Especialidade especialidade);
    List<EspecialistaResponse> listarTodos();
    List<EspecialistaResponse> listarAtivos();
    EspecialistaResponse atualizar(Long id, EspecialistaRequest request);
    void inativar(Long id);
    void reativar(Long id);
}
