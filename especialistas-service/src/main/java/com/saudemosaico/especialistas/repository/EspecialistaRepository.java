package com.saudemosaico.especialistas.repository;

import com.saudemosaico.especialistas.domain.Especialidade;
import com.saudemosaico.especialistas.domain.Especialista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EspecialistaRepository extends JpaRepository<Especialista, Long> {
    
    Optional<Especialista> findByCrm(String crm);
    
    Optional<Especialista> findByEmail(String email);
    
    @Query("SELECT e FROM Especialista e WHERE :especialidade MEMBER OF e.especialidades AND e.ativo = true")
    List<Especialista> findByEspecialidade(@Param("especialidade") Especialidade especialidade);
    
    boolean existsByCrm(String crm);
    
    boolean existsByEmail(String email);
    
    List<Especialista> findByAtivoTrue();
}
