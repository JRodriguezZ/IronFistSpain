package com.renegade.ironfistspain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.renegade.ironfistspain.databinding.FragmentSeleccionPrincipalBinding;
import com.renegade.ironfistspain.databinding.ViewholderSeleccionPersonajeBinding;

import java.util.ArrayList;
import java.util.List;


public class SeleccionPjPrincipalFragment extends BaseDialogFragment {

    private FragmentSeleccionPrincipalBinding binding;

    List<Personaje> personajes = new ArrayList<>();
    List<Personaje> personajesOriginal = new ArrayList<>();

    Personajes1Adapter personaje1Adapter = new Personajes1Adapter();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentSeleccionPrincipalBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.listaJugadores.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        binding.listaJugadores.setAdapter(personaje1Adapter);

        binding.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                personaje1Adapter.getFilter().filter(newText);
                return false;
            }
        });

        db.collection("Personajes")
                .addSnapshotListener((value, error) -> {
                    personajes.clear();
                    for (QueryDocumentSnapshot pj : value) {
                        String nombre = pj.getString("nombre");
                        String imagen = pj.getString("imagen");

                        personajes.add(new Personaje(nombre, imagen));
                        personajesOriginal.add(new Personaje(nombre, imagen));

                        personaje1Adapter.notifyDataSetChanged();
                    }
                });
    }


    class Personajes1Adapter extends RecyclerView.Adapter<PersonajeViewHolder> implements Filterable {

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
                viewModel.nombrePj1LiveData.setValue(personaje.nombre);   // aqui poner el ID del personaje
                viewModel.imagenPj1LiveData.setValue(personaje.imagenUrl);
                nav.popBackStack();
            });
        }

        @Override
        public int getItemCount() {
            return personajes == null ? 10 : personajes.size();
        }

        @Override
        public Filter getFilter() {
            return busqueda;
        }

        private Filter busqueda = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Personaje> personajesFiltrados = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    personajesFiltrados.addAll(personajesOriginal);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Personaje pj : personajesOriginal) {
                        if (pj.getNombre().toLowerCase().contains(filterPattern)) {
                            personajesFiltrados.add(pj);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = personajesFiltrados;

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                personajes.clear();
                personajes.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };
    }

    static class PersonajeViewHolder extends RecyclerView.ViewHolder {
        ViewholderSeleccionPersonajeBinding binding;
        public PersonajeViewHolder(@NonNull ViewholderSeleccionPersonajeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
