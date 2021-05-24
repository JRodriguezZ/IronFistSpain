package com.renegade.ironfistspain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.renegade.ironfistspain.databinding.FragmentVisualizacionNotificacionBinding;

import java.util.Arrays;


public class VisualizacionNotificacionFragment extends BaseDialogFragment {

    private FragmentVisualizacionNotificacionBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentVisualizacionNotificacionBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.nombreRivalNoti.setText(viewModel.nombreRivalLiveData.getValue());
        binding.rangoHora1.setText(viewModel.hora1RivalLiveData.getValue());
        binding.rangoHora2.setText(viewModel.hora2RivalLiveData.getValue());
        binding.diasDisponibles.setText(Arrays.toString(viewModel.diasSelecRivalLiveData.getValue().toArray()));

        db.collection(CollectionDB.USUARIOS)
                .document(viewModel.uidRivalLiveData.getValue())
                .get()
                .addOnSuccessListener(doc -> {
                    String pj1Rival = doc.getString("personajeMain");
                    String pj2Rival = doc.getString("personajeSecundario");

                    Glide.with(requireContext()).load(doc.getString("imagen")).circleCrop().into(binding.imagenRivalNoti);
                    binding.puntuacionRivalNoti.setText(""+doc.getLong("puntuacion"));

                    db.collection(CollectionDB.PERSONAJES)
                            .document(pj1Rival)
                            .get()
                            .addOnSuccessListener(doc1 ->
                                    Glide.with(requireContext()).load(doc1.getString("imagen")).circleCrop().into(binding.imagenPj1RivalNoti));

                    if (pj2Rival != null) {
                        db.collection(CollectionDB.PERSONAJES)
                                .document(pj2Rival)
                                .get()
                                .addOnSuccessListener(doc2 ->
                                        Glide.with(requireContext()).load(doc2.getString("imagen")).circleCrop().into(binding.imagenPj2RivalNoti));

                    } else {
                        binding.imagenPj2RivalNoti.setVisibility(View.GONE);
                    }
                });

        binding.botonAceptarReto.setOnClickListener(v -> {
            db.collection(CollectionDB.ENCUENTROS)
                    .document(viewModel.idNotiRivalLiveData.getValue())
                    .update("estado", "Aceptado")
                    .addOnSuccessListener(doc -> {
                        nav.navigate(R.id.retosPendientesFragment2);
                    });
        });

        binding.botonCancelarReto.setOnClickListener(v -> {
            db.collection(CollectionDB.ENCUENTROS)
                    .document(viewModel.idNotiRivalLiveData.getValue())
                    .update("estado", "Cancelado")
                    .addOnSuccessListener(doc -> {
                        nav.popBackStack();
                    });
        });
    }
}