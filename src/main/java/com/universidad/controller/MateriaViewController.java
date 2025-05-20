package com.universidad.controller;

import com.universidad.model.Materia;
import com.universidad.service.IMateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MateriaViewController {

    @Autowired
    private IMateriaService materiaService;

    @GetMapping("/vista-materias")
    public String verMaterias(Model model) {
        List<Materia> materias = materiaService.listarMaterias();
        model.addAttribute("materias", materias);
        return "materias";
    }
}
