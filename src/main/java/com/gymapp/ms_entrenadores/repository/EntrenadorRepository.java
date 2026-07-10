package com.gymapp.ms_entrenadores.repository;

import com.gymapp.ms_entrenadores.model.Entrenador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntrenadorRepository extends JpaRepository<Entrenador, Long> {

    Optional<Entrenador> findByEmail(String email);

    @Query("SELECT e FROM Entrenador e WHERE e.activo = true")
    List<Entrenador> findEntrenadoresActivos();

    @Query("SELECT e FROM Entrenador e WHERE LOWER(e.especialidad) = LOWER(:especialidad) AND e.activo = true")
    List<Entrenador> findByEspecialidadActivo(@Param("especialidad") String especialidad);

    @Query("SELECT e FROM Entrenador e WHERE LOWER(e.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Entrenador> buscarPorNombre(@Param("nombre") String nombre);

    @Query("SELECT COUNT(e) FROM Entrenador e WHERE LOWER(e.especialidad) = LOWER(:especialidad) AND e.activo = true")
    long countActivosPorEspecialidad(@Param("especialidad") String especialidad);

    @Query("SELECT e FROM Entrenador e WHERE e.activo = false")
    List<Entrenador> findEntrenadoresInactivos();
}