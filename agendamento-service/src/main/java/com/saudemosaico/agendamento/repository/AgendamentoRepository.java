package com.saudemosaico.agendamento.repository;

import com.saudemosaico.agendamento.domain.Agendamento;
import com.saudemosaico.agendamento.domain.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    boolean existsByEspecialistaIdAndDataHoraAgendamento(
            String especialistaId,
            LocalDateTime dataHoraAgendamento
    );

    List<Agendamento> findByPacienteId(String pacienteId);

    List<Agendamento> findByEspecialistaId(String especialistaId);

    @Query("SELECT COUNT(a) FROM Agendamento a WHERE a.pacienteId = :pacienteId " +
            "AND DATE(a.dataHoraAgendamento) = :data")
    int countByPacienteIdAndData(
            @Param("pacienteId") String pacienteId,
            @Param("data") LocalDate data
    );

    @Query("SELECT MAX(a.dataHoraAgendamento) FROM Agendamento a " +
            "WHERE a.especialistaId = :especialistaId " +
            "AND DATE(a.dataHoraAgendamento) = :data")
    LocalDateTime findUltimaConsultaEspecialista(
            @Param("especialistaId") String especialistaId,
            @Param("data") LocalDate data
    );

    @Query("SELECT a FROM Agendamento a WHERE a.especialistaId = :especialistaId " +
            "AND a.dataHoraAgendamento BETWEEN :inicio AND :fim")
    List<Agendamento> findByEspecialistaAndPeriodo(
            @Param("especialistaId") String especialistaId,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );

    @Query("SELECT COUNT(a) FROM Agendamento a " +
            "WHERE a.especialidade = :especialidade " +
            "AND a.pacienteId = :pacienteId " +
            "AND a.dataHoraAgendamento BETWEEN :inicio AND :fim")
    int countAgendamentosPorEspecialidade(
            @Param("especialidade") Especialidade especialidade,
            @Param("pacienteId") String pacienteId,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );

    @Query("SELECT a FROM Agendamento a " +
            "WHERE a.dataHoraAgendamento >= :hoje " +
            "ORDER BY a.dataHoraAgendamento ASC")
    List<Agendamento> findAllFuturos(@Param("hoje") LocalDateTime hoje);

    @Query("SELECT a FROM Agendamento a " +
            "WHERE a.pacienteId = :pacienteId " +
            "AND a.dataHoraAgendamento >= :hoje " +
            "ORDER BY a.dataHoraAgendamento ASC")
    List<Agendamento> findFuturosByPaciente(
            @Param("pacienteId") String pacienteId,
            @Param("hoje") LocalDateTime hoje
    );

    @Query("SELECT a FROM Agendamento a " +
            "WHERE a.especialistaId = :especialistaId " +
            "AND DATE(a.dataHoraAgendamento) = :data " +
            "ORDER BY a.dataHoraAgendamento ASC")
    List<Agendamento> findByEspecialistaIdAndData(
            @Param("especialistaId") String especialistaId,
            @Param("data") LocalDate data
    );
}