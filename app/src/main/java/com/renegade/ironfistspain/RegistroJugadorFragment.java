package com.renegade.ironfistspain;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.renegade.ironfistspain.databinding.FragmentRegistroJugadorBinding;

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
        binding.personajeSecundario.setOnClickListener(v -> nav.navigate(R.id.action_registroJugadorFragment_to_seleccionPrincipalFragment));


        viewModel.nombreLiveData.observe(getViewLifecycleOwner(), nombre -> {
            binding.personajeMain.setText(nombre);
        });

        viewModel.nombreLiveData.observe(getViewLifecycleOwner(), nombre2 -> {
            binding.personajeSecundario.setText(nombre2);
        });

//        db.guardar(viewModel.nombre);
    }
}