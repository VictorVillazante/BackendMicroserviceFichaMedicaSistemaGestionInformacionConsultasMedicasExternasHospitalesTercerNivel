package com.example.microserviciofichasmedicas.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
public class ConsultaMedicaEntity {
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
    private TurnosAtencionMedicaEntity turnoAtencionMedica;
    @Column(name = "numero_ficha")
    private int numeroFicha;
    @Column(name = "estado")
    private String estado;

     @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_at")
    private Date deletedAt;

    
    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }
    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
    public void markAsDeleted() {
        deletedAt = new Date();
    }
}
