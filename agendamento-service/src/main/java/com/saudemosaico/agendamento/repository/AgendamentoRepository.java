package com.saudemosaico.agendamento.repository;

import com.saudemosaico.agendamento.domain.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    // Métodos existentes...

    @Query("SELECT COUNT(a) FROM Agendamento a WHERE a.pacienteId = :pacienteId " +
            "AND DATE(a.dataHoraAgendamento) = :data")
    int countByPacienteIdAndData(@Param("pacienteId") String pacienteId, @Param("data") LocalDate data);

    @Query("SELECT MAX(a.dataHoraAgendamento) FROM Agendamento a " +
            "WHERE a.especialistaId = :especialistaId " +
            "AND DATE(a.dataHoraAgendamento) = :data")
    LocalDateTime findUltimaConsultaEspecialista(
            @Param("especialistaId") String especialistaId,
            @Param("data") LocalDate data
    );
}