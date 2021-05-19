package com.renegade.ironfistspain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
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

        db.collection(CollectionDB.USUARIOS)
                .document(user.getUid())
                .get().addOnSuccessListener(doc -> {
                    String s = doc.getString("personajeMain");
                    db.collection(CollectionDB.PERSONAJES)
                            .document(s)
                            .get(Source.SERVER)
                            .addOnSuccessListener(doc2 -> {
                                Glide.with(requireContext()).load(doc2.get("imagen")).circleCrop().into(binding.imagenPjMain);
                            });
                });

        db.collection(CollectionDB.USUARIOS)
                .document(user.getUid())
                .get().addOnSuccessListener(doc -> {
                    String s = doc.getString("personajeSecundario");
                    System.out.println("ABCD, " + s);
                    if (s == null) {
                        binding.textPjSec.setVisibility(View.GONE);
                        binding.imagenPjSec.setVisibility(View.GONE);
                    } else {
                        db.collection(CollectionDB.PERSONAJES)
                                .document(s)
                                .get()
                                .addOnSuccessListener(doc2 -> {
                                    Glide.with(requireContext()).load(doc2.get("imagen")).circleCrop().into(binding.imagenPjSec);

                                });
                    }
                });

        db.collection(CollectionDB.USUARIOS)
                .document(user.getUid())
                .get()
                .addOnSuccessListener(doc -> {
                    binding.puntuacionJugadorPerfil.setText(""+doc.getLong("puntuacion"));
                });

        db.collection(CollectionDB.USUARIOS)
                .document(user.getUid())
                .get()
                .addOnSuccessListener(doc -> {
                    binding.nombreJugadorPerfil.setText(doc.getString("nickname"));
                });

        db.collection(CollectionDB.USUARIOS)
                .document(user.getUid())
                .get()
                .addOnSuccessListener(doc -> {
                    Glide.with(requireContext()).load(doc.get("imagen")).circleCrop().into(binding.imagenJugadorPerfil);
                });
    }
}