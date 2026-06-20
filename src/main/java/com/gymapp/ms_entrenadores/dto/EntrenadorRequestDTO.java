package com.gymapp.ms_entrenadores.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Objeto para registrar o actualizar un entrenador en el sistema")
public class EntrenadorRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @Schema(description = "Nombre completo del entrenador", example = "Ricardo Milos")
    private String nombre;

    @NotBlank(message = "La especialidad es obligatoria (Ej: Musculación, Crossfit)")
    @Size(min = 3, max = 100, message = "La especialidad debe tener entre 3 y 100 caracteres")
    @Schema(description = "Área de conocimiento principal", example = "CrossFit")
    private String especialidad;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "Debe ingresar un formato de correo electrónico válido")
    @Schema(description = "Correo electrónico corporativo único", example = "ricardo.m@gymsync.cl")
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^\\+?[0-9]{8,15}$", message = "El teléfono debe contener entre 8 y 15 dígitos numéricos")
    @Schema(description = "Número de contacto telefónico", example = "+56912345678")
    private String telefono;
}