package com.renegade.ironfistspain;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.renegade.ironfistspain.databinding.FragmentVisualizacionRetoBinding;

import java.util.Arrays;
import java.util.Objects;

public class VisualizacionRetoFragment extends BaseDialogFragment {

    private FragmentVisualizacionRetoBinding binding;

    String estadoReto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentVisualizacionRetoBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        estadoReto = viewModel.estadoRetoLiveData.getValue();

        binding.diasDisponibles.setText(Arrays.toString(viewModel.diasSelecRivalLiveData.getValue().toArray()));
        binding.rangoHora1.setText(viewModel.hora1RivalLiveData.getValue());
        binding.rangoHora2.setText(viewModel.hora2RivalLiveData.getValue());

        db.collection(CollectionDB.ENCUENTROS)
                .document(viewModel.idNotiRivalLiveData.getValue())
                .get()
                .addOnSuccessListener(doc -> {
                    String uidL = doc.getString("uidLocal");
                    String uidV = doc.getString("uidVisitante");

                    db.collection(CollectionDB.USUARIOS)
                            .document(uidL)
                            .get()
                            .addOnSuccessListener(doc1 -> {
                        binding.nombreRival1Reto.setText(doc1.getString("nickname"));
                        binding.puntuacionRival1Reto.setText(""+doc1.getLong("puntuacion"));

                                db.collection(CollectionDB.USUARIOS)
                                        .document(uidV)
                                        .get()
                                        .addOnSuccessListener(doc2 -> {
                                            binding.nombreRival2Reto.setText(doc2.getString("nickname"));
                                            binding.puntuacionRival2Reto.setText(""+doc2.getLong("puntuacion"));
                                        });

                    });
        });



        if (estadoReto.equals("Enviado") || estadoReto.equals("Aceptado") || estadoReto.equals("Cancelado") || estadoReto.equals("En proceso")) {
            binding.constraintFase1.setVisibility(View.VISIBLE);
            binding.constraintFase2.setVisibility(View.GONE);
            binding.constraintFase3.setVisibility(View.GONE);
        } else if (estadoReto.equals("Planificado")) {
            binding.constraintFase1.setVisibility(View.GONE);
            binding.constraintFase2.setVisibility(View.VISIBLE);
            binding.constraintFase3.setVisibility(View.GONE);
        } else if (estadoReto.equals("Finalizado")) {
            binding.constraintFase1.setVisibility(View.GONE);
            binding.constraintFase2.setVisibility(View.VISIBLE);
            binding.constraintFase3.setVisibility(View.VISIBLE);
        }


        db.collection(CollectionDB.ENCUENTROS)
                .document(viewModel.idNotiRivalLiveData.getValue())
                .get()
                .addOnSuccessListener(doc -> {
                    if (doc.getString("fechaEncuentro") != null) {
                        binding.horaDelEncuentro.setText(doc.getString("horaEncuentro"));
                        binding.fechaDelEncuentro.setText(doc.getString("fechaEncuentro"));
                    }
                    if (doc.getLong("resultadoLocal") != null) {
                        binding.resultadoFTLocal.setText(""+doc.getLong("resultadoLocal"));
                        binding.resultadoFTVisitante.setText(""+doc.getLong("resultadoVisitante"));
                        binding.firstTo.setText(""+doc.getLong("firstTo"));
                    }
        });
    }
}