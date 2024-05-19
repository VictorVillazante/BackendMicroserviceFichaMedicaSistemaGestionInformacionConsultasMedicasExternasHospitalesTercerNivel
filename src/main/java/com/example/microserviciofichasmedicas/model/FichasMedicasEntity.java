package com.example.microserviciofichasmedicas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "consultas_medicas")
public class FichasMedicasEntity {
    @Id
    @Column
    private int id_consultas;
    @Column
    private String id_paciente;
}
