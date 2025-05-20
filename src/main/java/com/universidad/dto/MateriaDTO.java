package com.universidad.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MateriaDTO implements Serializable {
    
    private Long id;
    private String nombreMateria;
    private String codigoUnico;
    private Integer creditos;
    private List<Long> prerequisitos;
    private List<Long> esPrerequisitoDe;
    private Long docenteId; 
}