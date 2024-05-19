package com.example.microserviciofichasmedicas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.microserviciofichasmedicas.model.FichasMedicasEntity;
import com.example.microserviciofichasmedicas.repository.FichasMedicasRepositoryJPA;

@Controller
@RequestMapping(path = "fichas-medicas")
public class PeticionFichasMedicas {
    @Autowired
    FichasMedicasRepositoryJPA fichasMedicasRepositoryJPA;
    
    @PostMapping()
    public @ResponseBody String registrarReserva(@RequestBody FichasMedicasEntity nuevo){
        fichasMedicasRepositoryJPA.save(nuevo);
        return "Ok";
    }
    // @GetMapping()
    // public @ResponseBody List<Object> controllerMethod(@RequestParam(value="id") String id) {
    //     return fichasMedicasRepositoryJPA.obtenerTodosDatosConsulta(id);
    // }
}
