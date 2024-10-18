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

import com.example.microserviciofichasmedicas.model.ConsultaMedicaEntity;
import com.example.microserviciofichasmedicas.model.FichasMedicasEntity;
import com.example.microserviciofichasmedicas.model.UsuarioEntity;
import com.example.microserviciofichasmedicas.model.dtos.ConsultaMedicaDto;
import com.example.microserviciofichasmedicas.repository.ConsultaMedicaRepositoryJPA;
import com.example.microserviciofichasmedicas.repository.UsuarioRepositoryJPA;
import com.example.microserviciofichasmedicas.service.ConsultasMedicasService;
import com.example.microserviciofichasmedicas.service.ContainerMetadataService;

import jakarta.annotation.security.PermitAll;

@RestController
@RequestMapping(path = "/fichas-medicas")
public class ConsultasMedicasController {
    @Autowired
    ContainerMetadataService containerMetadataService;
    @Autowired
    ConsultasMedicasService consultasMedicasService;

    // @PostMapping()
    // public @ResponseBody String registrarReserva(@RequestBody FichasMedicasEntity nuevo){
    //     fichasMedicasRepositoryJPA.save(nuevo);
    //     return "Ok";
    // }
    // @PostMapping()
    // @PermitAll
    // public @ResponseBody ConsultaMedicaDto registrarReserva(@RequestBody ConsultaMedicaDto datos){
    //     FichasMedicasEntity fichasMedicasEntity = new FichasMedicasEntity();
    //     fichasMedicasEntity.setIdTurnoAtencionMedica(datos.getIdTurnoAtencionMedica());
    //     UsuarioEntity paciente=pacientesRepositoryJPA.findByEmail(datos.getEmail())
    //     .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));;
    //     fichasMedicasEntity.setIdPaciente(paciente.getIdUsuario());
    //     fichasMedicasEntity.setCodigoFichaMedica("nuevo");
    //     fichasMedicasEntity.setNumeroFicha(1);
    //     fichasMedicasRepositoryJPA.save(fichasMedicasEntity);
    //     return datos;
    // }
    @GetMapping("/paciente/{idPaciente}")
    @PermitAll
    public ResponseEntity<List<ConsultaMedicaDto>> controllerMethod(@PathVariable int idPaciente) {
        try{
            List<ConsultaMedicaDto> consultasMedicas = consultasMedicasService.obtenerConsultasMedicasPaciente(idPaciente);
            return new ResponseEntity<>(consultasMedicas,HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }   
    }
    @GetMapping("/detalle/paciente/{idPaciente}")
    @PermitAll
    public ResponseEntity<List<ConsultaMedicaDto>> obtenerFichasMedicasPacienteDetalle(@PathVariable int idPaciente) {
        try{
            List<ConsultaMedicaDto> consultasMedicas = consultasMedicasService.obtenerConsultasMedicasPaciente(idPaciente);
            return new ResponseEntity<>(consultasMedicas,HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }   
    }
    @GetMapping("/info-container")
    public @ResponseBody String obtenerInformacionContenedor() {
        return "microservicio fichas medicas:" + containerMetadataService.retrieveContainerMetadataInfo();
    }
    @GetMapping("/medico/{idMedico}")
    public ResponseEntity<List<ConsultaMedicaDto>> obtenerConsultasMedicasPorMedico(@PathVariable int idMedico) {
        List<ConsultaMedicaDto> consultas = consultasMedicasService.obtenerConsultasMedicasPorMedico(idMedico);
        return ResponseEntity.ok(consultas);
    }

    @PostMapping
    public ResponseEntity<ConsultaMedicaDto> crearConsultaMedica(@RequestBody ConsultaMedicaDto consultaMedicaDto) {
        ConsultaMedicaDto consultaCreada = consultasMedicasService.crearConsultaMedica(consultaMedicaDto);
        return ResponseEntity.ok().body(consultaCreada);
    }

    @DeleteMapping("/{idConsultaMedica}")
    public ResponseEntity<ConsultaMedicaDto> eliminarConsultaMedica(@PathVariable int idConsultaMedica) {
        try{
            consultasMedicasService.eliminarConsultaMedica(idConsultaMedica);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{idConsultaMedica}")
    public ResponseEntity<ConsultaMedicaDto> obtenerConsultaMedicaPorId(@PathVariable int idConsultaMedica) {
        ConsultaMedicaDto consulta = consultasMedicasService.obtenerConsultaMedicaPorId(idConsultaMedica);
        return ResponseEntity.ok().body(consulta);
    }
    // @GetMapping("/{idFichaMedica}")
    // @PermitAll
    // public @ResponseBody List<Object>  obtenerInformacionContenedor(@PathVariable int idFichaMedica) {
    //     return fichasMedicasRepositoryJPA.obtenerDetalleFichaMedica(idFichaMedica);
    // }
    // @GetMapping("/medico/{idMedico}")
    // @PermitAll
    // public @ResponseBody List<Object>  obtenerFichasMedicaPorIdMedico(@PathVariable int idMedico) {
    //     return fichasMedicasRepositoryJPA.obtenerFichasMedicasPorIdMedico(idMedico);
    // }
    // @DeleteMapping("/{idFichaMedica}")
    // @PermitAll
    // public @ResponseBody ResponseEntity<Object> eliminarInformacionContenedorFichaMedica(@PathVariable int idFichaMedica) {
    //     fichasMedicasRepositoryJPA.deleteById(idFichaMedica);
    //     Map<String, String> responseMap = new HashMap<>();
    //     responseMap.put("resp", "OK");
    //     return new ResponseEntity<Object>(responseMap, HttpStatus.OK);
    // }
}
