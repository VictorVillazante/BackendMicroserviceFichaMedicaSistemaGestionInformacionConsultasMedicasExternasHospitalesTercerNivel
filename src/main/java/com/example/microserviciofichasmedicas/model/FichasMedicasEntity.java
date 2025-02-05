package com.example.microserviciofichasmedicas.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "consultas_medicas")
public class FichasMedicasEntity {
    @Id
    @Column(name = "id_consulta_medica")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idConsultaMedica;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente", nullable = false)
    private UsuarioEntity paciente;
    @Column(name = "codigo_ficha_medica")
    private String codigoFichaMedica;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_turno_atencion_medica", nullable = false)
    private TurnosAtencionMedicaEntity turnosAtencionMedica;
    @Column(name = "numero_ficha")
    private int numeroFicha;


  
}
