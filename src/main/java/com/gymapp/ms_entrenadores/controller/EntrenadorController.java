package com.gymapp.ms_entrenadores.controller;

import com.gymapp.ms_entrenadores.assembler.EntrenadorModelAssembler;
import com.gymapp.ms_entrenadores.dto.EntrenadorRequestDTO;
import com.gymapp.ms_entrenadores.dto.EntrenadorResponseDTO;
import com.gymapp.ms_entrenadores.service.EntrenadorServiceInt;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping("/api/entrenadores")
@RequiredArgsConstructor
@Tag(name = "Entrenadores", description = "API para gestionar el staff de entrenadores")
public class EntrenadorController {

    private final EntrenadorServiceInt service;
    private final EntrenadorModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar todos", description = "Retorna catálogo completo con enlaces HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<EntrenadorResponseDTO>>> listarTodos() {
        List<EntityModel<EntrenadorResponseDTO>> entrenadores = service.listarTodos().stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(entrenadores,
                linkTo(methodOn(EntrenadorController.class).listarTodos()).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener por ID")
    public ResponseEntity<EntityModel<EntrenadorResponseDTO>> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(service.obtenerPorId(id)));
    }

    @PostMapping
    @Operation(summary = "Crear entrenador")
    public ResponseEntity<EntityModel<EntrenadorResponseDTO>> crear(@Valid @RequestBody EntrenadorRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(service.guardar(dto)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar", description = "Valida en ms-clases antes de borrar físicamente")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }



    @GetMapping("/reportes/activos")
    @Operation(summary = "Reporte 1: Activos")
    public ResponseEntity<CollectionModel<EntityModel<EntrenadorResponseDTO>>> listarActivos() {
        List<EntityModel<EntrenadorResponseDTO>> lista = service.listarActivos().stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(lista, linkTo(methodOn(EntrenadorController.class).listarActivos()).withSelfRel()));
    }

    @GetMapping("/reportes/especialidad/{especialidad}")
    @Operation(summary = "Reporte 2: Por especialidad")
    public ResponseEntity<CollectionModel<EntityModel<EntrenadorResponseDTO>>> buscarPorEspecialidad(@PathVariable String especialidad) {
        List<EntityModel<EntrenadorResponseDTO>> lista = service.buscarPorEspecialidad(especialidad).stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(lista));
    }

    @GetMapping("/reportes/buscar")
    @Operation(summary = "Reporte 3: Búsqueda dinámica por nombre")
    public ResponseEntity<CollectionModel<EntityModel<EntrenadorResponseDTO>>> buscarPorNombre(@RequestParam String nombre) {
        List<EntityModel<EntrenadorResponseDTO>> lista = service.buscarPorNombre(nombre).stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(lista));
    }

    @GetMapping("/reportes/especialidad/{especialidad}/conteo")
    @Operation(summary = "Reporte 4: Conteo por especialidad")
    public ResponseEntity<Long> contarPorEspecialidad(@PathVariable String especialidad) {
        return ResponseEntity.ok(service.contarPorEspecialidad(especialidad));
    }

    @GetMapping("/reportes/inactivos")
    @Operation(summary = "Reporte 5: Inactivos")
    public ResponseEntity<CollectionModel<EntityModel<EntrenadorResponseDTO>>> listarInactivos() {
        List<EntityModel<EntrenadorResponseDTO>> lista = service.listarInactivos().stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(lista));
    }
}