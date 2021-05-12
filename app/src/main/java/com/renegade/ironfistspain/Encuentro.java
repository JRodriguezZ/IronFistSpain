package com.renegade.ironfistspain;

import java.time.LocalDateTime;

public class Encuentro {
    String enlace;
    String estado;
    String jugadorLocal, jugadorVisitante;
    String resultadoLocal, resultadoVisitante;
    LocalDateTime fechaEncuentro, fechaPeticion;

    public Encuentro(String estado, String jugadorLocal, String jugadorVisitante) {
        this.estado = estado;
        this.jugadorLocal = jugadorLocal;
        this.jugadorVisitante = jugadorVisitante;
        this.fechaPeticion = LocalDateTime.now();
    }

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getJugadorLocal() {
        return jugadorLocal;
    }

    public void setJugadorLocal(String jugadorLocal) {
        this.jugadorLocal = jugadorLocal;
    }

    public String getJugadorVisitante() {
        return jugadorVisitante;
    }

    public void setJugadorVisitante(String jugadorVisitante) {
        this.jugadorVisitante = jugadorVisitante;
    }

    public String getResultadoLocal() {
        return resultadoLocal;
    }

    public void setResultadoLocal(String resultadoLocal) {
        this.resultadoLocal = resultadoLocal;
    }

    public String getResultadoVisitante() {
        return resultadoVisitante;
    }

    public void setResultadoVisitante(String resultadoVisitante) {
        this.resultadoVisitante = resultadoVisitante;
    }

    public LocalDateTime getFechaEncuentro() {
        return fechaEncuentro;
    }

    public void setFechaEncuentro(LocalDateTime fechaEncuentro) {
        this.fechaEncuentro = fechaEncuentro;
    }

    public LocalDateTime getFechaPeticion() {
        return fechaPeticion;
    }

    public void setFechaPeticion(LocalDateTime fechaPeticion) {
        this.fechaPeticion = fechaPeticion;
    }
}
