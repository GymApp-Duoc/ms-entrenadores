package com.gymapp.ms_entrenadores.service;

import com.gymapp.ms_entrenadores.client.ClaseClient;
import com.gymapp.ms_entrenadores.dto.EntrenadorRequestDTO;
import com.gymapp.ms_entrenadores.dto.EntrenadorResponseDTO;
import com.gymapp.ms_entrenadores.exception.BusinessException;
import com.gymapp.ms_entrenadores.model.Entrenador;
import com.gymapp.ms_entrenadores.repository.EntrenadorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EntrenadorServiceTest {

    @Mock
    private EntrenadorRepository repository;

    @Mock
    private ClaseClient claseClient;

    @InjectMocks
    private EntrenadorService service;

    private Entrenador entrenador;

    @BeforeEach
    void setUp() {
        entrenador = new Entrenador(1L, "Ricardo", "CrossFit", "ricardo@gym.cl", "12345678", true);
    }

    @Test
    void guardar_Exito() {
        EntrenadorRequestDTO request = new EntrenadorRequestDTO("Ricardo", "CrossFit", "ricardo@gym.cl", "12345678");
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(repository.save(any(Entrenador.class))).thenReturn(entrenador);

        EntrenadorResponseDTO response = service.guardar(request);

        assertNotNull(response);
        assertEquals("Ricardo", response.getNombre());
    }

    @Test
    void eliminar_LanzaExcepcionSiTieneClases() {
        when(repository.findById(1L)).thenReturn(Optional.of(entrenador));

        when(claseClient.buscarPorEntrenador(1L)).thenReturn(new ArrayList<>(java.util.List.of(new Object())));

        assertThrows(BusinessException.class, () -> service.eliminar(1L));
        verify(repository, never()).delete(any(Entrenador.class));
    }
}