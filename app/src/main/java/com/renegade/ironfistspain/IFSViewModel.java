package com.renegade.ironfistspain;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class IFSViewModel  extends ViewModel {
    public MutableLiveData<String> imagenJugadorLiveData = new MutableLiveData<>();
    public MutableLiveData<String> puntuacionJugadorLiveData = new MutableLiveData<>();


    public MutableLiveData<String> nombrePj1LiveData = new MutableLiveData<>();
    public MutableLiveData<String> imagenPj1LiveData = new MutableLiveData<>();

    public MutableLiveData<String> nombrePj2LiveData = new MutableLiveData<>();
    public MutableLiveData<String> imagenPj2LiveData = new MutableLiveData<>();

    public MutableLiveData<String> nombreRangoLiveData = new MutableLiveData<>();
    public MutableLiveData<String> imagenRangoLiveData = new MutableLiveData<>();
    public MutableLiveData<Long> puntuacionRangoLiveData = new MutableLiveData<>();

    public MutableLiveData<String> imagenRivalLiveData = new MutableLiveData<>();

    public MutableLiveData<String> idNotiRivalLiveData = new MutableLiveData<>();
    public MutableLiveData<String> nombreRivalLiveData = new MutableLiveData<>();
    public MutableLiveData<String> hora1RivalLiveData = new MutableLiveData<>();
    public MutableLiveData<String> hora2RivalLiveData = new MutableLiveData<>();
    public MutableLiveData<List<String>> diasSelecRivalLiveData = new MutableLiveData<>();
    public MutableLiveData<String> uidRivalLiveData = new MutableLiveData<>();


    // hora 1, hora 2, dias seleccionados, nombre rival, puntuacion rival.
}
