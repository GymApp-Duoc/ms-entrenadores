package com.gymapp.ms_entrenadores.service;

import com.gymapp.ms_entrenadores.dto.EntrenadorRequestDTO;
import com.gymapp.ms_entrenadores.dto.EntrenadorResponseDTO;
import com.gymapp.ms_entrenadores.exception.BusinessException;
import com.gymapp.ms_entrenadores.model.Entrenador;
import com.gymapp.ms_entrenadores.repository.EntrenadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EntrenadorService {

    private final EntrenadorRepository repository;
    private final RestTemplate restTemplate;

    @Value("${ms.clases.url}")
    private String clasesUrl;

    public List<EntrenadorResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    public EntrenadorResponseDTO obtenerPorId(Long id) {
        Entrenador entrenador = repository.findById(id)
                .orElseThrow(() -> new BusinessException("El entrenador con ID " + id + " no existe en el sistema."));

        return convertirADto(entrenador);
    }

    @Transactional
    public EntrenadorResponseDTO guardar(EntrenadorRequestDTO dto) {
        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new BusinessException("Ya existe un entrenador registrado con el correo: " + dto.getEmail());
        }

        Entrenador entrenador = new Entrenador(null, dto.getNombre(), dto.getEspecialidad(), dto.getEmail(), dto.getTelefono(), true);
        return convertirADto(repository.save(entrenador));
    }

    @Transactional
    public boolean eliminar(Long id) {
        Entrenador entrenador = repository.findById(id)
                .orElseThrow(() -> new BusinessException("No se puede eliminar: El entrenador con ID " + id + " no existe."));

        validarSiTieneClasesActivas(id);

        repository.delete(entrenador);
        return true;
    }

    private void validarSiTieneClasesActivas(Long id) {
        try {
            String url = clasesUrl + "/api/clases/entrenador/" + id;
            List<?> clasesAsignadas = restTemplate.getForObject(url, List.class);

            if (clasesAsignadas != null && !clasesAsignadas.isEmpty()) {
                throw new BusinessException("No se puede eliminar: El entrenador tiene clases activas asignadas.");
            }
        } catch (BusinessException be) {
            throw be;
        } catch (Exception e) {
            throw new BusinessException("Error de comunicación con el servicio de clases para validar dependencias.");
        }
    }

    private EntrenadorResponseDTO convertirADto(Entrenador e) {
        return new EntrenadorResponseDTO(e.getId(), e.getNombre(), e.getEspecialidad(), e.getEmail(), e.getTelefono(), e.isActivo());
    }
}