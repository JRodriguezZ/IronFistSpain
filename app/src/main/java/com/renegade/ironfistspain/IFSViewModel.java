package com.renegade.ironfistspain;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class IFSViewModel  extends ViewModel {
    public MutableLiveData<String> imagenJugadorLiveData = new MutableLiveData<>();
    public MutableLiveData<String> puntuacionJugadorLiveData = new MutableLiveData<>();


    public MutableLiveData<String> nombrePj1LiveData = new MutableLiveData<>();
    public MutableLiveData<String> imagenPj1LiveData = new MutableLiveData<>();

    public MutableLiveData<String> nombrePj2LiveData = new MutableLiveData<>();
    public MutableLiveData<String> imagenPj2LiveData = new MutableLiveData<>();

    public MutableLiveData<String> nombreRangoLiveData = new MutableLiveData<>();
    public MutableLiveData<String> imagenRangoLiveData = new MutableLiveData<>();
    public MutableLiveData<String> puntuacionRangoLiveData = new MutableLiveData<>();

    public MutableLiveData<String> nombreRivalLiveData = new MutableLiveData<>();
    public MutableLiveData<String> imagenRivalLiveData = new MutableLiveData<>();
    public MutableLiveData<String> puntuacionRivalLiveData = new MutableLiveData<>();
}
