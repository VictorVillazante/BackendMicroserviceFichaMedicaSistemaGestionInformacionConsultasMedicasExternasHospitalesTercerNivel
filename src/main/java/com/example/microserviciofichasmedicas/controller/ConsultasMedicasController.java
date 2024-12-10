package com.example.microserviciofichasmedicas.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<List<ConsultaMedicaDto>> controllerMethod(@PathVariable String idPaciente,@RequestParam(required = false) String fechaInicio,@RequestParam(required = false) String fechaFin,@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer size) {
        try{
            List<ConsultaMedicaDto> consultasMedicas = consultasMedicasService.obtenerConsultasMedicasPaciente(idPaciente,fechaInicio,fechaFin,page,size);
            return new ResponseEntity<>(consultasMedicas,HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }   
    }
    // @GetMapping("/detalle/paciente/{idPaciente}")
    // @PermitAll
    // public ResponseEntity<List<ConsultaMedicaDto>> obtenerFichasMedicasPacienteDetalle(@PathVariable int idPaciente) {
    //     try{
    //         List<ConsultaMedicaDto> consultasMedicas = consultasMedicasService.obtenerConsultasMedicasPaciente(idPaciente);
    //         return new ResponseEntity<>(consultasMedicas,HttpStatus.OK);
    //     }catch(Exception e){
    //         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //     }   
    // }
    @GetMapping("/info-container")
    @PermitAll
    public @ResponseBody String obtenerInformacionContenedor() {
        return "microservicio fichas medicas:" + containerMetadataService.retrieveContainerMetadataInfo();
    }
    @GetMapping("/medico/{idMedico}")
    @PermitAll
    public ResponseEntity<List<ConsultaMedicaDto>> obtenerConsultasMedicasPorMedico(@PathVariable String idMedico,@RequestParam(required = false) String fechaInicio,@RequestParam(required = false) String fechaFin,@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer size) {
        try {
            List<ConsultaMedicaDto> consultas = consultasMedicasService.obtenerConsultasMedicasPorMedico(idMedico,fechaInicio,fechaFin,page,size);
            return ResponseEntity.ok(consultas);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @PostMapping
    @PermitAll
    public ResponseEntity<ConsultaMedicaDto> crearConsultaMedica(@RequestBody ConsultaMedicaDto consultaMedicaDto) {
        try {
            ConsultaMedicaDto consultaCreada = consultasMedicasService.crearConsultaMedica(consultaMedicaDto);
            return ResponseEntity.ok().body(consultaCreada);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PutMapping("/rechazar-peticiones-fuera-fecha")
    @PermitAll
    public ResponseEntity<Void> putMethodName() {
        try {
            consultasMedicasService.rechazarPeticionesFueraFecha();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{idConsultaMedica}/estado")
    @PermitAll
    public ResponseEntity<ConsultaMedicaDto> cambiarEstadoConsultaMedica(@PathVariable int idConsultaMedica,@RequestBody ConsultaMedicaDto consultaMedicaDto) {
        try {
            ConsultaMedicaDto consultaActualizada = consultasMedicasService.actualizarEstadoConsultaMedica(idConsultaMedica,consultaMedicaDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{idConsultaMedica}")
    @PermitAll
    public ResponseEntity<ConsultaMedicaDto> eliminarConsultaMedica(@PathVariable int idConsultaMedica) {
        try{
            consultasMedicasService.eliminarConsultaMedica(idConsultaMedica);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{idConsultaMedica}")
    @PermitAll
    public ResponseEntity<ConsultaMedicaDto> obtenerConsultaMedicaPorId(@PathVariable int idConsultaMedica) {
        try {
            ConsultaMedicaDto consulta = consultasMedicasService.obtenerConsultaMedicaPorId(idConsultaMedica);
            return ResponseEntity.ok().body(consulta);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
    @GetMapping("consulta-en-curso/{idConsultaMedica}")
    @PermitAll
    public ResponseEntity<ConsultaMedicaDto> obtenerConsultaEnCurso(@PathVariable int idConsultaMedica) {
        try {
            ConsultaMedicaDto consulta = consultasMedicasService.obtenerConsultaEnCurso(idConsultaMedica);
            return ResponseEntity.ok().body(consulta);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @GetMapping("")
    @PermitAll
    public ResponseEntity<Page<ConsultaMedicaDto>> obtenerConsultasMedicas(@RequestParam(required = false) String nombreEspecialidad,@RequestParam(required = false) String nombreMedico,@RequestParam(required = false) String nombrePaciente,@RequestParam(required = false) String hora,@RequestParam(required = false) String fechaFin,@RequestParam(required = false) String fechaInicio,@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer size) {
        try {
            Page<ConsultaMedicaDto> consultas = consultasMedicasService.obtenerConsultasMedicas(nombreEspecialidad,nombreMedico,nombrePaciente,fechaInicio,fechaFin,hora,page,size);
            return new ResponseEntity<>(consultas,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
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
