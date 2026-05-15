package com.gymapp.ms_entrenadores.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ms-clases", url = "${ms.clases.url}")
public interface ClaseClient {

    @GetMapping("/api/clases/entrenador/{entrenadorId}")
    List<?> buscarPorEntrenador(@PathVariable("entrenadorId") Long entrenadorId);
}