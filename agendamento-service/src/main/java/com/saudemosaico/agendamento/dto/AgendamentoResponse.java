package com.saudemosaico.agendamento.dto;

import lombok.Data;
import com.saudemosaico.agendamento.domain.StatusAgendamento;
import java.time.LocalDateTime;

@Data
public class AgendamentoResponse {
    private Long id;
    private String pacienteId;
    private String especialistaId;
    private String especialidade;
    private LocalDateTime dataHoraAgendamento;
    private StatusAgendamento status;
    private String linkVideoConferencia;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}
