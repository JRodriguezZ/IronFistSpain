package com.renegade.ironfistspain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.renegade.ironfistspain.databinding.FragmentSeleccionPrincipalBinding;
import com.renegade.ironfistspain.databinding.ViewholderSeleccionPersonajeBinding;

import java.util.ArrayList;
import java.util.List;


public class SeleccionPjSecundarioFragment extends BaseDialogFragment {

    private FragmentSeleccionPrincipalBinding binding;
    private FirebaseFirestore db;
    List<Personaje> personajes = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentSeleccionPrincipalBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Personajes2Adapter personaje2Adapter = new Personajes2Adapter();

        binding.listaJugadores.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        binding.listaJugadores.setAdapter(personaje2Adapter);

        db.collection("Personajes")
                .addSnapshotListener((value, error) -> {
                    for (QueryDocumentSnapshot pj : value) {
                        String nombre = pj.getString("nombre");
                        String imagen = pj.getString("imagen");

                        personajes.add(new Personaje(nombre, imagen));

                        personaje2Adapter.notifyDataSetChanged();
                    }
                });
    }


    class Personajes2Adapter extends RecyclerView.Adapter<PersonajeViewHolder> {

        @NonNull
        @Override
        public PersonajeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new PersonajeViewHolder(ViewholderSeleccionPersonajeBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull PersonajeViewHolder holder, int position) {
            // rellenas el textview y el imageview
            Personaje personaje = personajes.get(position);

            holder.binding.nombrePersonaje.setText(personaje.nombre);
            Glide.with(requireContext()).load(personaje.imagenUrl).into(holder.binding.imagenPersonaje);

            holder.itemView.setOnClickListener(v -> {
                viewModel.nombrePj2LiveData.setValue(personaje.nombre);   // aqui poner el ID del personaje
                viewModel.imagenPj2LiveData.setValue(personaje.imagenUrl);
                nav.popBackStack();
            });
        }

        @Override
        public int getItemCount() {
            return personajes == null ? 10 : personajes.size();
        }
    }

    static class PersonajeViewHolder extends RecyclerView.ViewHolder {
        ViewholderSeleccionPersonajeBinding binding;
        public PersonajeViewHolder(@NonNull ViewholderSeleccionPersonajeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
