package com.example.microserviciofichasmedicas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "consultas_medicas")
public class FichasMedicasEntity {
    @Id
    @Column(name = "id_consulta_medica")
    private int idConsultaMedica;
    @Column(name = "id_paciente")
    private int idPaciente;
    @Column(name = "id_medico")
    private int idMedico;

    @Column(name = "id_especialidad")
    private int idEspecialidad;

    @Column(name = "id_consultorio")
    private int idConsultorio;

    @Column(name = "codigo_ficha_medica")
    private String codigoFichaMedica;
}
