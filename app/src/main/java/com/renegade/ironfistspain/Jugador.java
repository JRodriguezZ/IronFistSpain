package com.renegade.ironfistspain;

public class Jugador {
    String UID;
    String nickname;
    String personajeMain;
    String personajeSecundario;
    int puntuacion;

    public Jugador(String UID, String nickname, String personajeMain, String personajeSecundario, int puntuacion) {
        this.UID = UID;
        this.nickname = nickname;
        this.personajeMain = personajeMain;
        this.personajeSecundario = personajeSecundario;
        this.puntuacion = puntuacion;
    }

    public String getUID() {
        return UID;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPersonajeMain() {
        return personajeMain;
    }

    public void setPersonajeMain(String personajeMain) {
        this.personajeMain = personajeMain;
    }

    public String getPersonajeSecundario() {
        return personajeSecundario;
    }

    public void setPersonajeSecundario(String personajeSecundario) {
        this.personajeSecundario = personajeSecundario;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }
}
