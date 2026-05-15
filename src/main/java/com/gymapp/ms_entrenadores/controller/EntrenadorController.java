package com.gymapp.ms_entrenadores.controller;

import com.gymapp.ms_entrenadores.dto.EntrenadorRequestDTO;
import com.gymapp.ms_entrenadores.dto.EntrenadorResponseDTO;
import com.gymapp.ms_entrenadores.service.EntrenadorServiceInt;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/entrenadores")
@RequiredArgsConstructor
public class EntrenadorController {


    private final EntrenadorServiceInt service;

    @GetMapping
    public ResponseEntity<List<EntrenadorResponseDTO>> listarTodos() {
        log.info("Petición REST recibida: Listar todos los entrenadores");
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntrenadorResponseDTO> obtenerPorId(@PathVariable Long id) {
        log.info("Petición REST recibida: Obtener entrenador con ID {}", id);
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<EntrenadorResponseDTO> crear(@Valid @RequestBody EntrenadorRequestDTO dto) {
        log.info("Petición REST recibida: Crear nuevo entrenador '{}'", dto.getNombre());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Petición REST recibida: Eliminar entrenador con ID {}", id);
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}