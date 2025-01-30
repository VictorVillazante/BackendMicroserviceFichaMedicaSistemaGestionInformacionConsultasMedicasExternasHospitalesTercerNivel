package com.example.microserviciofichasmedicas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.microserviciofichasmedicas.model.dtos.ConsultaMedicaDto;
import com.example.microserviciofichasmedicas.service.ConsultasMedicasService;
import com.example.microserviciofichasmedicas.service.ContainerMetadataService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import jakarta.annotation.security.PermitAll;

@RestController
@RequestMapping(path = "/fichas-medicas")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,RequestMethod.OPTIONS})
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
    @GetMapping("/paciente/{idPaciente}")
    @PermitAll
    public ResponseEntity<List<ConsultaMedicaDto>> controllerMethod(@PathVariable String idPaciente,@RequestParam(required = false) String fechaInicio,@RequestParam(required = false) String fechaFin,@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer size) {
        List<ConsultaMedicaDto> consultasMedicas = consultasMedicasService.obtenerConsultasMedicasPaciente(idPaciente,fechaInicio,fechaFin,page,size);
        return new ResponseEntity<>(consultasMedicas,HttpStatus.OK);
    }
   
    @GetMapping("/info-container")
    @PermitAll
    public @ResponseBody String obtenerInformacionContenedor() {
        return "microservicio fichas medicas:" + containerMetadataService.retrieveContainerMetadataInfo();
    }
    @GetMapping("/medico/{idMedico}")
    @PreAuthorize("hasAnyAuthority('SUPERUSUARIO','ADMINISTRADOR','MEDICO')")
    public ResponseEntity<List<ConsultaMedicaDto>> obtenerConsultasMedicasPorMedico(@PathVariable String idMedico,@RequestParam(required = false) String fechaInicio,@RequestParam(required = false) String fechaFin,@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer size) {
        List<ConsultaMedicaDto> consultas = consultasMedicasService.obtenerConsultasMedicasPorMedico(idMedico,fechaInicio,fechaFin,page,size);
        return ResponseEntity.ok(consultas); 
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('SUPERUSUARIO','ADMINISTRADOR','PACIENTE')")
    public ResponseEntity<ConsultaMedicaDto> crearConsultaMedica(@RequestBody ConsultaMedicaDto consultaMedicaDto) throws JsonMappingException, JsonProcessingException {
        ConsultaMedicaDto consultaCreada = consultasMedicasService.crearConsultaMedica(consultaMedicaDto);
        return ResponseEntity.ok().body(consultaCreada);
    }
    @PutMapping("/rechazar-peticiones-fuera-fecha")
    @PreAuthorize("hasAnyAuthority('SUPERUSUARIO','ADMINISTRADOR')")
    public ResponseEntity<Void> putMethodName() {
        consultasMedicasService.rechazarPeticionesFueraFecha();
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/{idConsultaMedica}/estado")
    @PermitAll
    public ResponseEntity<ConsultaMedicaDto> cambiarEstadoConsultaMedica(@PathVariable int idConsultaMedica,@RequestBody ConsultaMedicaDto consultaMedicaDto) {
        ConsultaMedicaDto consultaActualizada = consultasMedicasService.actualizarEstadoConsultaMedica(idConsultaMedica,consultaMedicaDto);
        return new ResponseEntity<>(HttpStatus.OK);
        
    }
    @DeleteMapping("/{idConsultaMedica}")
    @PreAuthorize("hasAnyAuthority('SUPERUSUARIO','ADMINISTRADOR','PACIENTE')")
    public ResponseEntity<ConsultaMedicaDto> eliminarConsultaMedica(@PathVariable int idConsultaMedica) throws Exception {
        consultasMedicasService.eliminarConsultaMedica(idConsultaMedica);
        return new ResponseEntity<>(HttpStatus.OK);

    }
    @DeleteMapping("/turnos-atencion-medica/{idTurnoAtencionMedica}")
    @PreAuthorize("hasAnyAuthority('SUPERUSUARIO','ADMINISTRADOR')")
    public ResponseEntity<ConsultaMedicaDto> eliminarConsultasDeTurnoAtencionMedica(@PathVariable int idTurnoAtencionMedica) throws Exception {
        consultasMedicasService.eliminarConsultasDeTurnoAtencionMedica(idTurnoAtencionMedica);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/{idConsultaMedica}")
    @PermitAll
    public ResponseEntity<ConsultaMedicaDto> obtenerConsultaMedicaPorId(@PathVariable int idConsultaMedica) throws Exception {
        ConsultaMedicaDto consulta = consultasMedicasService.obtenerConsultaMedicaPorId(idConsultaMedica);
        return ResponseEntity.ok().body(consulta);
    }
    @GetMapping("consulta-en-curso/{idConsultaMedica}")
    @PermitAll
    public ResponseEntity<ConsultaMedicaDto> obtenerConsultaEnCurso(@PathVariable int idConsultaMedica) {
        ConsultaMedicaDto consulta = consultasMedicasService.obtenerConsultaEnCurso(idConsultaMedica);
        return ResponseEntity.ok().body(consulta);
    }

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('SUPERUSUARIO','ADMINISTRADOR')")
    public ResponseEntity<Page<ConsultaMedicaDto>> obtenerConsultasMedicas(@RequestParam(required = false) String nombreEspecialidad,@RequestParam(required = false) String nombreMedico,@RequestParam(required = false) String nombrePaciente,@RequestParam(required = false) String hora,@RequestParam(required = false) String fechaFin,@RequestParam(required = false) String fechaInicio,@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer size) {
        Page<ConsultaMedicaDto> consultas = consultasMedicasService.obtenerConsultasMedicas(nombreEspecialidad,nombreMedico,nombrePaciente,fechaInicio,fechaFin,hora,page,size);
        return new ResponseEntity<>(consultas,HttpStatus.OK);  
    }
    @GetMapping("/info")
    @PermitAll
    public ResponseEntity<String> ultimaActualizacion() {
        return new ResponseEntity<String>("012922025",HttpStatus.OK);
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
