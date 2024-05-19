package com.example.microserviciofichasmedicas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    @Column
    private int id_consulta_medica;
    @Column
    private int id_paciente;
    @Column
    private int id_medico;
    @Column
    private int id_especialidad;
    @Column
    private int id_consultorio;
    @Column
    private String codigo_ficha_medica;
}
