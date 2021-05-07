package com.renegade.ironfistspain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.renegade.ironfistspain.databinding.FragmentInicioBinding;


public class InicioFragment extends BaseFragment {

    private FragmentInicioBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentInicioBinding.inflate(inflater, container, false)).getRoot();
    }
}