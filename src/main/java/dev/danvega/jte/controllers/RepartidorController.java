package dev.danvega.jte.controllers;

import dev.danvega.jte.classes.Repartidor;
import dev.danvega.jte.ConnectionDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class RepartidorController {

    private final ConnectionDB connectionDB;
    private static List<Repartidor> repartidores;
    
    @Autowired
    public RepartidorController(ConnectionDB connectionDB) {
        this.connectionDB = connectionDB;
    }

    @GetMapping("/team")
    public String getRepartidores(Model model) {
        repartidores = connectionDB.getRepartidores();
        model.addAttribute("repartidores", repartidores);
        return "pages/team";
    }


}
