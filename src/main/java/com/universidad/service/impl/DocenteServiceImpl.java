package com.universidad.service.impl;

import com.universidad.dto.DocenteDTO;
import com.universidad.model.Docente;
import com.universidad.repository.DocenteRepository;
import com.universidad.service.IDocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocenteServiceImpl implements IDocenteService {

    @Autowired
    private DocenteRepository docenteRepository;

    private DocenteDTO mapToDTO(Docente docente) {
        return DocenteDTO.builder()
                .id(docente.getId())
                .nombre(docente.getNombre())
                .apellido(docente.getApellido())
                .email(docente.getEmail())
                .fechaNacimiento(docente.getFechaNacimiento())
                .nroEmpleado(docente.getNroEmpleado())
                .departamento(docente.getDepartamento())
                .build();
    }

    private Docente mapToEntity(DocenteDTO dto) {
        return Docente.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .email(dto.getEmail())
                .fechaNacimiento(dto.getFechaNacimiento())
                .nroEmpleado(dto.getNroEmpleado())
                .departamento(dto.getDepartamento())
                .build();
    }

    @Override
    public List<DocenteDTO> obtenerTodosLosDocentes() {
        return docenteRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public DocenteDTO obtenerDocentePorId(Long id) {
        return docenteRepository.findById(id).map(this::mapToDTO).orElse(null);
    }

    @Override
    public DocenteDTO crearDocente(DocenteDTO docenteDTO) {
        Docente docente = docenteRepository.save(mapToEntity(docenteDTO));
        return mapToDTO(docente);
    }

    @Override
    public DocenteDTO actualizarDocente(Long id, DocenteDTO docenteDTO) {
        Docente docente = docenteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Docente no encontrado"));

        docente.setNombre(docenteDTO.getNombre());
        docente.setApellido(docenteDTO.getApellido());
        docente.setEmail(docenteDTO.getEmail());
        docente.setFechaNacimiento(docenteDTO.getFechaNacimiento());
        docente.setNroEmpleado(docenteDTO.getNroEmpleado());
        docente.setDepartamento(docenteDTO.getDepartamento());

        Docente actualizado = docenteRepository.save(docente);
        return mapToDTO(actualizado);
    }

    @Override
    public void eliminarDocente(Long id) {
        docenteRepository.deleteById(id);
    }
}