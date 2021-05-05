package com.renegade.ironfistspain;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

        
        binding.botonRegistroJugador.setOnClickListener(v -> {
            //TODO: generar una puntuacion a partir del rango indicado en el registro.

            int puntuacion = 0;



            // add -> genera un id de documento aleatorio
            // document.set  // le pones el id que quieras

            db.collection("usuarios").document(user.getUid()).set(new Jugador(user.getUid(), binding.jugadorNickname.toString(), binding.personajeMain.getText().toString(), binding.personajeSecundario.getText().toString(),puntuacion));
            nav.navigate(R.id.action_registroJugadorFragment_to_inicioFragment);
        });

    }
}