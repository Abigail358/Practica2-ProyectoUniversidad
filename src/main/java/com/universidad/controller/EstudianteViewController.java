package com.universidad.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.universidad.model.Estudiante;
import com.universidad.service.IEstudianteService;

@Controller
public class EstudianteViewController {
     @Autowired
    private IEstudianteService estudianteService;

    @GetMapping("/vista-estudiantes")
    public String verEstudiantes(Model model) {
        List<Estudiante> estudiantes = estudianteService.listarEstudiantes();
        model.addAttribute("estudiantes", estudiantes);
        return "estudiantes";
    }
}
