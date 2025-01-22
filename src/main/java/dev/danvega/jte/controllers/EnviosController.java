package dev.danvega.jte.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import dev.danvega.jte.classes.Envio;

@Controller
public class EnviosController {
    List<Envio> envios;
    
    @GetMapping("/projects")
    public String projects(Model model) {
        
        

        List<String> projects = List.of("Project 1", "Project 2", "Project 3");
 
        model.addAttribute("projects",projects);
        return "pages/projects";
    }
}
