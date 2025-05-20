package com.universidad.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InscripcionDTO {
    private Long id;
    private Long estudianteId;
    private Long materiaId;
    private LocalDateTime fechaInscripcion;
    private String estado;
    private Double calificacion;
}
