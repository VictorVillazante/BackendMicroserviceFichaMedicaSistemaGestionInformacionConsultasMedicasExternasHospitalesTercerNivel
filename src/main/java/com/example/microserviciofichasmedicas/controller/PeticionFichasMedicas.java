package com.example.microserviciofichasmedicas.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.microserviciofichasmedicas.model.FichasMedicasEntity;
import com.example.microserviciofichasmedicas.model.PacienteEntity;
import com.example.microserviciofichasmedicas.model.dtos.ReservaFichaMedicaDto;
import com.example.microserviciofichasmedicas.repository.FichasMedicasRepositoryJPA;
import com.example.microserviciofichasmedicas.repository.PacientesRepositoryJPA;
import com.example.microserviciofichasmedicas.service.ContainerMetadataService;

import jakarta.annotation.security.PermitAll;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/fichas-medicas")
public class PeticionFichasMedicas {
    @Autowired
    FichasMedicasRepositoryJPA fichasMedicasRepositoryJPA;
    @Autowired
    ContainerMetadataService containerMetadataService;
    @Autowired
    PacientesRepositoryJPA pacientesRepositoryJPA;
    // @PostMapping()
    // public @ResponseBody String registrarReserva(@RequestBody FichasMedicasEntity nuevo){
    //     fichasMedicasRepositoryJPA.save(nuevo);
    //     return "Ok";
    // }
    @PostMapping()
    @PermitAll
    public @ResponseBody String registrarReserva(@RequestBody ReservaFichaMedicaDto datos){
        FichasMedicasEntity fichasMedicasEntity = new FichasMedicasEntity();
        fichasMedicasEntity.setIdTurnoAtencionMedica(datos.getIdTurnoAtencionMedica());
        PacienteEntity paciente=pacientesRepositoryJPA.findByEmail(datos.getEmail())
        .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));;
        fichasMedicasEntity.setIdPaciente(paciente.getIdPaciente());
        fichasMedicasEntity.setCodigoFichaMedica("nuevo");
        fichasMedicasEntity.setNumeroFicha(1);
        fichasMedicasRepositoryJPA.save(fichasMedicasEntity);
        return "Ok";
    }
    @GetMapping("/paciente/{idPaciente}")
    @PermitAll
    public @ResponseBody List<FichasMedicasEntity> controllerMethod(@PathVariable int idPaciente) {
        return fichasMedicasRepositoryJPA.findByIdPaciente(idPaciente);
    }
    @GetMapping("/info-container")
    public @ResponseBody String obtenerInformacionContenedor() {
        return "microservicio historias clinicas:" + containerMetadataService.retrieveContainerMetadataInfo();
    }
    @GetMapping("/{idFichaMedica}")
    public @ResponseBody List<Object>  obtenerInformacionContenedor(@PathVariable int idFichaMedica) {
        return fichasMedicasRepositoryJPA.obtenerDetalleFichaMedica(idFichaMedica);
    }
    @DeleteMapping("/{idFichaMedica}")
    @PermitAll
    public @ResponseBody ResponseEntity<Object> eliminarInformacionContenedorFichaMedica(@PathVariable int idFichaMedica) {
        fichasMedicasRepositoryJPA.deleteById(idFichaMedica);
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("resp", "OK");
        return new ResponseEntity<Object>(responseMap, HttpStatus.OK);
    }
}
