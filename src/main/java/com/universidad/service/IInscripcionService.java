package com.universidad.service;

import com.universidad.dto.InscripcionDTO;
import java.util.List;

public interface IInscripcionService {
    //InscripcionDTO crearInscripcion(InscripcionDTO inscripcionDTO);
    //List<InscripcionDTO> listarInscripcionesPorEstudiante(Long estudianteId);
    //InscripcionDTO actualizarInscripcion(Long id, InscripcionDTO inscripcionDTO);
   // void cancelarInscripcion(Long id);
    //InscripcionDTO registrarCalificacion(Long id, Double calificacion);
    InscripcionDTO crearInscripcion(InscripcionDTO inscripcionDTO);
    List<InscripcionDTO> listarInscripcionesPorEstudiante(Long estudianteId);
    InscripcionDTO actualizarInscripcion(Long id, InscripcionDTO inscripcionDTO);
    void cancelarInscripcion(Long id);
    InscripcionDTO registrarCalificacion(Long id, Double calificacion);
    void eliminarInscripcion(Long id);
}
