package com.saudemosaico.agendamento.client;

import com.saudemosaico.agendamento.dto.EspecialistaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Optional;

@FeignClient(name = "especialista-service", url = "${app.especialista-service.url}")
public interface EspecialistaClient {
    
    @GetMapping("/api/v1/especialistas/{id}")
    Optional<EspecialistaResponse> buscarPorId(@PathVariable Long id);
}
