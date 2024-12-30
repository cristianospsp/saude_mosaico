package com.saudemosaico.agendamento.repository;

import com.saudemosaico.agendamento.domain.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    
    @Query("SELECT a FROM Agendamento a WHERE a.especialistaId = :especialistaId " +
           "AND a.dataHoraAgendamento BETWEEN :inicio AND :fim")
    List<Agendamento> findByEspecialistaAndPeriodo(
        @Param("especialistaId") String especialistaId,
        @Param("inicio") LocalDateTime inicio,
        @Param("fim") LocalDateTime fim
    );
    
    boolean existsByEspecialistaIdAndDataHoraAgendamento(
        String especialistaId,
        LocalDateTime dataHoraAgendamento
    );
}
