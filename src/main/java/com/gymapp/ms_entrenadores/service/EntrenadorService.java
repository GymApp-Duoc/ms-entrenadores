package com.gymapp.ms_entrenadores.service;

import com.gymapp.ms_entrenadores.client.ClaseClient;
import com.gymapp.ms_entrenadores.dto.EntrenadorRequestDTO;
import com.gymapp.ms_entrenadores.dto.EntrenadorResponseDTO;
import com.gymapp.ms_entrenadores.exception.BusinessException;
import com.gymapp.ms_entrenadores.model.Entrenador;
import com.gymapp.ms_entrenadores.repository.EntrenadorRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EntrenadorService implements EntrenadorServiceInt {

    private final EntrenadorRepository repository;
    private final ClaseClient claseClient;

    @Override
    @Transactional(readOnly = true)
    public List<EntrenadorResponseDTO> listarTodos() {
        log.info("Consultando la lista de todos los entrenadores");
        return repository.findAll().stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EntrenadorResponseDTO obtenerPorId(Long id) {
        log.info("Buscando entrenador con ID: {}", id);
        Entrenador entrenador = repository.findById(id)
                .orElseThrow(() -> new BusinessException("El entrenador con ID " + id + " no existe."));
        return convertirADto(entrenador);
    }

    @Override
    @Transactional
    public EntrenadorResponseDTO guardar(EntrenadorRequestDTO dto) {
        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new BusinessException("Ya existe un entrenador con el correo: " + dto.getEmail());
        }

        Entrenador entrenador = new Entrenador();
        entrenador.setNombre(dto.getNombre());
        entrenador.setEspecialidad(dto.getEspecialidad());
        entrenador.setEmail(dto.getEmail());
        entrenador.setTelefono(dto.getTelefono());
        entrenador.setActivo(true);

        log.info("Guardando nuevo entrenador: {}", entrenador.getNombre());
        return convertirADto(repository.save(entrenador));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Entrenador entrenador = repository.findById(id)
                .orElseThrow(() -> new BusinessException("No se encuentra el entrenador ID: " + id));


        validarSiTieneClasesActivas(id);

        repository.delete(entrenador);
        log.info("Entrenador eliminado físicamente: {}", id);
    }



    private void validarSiTieneClasesActivas(Long id) {
        try {
            List<?> clasesAsignadas = claseClient.buscarPorEntrenador(id);

            if (clasesAsignadas != null && !clasesAsignadas.isEmpty()) {
                log.warn("Intento de eliminación denegado: El entrenador {} tiene clases activas.", id);
                throw new BusinessException("No se puede eliminar: El entrenador tiene clases activas.");
            }
        } catch (BusinessException be) {
            throw be;
        } catch (FeignException e) {
            log.error("Falla de comunicación con ms-clases a través de Feign: {}", e.getMessage());
            throw new BusinessException("No se pudo verificar si el entrenador tiene clases debido a un error de conexión.");
        }
    }

    private EntrenadorResponseDTO convertirADto(Entrenador e) {
        return EntrenadorResponseDTO.builder()
                .id(e.getId())
                .nombre(e.getNombre())
                .especialidad(e.getEspecialidad())
                .email(e.getEmail())
                .telefono(e.getTelefono())
                .activo(e.isActivo())
                .build();
    }
}