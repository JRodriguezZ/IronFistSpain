package com.renegade.ironfistspain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
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

        binding.imageButtonPjPrincipal.setOnClickListener(v -> nav.navigate(R.id.action_registroJugadorFragment_to_seleccionPrincipalFragment));
        binding.imageButtonPjSecundario.setOnClickListener(v -> nav.navigate(R.id.action_registroJugadorFragment_to_seleccionPjSecundarioFragment));
        binding.imagenRango.setOnClickListener(v -> nav.navigate(R.id.action_registroJugadorFragment_to_seleccionRangoFragment));

        binding.botonLimpiarCasilla.setOnClickListener(v -> {
            viewModel.imagenPj2LiveData.setValue(null);
            viewModel.nombrePj2LiveData.setValue(null);
        });

        viewModel.imagenPj1LiveData.observe(getViewLifecycleOwner(), imagenMain -> {
            Glide.with(requireContext()).load(viewModel.imagenPj1LiveData.getValue()).circleCrop().into(binding.imageButtonPjPrincipal);
//            Log.d(TAG, "Nombre del personaje 1: " + imagenMain);
        });

        viewModel.imagenPj2LiveData.observe(getViewLifecycleOwner(), imagenSecundario -> {
            Glide.with(requireContext()).load(viewModel.imagenPj2LiveData.getValue()).circleCrop().into(binding.imageButtonPjSecundario);
//            Log.d(TAG, "Nombre del personaje 2: " + imagenSecundario);
        });

        viewModel.imagenRangoLiveData.observe(getViewLifecycleOwner(), imagenRango -> {
            Glide.with(requireContext()).load(viewModel.imagenRangoLiveData.getValue()).into(binding.imagenRango);
        });

        binding.botonRegistroJugador.setOnClickListener(v -> {

            if (binding.editTextNombreUsuario.getText().toString().equals("") ||
                    viewModel.nombrePj1LiveData.getValue() == null ||
                    viewModel.puntuacionRangoLiveData.getValue() == null) {
                Toast.makeText(getActivity(), "¡Hay campos obligatorios sin rellenar!", Toast.LENGTH_LONG).show();
            } else {
                    db.collection(CollectionDB.USUARIOS)
                            .document(user.getUid())
                            .set(new Jugador(user.getUid(), binding.editTextNombreUsuario.getText().toString(), viewModel.imagenPj1LiveData.getValue(), viewModel.nombrePj1LiveData.getValue(), viewModel.nombrePj2LiveData.getValue(), viewModel.puntuacionRangoLiveData.getValue(), "jugador"));

                    nav.navigate(R.id.action_registroJugadorFragment_to_inicioFragment);
                }

            });
            // add -> genera un id de documento aleatorio
            // document.set  // le pones el id que quieras


    }

}