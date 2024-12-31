package com.saudemosaico.agendamento.util;

import com.saudemosaico.agendamento.exception.AgendamentoException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class ValidadorAgendamentoUtil {
    private static final LocalTime HORA_INICIO_EXPEDIENTE = LocalTime.of(8, 0);
    private static final LocalTime HORA_FIM_EXPEDIENTE = LocalTime.of(18, 0);
    private static final long ANTECEDENCIA_MINIMA_HORAS = 24;
    private static final int INTERVALO_MINIMO_CONSULTAS_MINUTOS = 30;
    private static final int LIMITE_DIARIO_CONSULTAS = 3;

    public static void validarHorarioComercial(LocalDateTime dataHora) {
        LocalTime hora = dataHora.toLocalTime();
        if (hora.isBefore(HORA_INICIO_EXPEDIENTE) || hora.isAfter(HORA_FIM_EXPEDIENTE)) {
            throw new AgendamentoException(
                    String.format("Horário deve estar entre %s e %s",
                            HORA_INICIO_EXPEDIENTE, HORA_FIM_EXPEDIENTE)
            );
        }
    }

    public static void validarDiaUtil(LocalDateTime dataHora) {
        DayOfWeek diaSemana = dataHora.getDayOfWeek();
        if (diaSemana == DayOfWeek.SATURDAY || diaSemana == DayOfWeek.SUNDAY) {
            throw new AgendamentoException("Agendamentos não são permitidos em finais de semana");
        }
    }

    public static void validarAntecedenciaMinima(LocalDateTime dataHora) {
        LocalDateTime minDataHora = LocalDateTime.now().plusHours(ANTECEDENCIA_MINIMA_HORAS);
        if (dataHora.isBefore(minDataHora)) {
            throw new AgendamentoException(
                    String.format("O agendamento deve ter no mínimo %d horas de antecedência",
                            ANTECEDENCIA_MINIMA_HORAS)
            );
        }
    }

    public static void validarIntervaloEntreConsultas(LocalDateTime dataHora, LocalDateTime ultimaConsulta) {
        if (ultimaConsulta != null) {
            long minutosEntre = ChronoUnit.MINUTES.between(ultimaConsulta, dataHora);
            if (Math.abs(minutosEntre) < INTERVALO_MINIMO_CONSULTAS_MINUTOS) {
                throw new AgendamentoException(
                        String.format("Deve haver um intervalo mínimo de %d minutos entre consultas",
                                INTERVALO_MINIMO_CONSULTAS_MINUTOS)
                );
            }
        }
    }

    public static void validarLimiteDiarioConsultas(int consultasNoDia) {
        if (consultasNoDia >= LIMITE_DIARIO_CONSULTAS) {
            throw new AgendamentoException(
                    String.format("Limite diário de %d consultas atingido",
                            LIMITE_DIARIO_CONSULTAS)
            );
        }
    }
}