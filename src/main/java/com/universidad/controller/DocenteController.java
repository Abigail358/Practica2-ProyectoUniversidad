package com.universidad.controller;

import com.universidad.dto.DocenteDTO;
import com.universidad.service.IDocenteService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/docentes")
public class DocenteController {

    private final IDocenteService docenteService;

    @Autowired
    public DocenteController(IDocenteService docenteService) {
        this.docenteService = docenteService;
    }

    @GetMapping
    public ResponseEntity<List<DocenteDTO>> obtenerTodosLosDocentes() {
        return ResponseEntity.ok(docenteService.obtenerTodosLosDocentes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocenteDTO> obtenerDocentePorId(@PathVariable Long id) {
        DocenteDTO docente = docenteService.obtenerDocentePorId(id);
        if (docente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(docente);
    }

    @PostMapping
    @Transactional // Anotación que indica que este método debe ejecutarse dentro de una transacción
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DocenteDTO> crearDocente(@RequestBody DocenteDTO docenteDTO) {
        DocenteDTO nuevoDocente = docenteService.crearDocente(docenteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoDocente);
    }

    @PutMapping("/{id}")
    @Transactional // Anotación que indica que este método debe ejecutarse dentro de una transacción
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DocenteDTO> actualizarDocente(@PathVariable Long id, @RequestBody DocenteDTO docenteDTO) {
        DocenteDTO actualizado = docenteService.actualizarDocente(id, docenteDTO);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @Transactional // Anotación que indica que este método debe ejecutarse dentro de una transacción
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> eliminarDocente(@PathVariable Long id) {
        docenteService.eliminarDocente(id);
        return ResponseEntity.noContent().build();
    }
}
