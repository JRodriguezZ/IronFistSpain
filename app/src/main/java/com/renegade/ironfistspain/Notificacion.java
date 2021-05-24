package com.renegade.ironfistspain;

import java.util.List;

public class Notificacion {
    String rangoHoraMin;
    String rangoHoraMax;
    String nicknameRival;
    List<Integer> diasDisponibles;
    String uidRival;
    String id;

    public Notificacion(String nicknameRival, String rangoHoraMin, String rangoHoraMax, List<Integer> diasDisponibles, String uidRival, String id) {
        this.nicknameRival = nicknameRival;
        this.rangoHoraMin = rangoHoraMin;
        this.rangoHoraMax = rangoHoraMax;
        this.diasDisponibles = diasDisponibles;
        this.uidRival = uidRival;
        this.id = id;
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

    public List<Integer> getDiasDisponibles() {
        return diasDisponibles;
    }

    public void setDiasDisponibles(List<Integer> diasDisponibles) {
        this.diasDisponibles = diasDisponibles;
    }

    public String getUidRival() {
        return uidRival;
    }

    public void setUidRival(String uidRival) {
        this.uidRival = uidRival;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
