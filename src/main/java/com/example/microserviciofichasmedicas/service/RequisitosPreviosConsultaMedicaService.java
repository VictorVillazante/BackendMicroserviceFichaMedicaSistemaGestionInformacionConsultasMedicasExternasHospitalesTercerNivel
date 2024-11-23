package com.example.microserviciofichasmedicas.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.microserviciofichasmedicas.model.ConsultaMedicaEntity;
import com.example.microserviciofichasmedicas.model.RequisitoPrevioConsultaMedicaEntity;
import com.example.microserviciofichasmedicas.model.dtos.RequisitoPrevioConsultaMedicaDto;
import com.example.microserviciofichasmedicas.repository.ConsultaMedicaRepositoryJPA;
import com.example.microserviciofichasmedicas.repository.RequisitosPreviosConsultaMedicaRepositoryJPA;

@Service
public class RequisitosPreviosConsultaMedicaService {
    @Autowired
    private ConverterVariablesService converterVariablesService;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private RequisitosPreviosConsultaMedicaRepositoryJPA requisitosPreviosConsultaMedicaRepositoryJPA;

    @Autowired
    private ConsultaMedicaRepositoryJPA consultaMedicaRepositoryJPA;
    public List<RequisitoPrevioConsultaMedicaDto> obtenerRequisitosPrevios() {
        List<RequisitoPrevioConsultaMedicaEntity> requisitos = requisitosPreviosConsultaMedicaRepositoryJPA.findAllByDeletedAtIsNull();
        return requisitos.stream()
                         .map(requisito -> new RequisitoPrevioConsultaMedicaDto().convertirRequisitoPrevioConsultaMedicaEntityARequisitoPrevioConsultaMedicaDto(requisito))
                         .toList();
    }
    public List<RequisitoPrevioConsultaMedicaDto> obtenerRequisitosPreviosPorIdConsultaMedica(int idConsultaMedica) {
        ConsultaMedicaEntity consultaMedicaEntity = consultaMedicaRepositoryJPA.findById(idConsultaMedica)
        .orElseThrow(() -> new RuntimeException("Consulta medica no encontrada"));


        List<RequisitoPrevioConsultaMedicaEntity> requisitos = requisitosPreviosConsultaMedicaRepositoryJPA.findAllByConsultaMedicaAndDeletedAtIsNull(consultaMedicaEntity);
        return requisitos.stream()
                         .map(requisito -> new RequisitoPrevioConsultaMedicaDto().convertirRequisitoPrevioConsultaMedicaEntityARequisitoPrevioConsultaMedicaDto(requisito))
                         .toList();
    }
    public RequisitoPrevioConsultaMedicaDto obtenerRequisitoPorId(int id) {
        RequisitoPrevioConsultaMedicaEntity requisito = requisitosPreviosConsultaMedicaRepositoryJPA.findById(id)
                .orElseThrow(() -> new RuntimeException("Requisito previo no encontrado"));
        return new RequisitoPrevioConsultaMedicaDto().convertirRequisitoPrevioConsultaMedicaEntityARequisitoPrevioConsultaMedicaDto(requisito);
    }

    public RequisitoPrevioConsultaMedicaDto crearRequisito(RequisitoPrevioConsultaMedicaDto requisitoDto,MultipartFile file) throws IOException {
        ConsultaMedicaEntity consultaMedicaEntity = consultaMedicaRepositoryJPA.findById(requisitoDto.getIdConsultaMedica())
        .orElseThrow(() -> new RuntimeException("Consulta medica no encontrada"));

        RequisitoPrevioConsultaMedicaEntity requisitoEntity = new RequisitoPrevioConsultaMedicaEntity();
        requisitoEntity.setTitulo(requisitoDto.getTitulo());
        requisitoEntity.setDescripcion(requisitoDto.getDescripcion());
        requisitoEntity.setConsultaMedica(consultaMedicaEntity);
        String key = s3Service.uploadFileRequisitoPrevioConsultaMedica(file);        
        requisitoEntity.setKeyArchivo(key);
        requisitoEntity.setEnlace(converterVariablesService.convertirKeyImagenAEnlaceImagen(key));

        RequisitoPrevioConsultaMedicaEntity savedEntity = requisitosPreviosConsultaMedicaRepositoryJPA.save(requisitoEntity);
        return new RequisitoPrevioConsultaMedicaDto().convertirRequisitoPrevioConsultaMedicaEntityARequisitoPrevioConsultaMedicaDto(savedEntity);
    }

    public RequisitoPrevioConsultaMedicaDto actualizarRequisito(int id, RequisitoPrevioConsultaMedicaDto requisitoDto,MultipartFile file) throws IOException {
        ConsultaMedicaEntity consultaMedicaEntity = consultaMedicaRepositoryJPA.findById(requisitoDto.getIdConsultaMedica())
                .orElseThrow(() -> new RuntimeException("Consulta medica no encontrada"));
        RequisitoPrevioConsultaMedicaEntity requisitoEntity = requisitosPreviosConsultaMedicaRepositoryJPA.findById(id)
                .orElseThrow(() -> new RuntimeException("Requisito previo no encontrado"));
        if(!converterVariablesService.arreglarFileName("requisitos-previo-consulta-medica/"+file.getOriginalFilename()).equals(requisitoEntity.getKeyArchivo())){
            s3Service.deleteFile(requisitoEntity.getKeyArchivo());
            String key = s3Service.uploadFileRequisitoPrevioConsultaMedica(file);
            requisitoEntity.setKeyArchivo(key);
            requisitoEntity.setEnlace(converterVariablesService.convertirKeyImagenAEnlaceImagen(key));
        }
        requisitoEntity.setTitulo(requisitoDto.getTitulo());
        requisitoEntity.setDescripcion(requisitoDto.getDescripcion());
        requisitoEntity.setConsultaMedica(consultaMedicaEntity);
        RequisitoPrevioConsultaMedicaEntity updatedEntity = requisitosPreviosConsultaMedicaRepositoryJPA.save(requisitoEntity);
        return new RequisitoPrevioConsultaMedicaDto().convertirRequisitoPrevioConsultaMedicaEntityARequisitoPrevioConsultaMedicaDto(updatedEntity);
    }

    public void eliminarRequisito(int id) {
        RequisitoPrevioConsultaMedicaEntity requisitoEntity = requisitosPreviosConsultaMedicaRepositoryJPA.findById(id)
                .orElseThrow(() -> new RuntimeException("Requisito previo no encontrado"));
        requisitoEntity.markAsDeleted();
        requisitosPreviosConsultaMedicaRepositoryJPA.save(requisitoEntity);
    }
}