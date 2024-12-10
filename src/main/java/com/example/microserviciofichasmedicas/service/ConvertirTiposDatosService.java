package com.example.microserviciofichasmedicas.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConvertirTiposDatosService {
    @Value("${aws.s3.bucket.name}")
    private String bucketName;
    public String convertirKeyImagenAEnlaceImagen(String key){
        return String.format("https://%s.s3.amazonaws.com/%s", bucketName, key);
    }
    public String arreglarFileName(String fileName){
        return System.currentTimeMillis() + "_" + fileName.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
    public Date convertirStringADate(String fecha) {
        if (fecha == null) {
            return null;
        }
        Date fechaDate;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
         try {
            fechaDate = format.parse(fecha);
            return fechaDate;

        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al convertir la fecha.");
        }
    }
      public LocalTime convertirStringALocalTime(String hora) {
        if (hora == null) {
            return null;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return LocalTime.parse(hora, formatter);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al convertir la hora.");
        }
    }
}
