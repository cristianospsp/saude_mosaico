package com.saudemosaico.agendamento.dto;

import com.saudemosaico.agendamento.domain.Especialidade;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
public class AgendamentoRequest {

    @NotNull(message = "ID do paciente é obrigatório")
    @Pattern(regexp = "^[0-9a-zA-Z]+$", message = "ID do paciente deve conter apenas letras e números")
    @Length(max = 50, message = "ID do paciente deve ter no máximo 50 caracteres")
    private String pacienteId;

    @NotNull(message = "ID do especialista é obrigatório")
    @Pattern(regexp = "^[0-9a-zA-Z]+$", message = "ID do especialista deve conter apenas letras e números")
    @Length(max = 50, message = "ID do especialista deve ter no máximo 50 caracteres")
    private String especialistaId;

    @NotNull(message = "Especialidade é obrigatória")
    private Especialidade especialidade;

    @NotNull(message = "Data e hora são obrigatórias")
    @FutureOrPresent(message = "A data e hora devem ser futuras ou presentes")
    private LocalDateTime dataHoraAgendamento;

    @Size(max = 255, message = "Observações não podem exceder 255 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9 .,!?()-]*$", message = "Observações contêm caracteres inválidos")
    private String observacoes;

    @AssertTrue(message = "O horário deve estar dentro do expediente (8h às 18h)")
    private boolean isHorarioValido() {
        if (dataHoraAgendamento == null) {
            return true; // será capturado pela validação @NotNull
        }
        int hora = dataHoraAgendamento.getHour();
        return hora >= 8 && hora < 18;
    }

    @AssertTrue(message = "Agendamentos não são permitidos em finais de semana")
    private boolean isDiaUtil() {
        if (dataHoraAgendamento == null) {
            return true; // será capturado pela validação @NotNull
        }
        int diadasemana = dataHoraAgendamento.getDayOfWeek().getValue();
        return diadasemana >= 1 && diadasemana <= 5;
    }

    @AssertTrue(message = "O agendamento deve ter no mínimo 24 horas de antecedência")
    private boolean isAntecedenciaMinima() {
        if (dataHoraAgendamento == null) {
            return true; // será capturado pela validação @NotNull
        }
        LocalDateTime minDataHora = LocalDateTime.now().plusHours(24);
        return dataHoraAgendamento.isAfter(minDataHora);
    }

    public @NotNull(message = "ID do especialista é obrigatório") @Pattern(regexp = "^[0-9a-zA-Z]+$", message = "ID do especialista deve conter apenas letras e números") @Length(max = 50, message = "ID do especialista deve ter no máximo 50 caracteres") String getEspecialistaId() {
        return especialistaId;
    }

    // Getters e Setters são gerados pelo Lombok (@Data)
}