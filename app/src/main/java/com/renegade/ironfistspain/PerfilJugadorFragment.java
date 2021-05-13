package com.renegade.ironfistspain;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Source;
import com.renegade.ironfistspain.databinding.FragmentPerfilJugadorBinding;


public class PerfilJugadorFragment extends BaseFragment {

    private FragmentPerfilJugadorBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentPerfilJugadorBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db.collection(CollectionDB.USUARIOS).document("As5KNN1BoXVCpmJDfXbnOYOML4y1").get(Source.SERVER).addOnCompleteListener(task -> {
            Log.e("ABCD", task.getResult().getString("personajeMain") + " " + task.getResult().get("personajeMain"));
            if (task.isSuccessful()) {
                db.collection(CollectionDB.PERSONAJES).document(String.valueOf(task.getResult().get("personajeMain"))).get(Source.SERVER).addOnCompleteListener(task1 -> {
                    Log.e("ABCD", task1.getResult().getString("imagen") + " " + task1.getResult().get("imagen"));
                    if (task1.isSuccessful()) {
                        Glide.with(requireContext()).load(task1.getResult().get("imagen")).circleCrop().into(binding.imagenPjMain);
                    }
                });
            }
        });

        db.collection(CollectionDB.USUARIOS).document("As5KNN1BoXVCpmJDfXbnOYOML4y1").get(Source.SERVER).addOnCompleteListener(task -> {
            Log.e("ABCD", task.getResult().getString("personajeSecundario") + " " + task.getResult().get("personajeMain"));
            if (task.isSuccessful()) {
                db.collection(CollectionDB.PERSONAJES).document(String.valueOf(task.getResult().get("personajeSecundario"))).get(Source.SERVER).addOnCompleteListener(task1 -> {
                    Log.e("ABCD", task1.getResult().getString("imagen") + " " + task1.getResult().get("imagen"));
                    if (task1.isSuccessful()) {
                        Glide.with(requireContext()).load(task1.getResult().get("imagen")).circleCrop().into(binding.imagenPjSec);
                    }
                });
            }
        });

        db.collection(CollectionDB.USUARIOS).document("As5KNN1BoXVCpmJDfXbnOYOML4y1").get(Source.SERVER).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                binding.puntuacionJugadorPerfil.setText(task.getResult().get("puntuacion").toString());
            }
        });

        db.collection(CollectionDB.USUARIOS).document("As5KNN1BoXVCpmJDfXbnOYOML4y1").get(Source.SERVER).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                binding.nombreJugadorPerfil.setText(task.getResult().get("nickname").toString());
            }
        });

    }
}