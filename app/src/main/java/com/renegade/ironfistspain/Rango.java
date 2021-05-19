package com.renegade.ironfistspain;

public class Rango {
    String nombre;
    String imagenUrl;
    Long puntuacion;

    public Rango(String nombre, String imagenUrl, Long puntuacion) {
        this.nombre = nombre;
        this.imagenUrl = imagenUrl;
        this.puntuacion = puntuacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public Long getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Long puntuacion) {
        this.puntuacion = puntuacion;
    }
}
