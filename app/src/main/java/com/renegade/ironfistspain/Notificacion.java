package com.renegade.ironfistspain;

import java.util.List;

public class Notificacion {
    String rangoHoraMin;
    String rangoHoraMax;
    String nicknameRival;
    List<String> diasDisponibles;

    public Notificacion(String nicknameRival, String rangoHoraMin, String rangoHoraMax, List<String> diasDisponibles) {
        this.nicknameRival = nicknameRival;
        this.rangoHoraMin = rangoHoraMin;
        this.rangoHoraMax = rangoHoraMax;
        this.diasDisponibles = diasDisponibles;
    }

    public String getRangoHoraMin() {
        return rangoHoraMin;
    }

    public void setRangoHoraMin(String rangoHoraMin) {
        this.rangoHoraMin = rangoHoraMin;
    }

    public String getRangoHoraMax() {
        return rangoHoraMax;
    }

    public void setRangoHoraMax(String rangoHoraMax) {
        this.rangoHoraMax = rangoHoraMax;
    }

    public String getNicknameRival() {
        return nicknameRival;
    }

    public void setNicknameRival(String nicknameRival) {
        this.nicknameRival = nicknameRival;
    }

    public List<String> getDiasDisponibles() {
        return diasDisponibles;
    }

    public void setDiasDisponibles(List<String> diasDisponibles) {
        this.diasDisponibles = diasDisponibles;
    }
}
