package com.gymapp.ms_entrenadores.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EntrenadorRequestDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La especialidad es obligatoria")
    private String especialidad;

    @Email(message = "Formato de correo no válido")
    @NotBlank(message = "El correo es obligatorio")
    private String email;

    private String telefono;
}
