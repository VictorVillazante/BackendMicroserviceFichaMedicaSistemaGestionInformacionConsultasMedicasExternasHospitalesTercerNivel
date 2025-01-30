package com.example.microserviciofichasmedicas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.microserviciofichasmedicas.model.dtos.RequisitoPrevioConsultaMedicaDto;
import com.example.microserviciofichasmedicas.service.RequisitosPreviosConsultaMedicaService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/v1.0/requisitos-previos-consulta-medica")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,RequestMethod.OPTIONS})
public class RequisitosPreviosConsultaMedicaController {

    // @Autowired
    // private RequisitosPreviosConsultaMedicaService requisitosPreviosConsultaMedicaService;

    // private final ObjectMapper objectMapper = new ObjectMapper();

    // @GetMapping
    // public ResponseEntity<List<RequisitoPrevioConsultaMedicaDto>> obtenerRequisitosPrevios() {
    //     try {
    //         List<RequisitoPrevioConsultaMedicaDto> requisitos = requisitosPreviosConsultaMedicaService.obtenerRequisitosPrevios();
    //         return new ResponseEntity<>(requisitos, HttpStatus.OK);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

    // @GetMapping("/{id}")
    // public ResponseEntity<RequisitoPrevioConsultaMedicaDto> obtenerRequisitoPorId(@PathVariable int id) {
    //     try {
    //         RequisitoPrevioConsultaMedicaDto requisito = requisitosPreviosConsultaMedicaService.obtenerRequisitoPorId(id);
    //         return new ResponseEntity<>(requisito, HttpStatus.OK);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //     }
    // }

    // @GetMapping("/consultas-medicas/{idConsultaMedica}")
    // public ResponseEntity<List<RequisitoPrevioConsultaMedicaDto>> obtenerRequisitosPorConsultaMedica(@PathVariable int idConsultaMedica) {
    //     try {
    //         List<RequisitoPrevioConsultaMedicaDto> requisitos = requisitosPreviosConsultaMedicaService.obtenerRequisitosPreviosPorIdConsultaMedica(idConsultaMedica);
    //         return new ResponseEntity<>(requisitos, HttpStatus.OK);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //     }
    // }

    // @PostMapping
    // public ResponseEntity<RequisitoPrevioConsultaMedicaDto> crearRequisito(
    //         @RequestParam("data") String data,
    //         @RequestParam("file") MultipartFile file) {

    //     try {
    //         RequisitoPrevioConsultaMedicaDto requisitoDto = objectMapper.readValue(data, RequisitoPrevioConsultaMedicaDto.class);
    //         RequisitoPrevioConsultaMedicaDto requisitoCreado = requisitosPreviosConsultaMedicaService.crearRequisito(requisitoDto, file);
    //         return new ResponseEntity<>(requisitoCreado, HttpStatus.CREATED);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    //     }
    // }

    // @PutMapping("/{id}")
    // public ResponseEntity<RequisitoPrevioConsultaMedicaDto> actualizarRequisito(
    //         @PathVariable int id,
    //         @RequestParam("data") String data,
    //         @RequestParam("file") MultipartFile file) {

    //     try {
    //         RequisitoPrevioConsultaMedicaDto requisitoDto = objectMapper.readValue(data, RequisitoPrevioConsultaMedicaDto.class);
    //         RequisitoPrevioConsultaMedicaDto requisitoActualizado = requisitosPreviosConsultaMedicaService.actualizarRequisito(id, requisitoDto, file);
    //         return new ResponseEntity<>(requisitoActualizado, HttpStatus.OK);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    //     }
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> eliminarRequisito(@PathVariable int id) {
    //     try {
    //         requisitosPreviosConsultaMedicaService.eliminarRequisito(id);
    //         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //     }
    // }
}