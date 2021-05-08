package com.renegade.ironfistspain;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.net.URI;

public class IFSViewModel  extends ViewModel {
    public MutableLiveData<String> nombrePj1LiveData = new MutableLiveData<>();
    public MutableLiveData<URI> imagenPj1LiveData = new MutableLiveData<>();
    public MutableLiveData<String> nombrePj2LiveData = new MutableLiveData<>();
    public MutableLiveData<URI> imagenPj2LiveData = new MutableLiveData<>();
}
