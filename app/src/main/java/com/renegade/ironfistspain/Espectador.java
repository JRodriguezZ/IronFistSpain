package com.renegade.ironfistspain;

public class Espectador {

    String nickname;
    String rol;

    public Espectador(String nickname, String rol) {
        this.nickname = nickname;
        this.rol = rol;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
