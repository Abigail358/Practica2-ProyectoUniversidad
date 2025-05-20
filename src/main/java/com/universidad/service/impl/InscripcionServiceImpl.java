package com.universidad.service.impl;

import com.universidad.dto.InscripcionDTO;
import com.universidad.model.*;
import com.universidad.repository.*;
import com.universidad.service.IInscripcionService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InscripcionServiceImpl implements IInscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final EstudianteRepository estudianteRepository;
    private final MateriaRepository materiaRepository;

    @Override
    @Transactional
    public InscripcionDTO crearInscripcion(InscripcionDTO inscripcionDTO) {
        // Validar que no exista una inscripción activa para la misma materia
        if (inscripcionRepository.existsByEstudianteIdAndMateriaId(
                inscripcionDTO.getEstudianteId(), 
                inscripcionDTO.getMateriaId())) {
            throw new IllegalArgumentException("El estudiante ya está inscrito en esta materia");
        }

        Estudiante estudiante = estudianteRepository.findById(inscripcionDTO.getEstudianteId())
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
        Materia materia = materiaRepository.findById(inscripcionDTO.getMateriaId())
                .orElseThrow(() -> new IllegalArgumentException("Materia no encontrada"));

        Inscripcion inscripcion = Inscripcion.builder()
                .estudiante(estudiante)
                .materia(materia)
                .fechaInscripcion(LocalDateTime.now())
                .estado("ACTIVO")
                .build();

        return convertToDTO(inscripcionRepository.save(inscripcion));
    }

    @Override
    public List<InscripcionDTO> listarInscripcionesPorEstudiante(Long estudianteId) {
        return inscripcionRepository.findByEstudianteId(estudianteId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private InscripcionDTO convertToDTO(Inscripcion inscripcion) {
        return InscripcionDTO.builder()
                .id(inscripcion.getId())
                .estudianteId(inscripcion.getEstudiante().getId())
                .materiaId(inscripcion.getMateria().getId())
                .fechaInscripcion(inscripcion.getFechaInscripcion())
                .estado(inscripcion.getEstado())
                .calificacion(inscripcion.getCalificacion())
                .build();
    }

    @Override
    public InscripcionDTO actualizarInscripcion(Long id, InscripcionDTO dto) {
        Inscripcion insc = inscripcionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Inscripción no encontrada"));

        if (dto.getEstado() != null) {
            insc.setEstado(dto.getEstado());
        }

        if (dto.getCalificacion() != null) {
            insc.setCalificacion(dto.getCalificacion());
        }

        return convertToDTO(inscripcionRepository.save(insc));
    }

    @Override
    public void cancelarInscripcion(Long id) {
        Inscripcion insc = inscripcionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Inscripción no encontrada"));
        insc.setEstado("CANCELADO");
        inscripcionRepository.save(insc);
    }

    @Override
    public InscripcionDTO registrarCalificacion(Long id, Double calificacion) {
        Inscripcion insc = inscripcionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Inscripción no encontrada"));
        insc.setCalificacion(calificacion);
        return convertToDTO(inscripcionRepository.save(insc));
    }

    @Override
    public void eliminarInscripcion(Long id) {
        if (!inscripcionRepository.existsById(id)) {
            throw new IllegalArgumentException("Inscripción no encontrada");
        }
        inscripcionRepository.deleteById(id);
    }

}