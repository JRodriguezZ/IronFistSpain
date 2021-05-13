package com.renegade.ironfistspain;

public class Rango {
    String nombre;
    String imagenUrl;
    String puntuacion;

    public Rango(String nombre, String imagenUrl, String puntuacion) {
        this.nombre = nombre;
        this.imagenUrl = imagenUrl;
        this.puntuacion = puntuacion;
    }

    public String getNombre() {
        return nombre;
    }
}
