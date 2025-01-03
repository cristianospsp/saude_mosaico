package com.saudemosaico.notificacao.repository;

import com.saudemosaico.notificacao.domain.Template;
import com.saudemosaico.notificacao.domain.TipoNotificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {
    Optional<Template> findByCodigo(String codigo);
    List<Template> findByTipoAndAtivoTrue(TipoNotificacao tipo);
    boolean existsByCodigo(String codigo);
}
