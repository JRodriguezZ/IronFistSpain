package com.renegade.ironfistspain;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.renegade.ironfistspain.databinding.FragmentRegistroJugadorBinding;

import static android.content.ContentValues.TAG;

public class RegistroJugadorFragment extends BaseFragment {

    private FragmentRegistroJugadorBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentRegistroJugadorBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.botonRegistroJugador.setOnClickListener(v -> nav.navigate(R.id.action_registroJugadorFragment_to_inicioFragment));
        binding.personajeMain.setOnClickListener(v -> nav.navigate(R.id.action_registroJugadorFragment_to_seleccionPrincipalFragment));
        binding.personajeSecundario.setOnClickListener(v -> nav.navigate(R.id.action_registroJugadorFragment_to_seleccionPjSecundarioFragment));


        viewModel.nombrePj1LiveData.observe(getViewLifecycleOwner(), nombreMain -> {
            binding.personajeMain.setText(nombreMain);
            Log.d(TAG, "Nombre del personaje 1: " + nombreMain);
        });

        viewModel.nombrePj2LiveData.observe(getViewLifecycleOwner(), nombreSecundario -> {
            binding.personajeSecundario.setText(nombreSecundario);
            Log.d(TAG, "Nombre del personaje 2: " + nombreSecundario);
        });

//        db.guardar(viewModel.nombre);
    }
}