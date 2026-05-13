package com.gymapp.ms_entrenadores.repository;

import com.gymapp.ms_entrenadores.model.Entrenador;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EntrenadorRepository extends JpaRepository<Entrenador, Long> {
    Optional<Entrenador> findByEmail(String email);
}