package com.gymapp.ms_entrenadores;

import com.gymapp.ms_entrenadores.client.ClaseClient;
import com.gymapp.ms_entrenadores.config.JwtAuthenticationFilter;
import com.gymapp.ms_entrenadores.config.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;


@SpringBootTest(properties = "ms.clases.url=http://localhost:8080")
class MsEntrenadoresApplicationTests {

    @MockitoBean
    private ClaseClient claseClient;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void contextLoads() {
    }
}