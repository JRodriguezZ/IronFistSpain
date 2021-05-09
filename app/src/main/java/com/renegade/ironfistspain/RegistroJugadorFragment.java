package com.renegade.ironfistspain;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
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

        binding.imageButtonPjPrincipal.setOnClickListener(v -> nav.navigate(R.id.action_registroJugadorFragment_to_seleccionPrincipalFragment));
        binding.imageButtonPjSecundario.setOnClickListener(v -> nav.navigate(R.id.action_registroJugadorFragment_to_seleccionPjSecundarioFragment));


        viewModel.imagenPj1LiveData.observe(getViewLifecycleOwner(), imagenMain -> {
            Glide.with(requireContext()).load(viewModel.imagenPj1LiveData).circleCrop().into(binding.imageButtonPjPrincipal);
//            Log.d(TAG, "Nombre del personaje 1: " + imagenMain);
        });

        viewModel.imagenPj2LiveData.observe(getViewLifecycleOwner(), imagenSecundario -> {
            Glide.with(requireContext()).load(viewModel.imagenPj2LiveData).circleCrop().into(binding.imageButtonPjSecundario);
//            Log.d(TAG, "Nombre del personaje 2: " + imagenSecundario);
        });

        
        binding.botonRegistroJugador.setOnClickListener(v -> {
            //TODO: generar una puntuacion a partir del rango indicado en el registro.

            int puntuacion = 0;

            


            // add -> genera un id de documento aleatorio
            // document.set  // le pones el id que quieras

            db.collection("usuarios")
                    .document(user.getUid())
                    .set(new Jugador(user.getUid(), binding.editTextNombreUsuario.getText().toString(), viewModel.nombrePj1LiveData.getValue(), viewModel.nombrePj2LiveData.getValue(), puntuacion, "jugador"));

            nav.navigate(R.id.action_registroJugadorFragment_to_inicioFragment);
        });

    }

//    private void calcularPuntuacion() {
//        if (binding.
//        )
//    }
}