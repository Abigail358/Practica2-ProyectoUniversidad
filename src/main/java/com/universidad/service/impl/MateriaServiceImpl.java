package com.universidad.service.impl;

import com.universidad.model.Docente;
import com.universidad.model.Materia;
import com.universidad.repository.DocenteRepository;
import com.universidad.repository.MateriaRepository;
import com.universidad.service.IMateriaService;

import jakarta.transaction.Transactional;
import com.universidad.dto.MateriaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MateriaServiceImpl implements IMateriaService {

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private DocenteRepository docenteRepository;

    // Método utilitario para mapear Materia a MateriaDTO
    private MateriaDTO mapToDTO(Materia materia) {
        if (materia == null) return null;
        return MateriaDTO.builder()
                .id(materia.getId())
                .nombreMateria(materia.getNombreMateria())
                .codigoUnico(materia.getCodigoUnico())
                .creditos(materia.getCreditos())
                .prerequisitos(materia.getPrerequisitos() != null ?
                    materia.getPrerequisitos().stream().map(Materia::getId).collect(Collectors.toList()) : null)
                .esPrerequisitoDe(materia.getEsPrerequisitoDe() != null ?
                    materia.getEsPrerequisitoDe().stream().map(Materia::getId).collect(Collectors.toList()) : null)
                //TAREA
                .docenteId(materia.getDocente() != null ? materia.getDocente().getId() : null)
                //
                .build();
    }

    @Override
    @Cacheable(value = "materias")
    public List<MateriaDTO> obtenerTodasLasMaterias() {
        return materiaRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "materia", key = "#id")
    public MateriaDTO obtenerMateriaPorId(Long id) {
        return materiaRepository.findById(id).map(this::mapToDTO).orElse(null);
    }

    @Override
    @Cacheable(value = "materia", key = "#codigoUnico")
    public MateriaDTO obtenerMateriaPorCodigoUnico(String codigoUnico) {
        Materia materia = materiaRepository.findByCodigoUnico(codigoUnico);
        return mapToDTO(materia);
    }

    /*@Override
    @CachePut(value = "materia", key = "#result.id")
    @CacheEvict(value = "materias", allEntries = true)
    public MateriaDTO crearMateria(MateriaDTO materiaDTO) {
        Materia materia = new Materia();
        materia.setNombreMateria(materiaDTO.getNombreMateria());
        materia.setCodigoUnico(materiaDTO.getCodigoUnico());
        materia.setCreditos(materiaDTO.getCreditos());
        // Map other fields as necessary
        Materia savedMateria = materiaRepository.save(materia);
        return mapToDTO(savedMateria);
    }

    @Override
    @CachePut(value = "materia", key = "#id")
    @CacheEvict(value = "materias", allEntries = true)
    public MateriaDTO actualizarMateria(Long id, MateriaDTO materiaDTO) {
        Materia materia = materiaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Materia not found"));
        materia.setNombreMateria(materiaDTO.getNombreMateria());
        materia.setCodigoUnico(materiaDTO.getCodigoUnico());
        materia.setCreditos(materiaDTO.getCreditos());
        // Map other fields as necessary
        Materia updatedMateria = materiaRepository.save(materia);
        return mapToDTO(updatedMateria);
    }*/

    @Override
    @CacheEvict(value = {"materia", "materias"}, allEntries = true)
    public void eliminarMateria(Long id) {
        materiaRepository.deleteById(id);
    }

    //TAREA
    @Override
    @CachePut(value = "materia", key = "#result.id")
    @CacheEvict(value = "materias", allEntries = true)
    public MateriaDTO crearMateria(MateriaDTO materiaDTO) {
        Materia materia = new Materia();
        materia.setNombreMateria(materiaDTO.getNombreMateria());
        materia.setCodigoUnico(materiaDTO.getCodigoUnico());
        materia.setCreditos(materiaDTO.getCreditos());
        
        // Asignar docente si se proporciona el ID
        if (materiaDTO.getDocenteId() != null) {
            Docente docente = docenteRepository.findById(materiaDTO.getDocenteId())
                    .orElseThrow(() -> new IllegalArgumentException("Docente no encontrado con ID: " + materiaDTO.getDocenteId()));
            materia.setDocente(docente);
        }
        
        Materia savedMateria = materiaRepository.save(materia);
        return mapToDTO(savedMateria);
    }
    
    @Override
    @CachePut(value = "materia", key = "#id")
    @CacheEvict(value = "materias", allEntries = true)
    public MateriaDTO actualizarMateria(Long id, MateriaDTO materiaDTO) {
        Materia materia = materiaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Materia no encontrada"));
        
        materia.setNombreMateria(materiaDTO.getNombreMateria());
        materia.setCodigoUnico(materiaDTO.getCodigoUnico());
        materia.setCreditos(materiaDTO.getCreditos());
        
        if (materiaDTO.getDocenteId() != null) {
            Docente docente = docenteRepository.findById(materiaDTO.getDocenteId())
                    .orElseThrow(() -> new IllegalArgumentException("Docente no encontrado"));
            materia.setDocente(docente);
        } else {
            materia.setDocente(null);
        }
        
        Materia updatedMateria = materiaRepository.save(materia);
        return mapToDTO(updatedMateria);
    }

    @Override
    @Transactional
    @CachePut(value = "materia", key = "#materiaId")
    public MateriaDTO asignarDocenteAMateria(Long materiaId, Long docenteId) {
        Materia materia = materiaRepository.findById(materiaId)
                .orElseThrow(() -> new IllegalArgumentException("Materia no encontrada"));
        
        Docente docente = docenteRepository.findById(docenteId)
                .orElseThrow(() -> new IllegalArgumentException("Docente no encontrado"));
        
        materia.setDocente(docente);
        Materia materiaActualizada = materiaRepository.save(materia);
        return mapToDTO(materiaActualizada);
    }

    @Override
    @Cacheable(value = "materiasPorDocente", key = "#docenteId")
    public List<MateriaDTO> obtenerMateriasPorDocente(Long docenteId) {
        return materiaRepository.findByDocenteId(docenteId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    //FRONTEND
    @Override
    public List<Materia> listarMaterias() {
        return materiaRepository.findAll();
    }
}
