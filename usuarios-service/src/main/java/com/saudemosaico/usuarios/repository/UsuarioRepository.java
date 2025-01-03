package com.saudemosaico.usuarios.repository;

import com.saudemosaico.usuarios.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCpf(String cpf);
    Optional<Usuario> findByRg(String rg);
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByAtivoTrue();
    boolean existsByCpf(String cpf);
    boolean existsByRg(String rg);
    boolean existsByEmail(String email);
}
