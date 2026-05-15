package com.gymapp.ms_entrenadores.service;

import com.gymapp.ms_entrenadores.dto.EntrenadorRequestDTO;
import com.gymapp.ms_entrenadores.dto.EntrenadorResponseDTO;

import java.util.List;

public interface EntrenadorServiceInt {

    List<EntrenadorResponseDTO> listarTodos();

    EntrenadorResponseDTO obtenerPorId(Long id);

    EntrenadorResponseDTO guardar(EntrenadorRequestDTO dto);

    void eliminar(Long id);
}