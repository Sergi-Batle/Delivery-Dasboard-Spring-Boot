package dev.sergi.jte.controllers;

import dev.sergi.jte.classes.Repartidor;
import dev.sergi.jte.ConnectionDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import java.util.List;


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
