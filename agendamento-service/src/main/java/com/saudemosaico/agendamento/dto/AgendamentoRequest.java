package com.saudemosaico.agendamento.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.FutureOrPresent;
import java.time.LocalDateTime;

@Data
public class AgendamentoRequest {
    @NotNull(message = "ID do paciente é obrigatório")
    private String pacienteId;
    
    @NotNull(message = "ID do especialista é obrigatório")
    private String especialistaId;
    
    @NotNull(message = "Especialidade é obrigatória")
    private String especialidade;
    
    @NotNull(message = "Data e hora são obrigatórias")
    @FutureOrPresent(message = "A data e hora devem ser futuras ou presentes")
    private LocalDateTime dataHoraAgendamento;
}
