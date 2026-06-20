package com.gymapp.ms_entrenadores.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gymapp.ms_entrenadores.assembler.EntrenadorModelAssembler;
import com.gymapp.ms_entrenadores.dto.EntrenadorResponseDTO;
import com.gymapp.ms_entrenadores.service.EntrenadorServiceInt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EntrenadorController.class)
@AutoConfigureMockMvc(addFilters = false)
class EntrenadorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EntrenadorServiceInt service;

    @MockitoBean
    private EntrenadorModelAssembler assembler;

    private EntrenadorResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new EntrenadorResponseDTO(1L, "Ricardo", "CrossFit", "ricardo@gym.cl", "12345678", true);
        when(assembler.toModel(any(EntrenadorResponseDTO.class)))
                .thenAnswer(invocation -> EntityModel.of((EntrenadorResponseDTO) invocation.getArgument(0)));
    }

    @Test
    void listarTodos_Exito() throws Exception {
        when(service.listarTodos()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/entrenadores")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.entrenadorResponseDTOList[0].nombre").value("Ricardo"));
    }
}