package com.saudemosaico.agendamento.service;

import com.saudemosaico.agendamento.dto.AgendamentoRequest;
import com.saudemosaico.agendamento.dto.AgendamentoResponse;
import java.util.List;

public interface AgendamentoService {
    AgendamentoResponse criar(AgendamentoRequest request);
    AgendamentoResponse buscarPorId(Long id);
    List<AgendamentoResponse> listarTodos();
    List<AgendamentoResponse> listarPorPaciente(String pacienteId);
    List<AgendamentoResponse> listarPorEspecialista(String especialistaId);
    AgendamentoResponse atualizar(Long id, AgendamentoRequest request);
    void deletar(Long id);
}
