package com.universidad.controller;

import com.universidad.dto.InscripcionDTO;
import com.universidad.service.IInscripcionService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
@RequiredArgsConstructor
@Validated
public class InscripcionController {

    private final IInscripcionService inscripcionService;

    @PostMapping
    @Transactional // Anotación que indica que este método debe ejecutarse dentro de una transacción
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<InscripcionDTO> crearInscripcion(@RequestBody InscripcionDTO inscripcionDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(inscripcionService.crearInscripcion(inscripcionDTO));
    }

    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<List<InscripcionDTO>> listarPorEstudiante(@PathVariable Long estudianteId) {
        return ResponseEntity.ok(inscripcionService.listarInscripcionesPorEstudiante(estudianteId));
    }

    // Otros endpoints (PUT, DELETE, etc.)...
    @PutMapping("/{id}")
    @Transactional // Anotación que indica que este método debe ejecutarse dentro de una transacción
    @ResponseStatus(HttpStatus.OK)
public ResponseEntity<InscripcionDTO> actualizarInscripcion(
        @PathVariable Long id,
        @RequestBody InscripcionDTO dto) {
    return ResponseEntity.ok(inscripcionService.actualizarInscripcion(id, dto));
}

@PutMapping("/{id}/cancelar")
@Transactional // Anotación que indica que este método debe ejecutarse dentro de una transacción
    @ResponseStatus(HttpStatus.OK)
public ResponseEntity<Void> cancelarInscripcion(@PathVariable Long id) {
    inscripcionService.cancelarInscripcion(id);
    return ResponseEntity.noContent().build();
}

@PutMapping("/{id}/calificacion")
@Transactional // Anotación que indica que este método debe ejecutarse dentro de una transacción
    @ResponseStatus(HttpStatus.OK)
public ResponseEntity<InscripcionDTO> registrarCalificacion(
        @PathVariable Long id,
        @RequestParam Double calificacion) {
    return ResponseEntity.ok(inscripcionService.registrarCalificacion(id, calificacion));
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> eliminarInscripcion(@PathVariable Long id) {
    inscripcionService.eliminarInscripcion(id);
    return ResponseEntity.noContent().build();
}

}

