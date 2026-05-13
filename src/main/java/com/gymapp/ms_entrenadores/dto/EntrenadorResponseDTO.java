package com.gymapp.ms_entrenadores.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class EntrenadorResponseDTO {
    private Long id;
    private String nombre;
    private String especialidad;
    private String email;
    private String telefono;
    private boolean activo;
}