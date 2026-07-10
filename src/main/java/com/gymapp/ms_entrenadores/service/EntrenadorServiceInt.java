package com.gymapp.ms_entrenadores.service;

import com.gymapp.ms_entrenadores.dto.EntrenadorRequestDTO;
import com.gymapp.ms_entrenadores.dto.EntrenadorResponseDTO;

import java.util.List;

public interface EntrenadorServiceInt {
    EntrenadorResponseDTO obtenerPorId(Long id);
    List<EntrenadorResponseDTO> listarTodos();
    EntrenadorResponseDTO guardar(EntrenadorRequestDTO dto);
    void eliminar(Long id);

    // Reportes
    List<EntrenadorResponseDTO> listarActivos();
    List<EntrenadorResponseDTO> buscarPorEspecialidad(String especialidad);
    List<EntrenadorResponseDTO> buscarPorNombre(String nombre);
    long contarPorEspecialidad(String especialidad);
    List<EntrenadorResponseDTO> listarInactivos();
}