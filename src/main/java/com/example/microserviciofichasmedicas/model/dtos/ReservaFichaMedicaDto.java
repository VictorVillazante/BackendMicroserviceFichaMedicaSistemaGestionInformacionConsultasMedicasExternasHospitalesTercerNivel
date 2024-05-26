package com.example.microserviciofichasmedicas.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservaFichaMedicaDto {
    int idTurnoAtencionMedica;
    String email;
}
