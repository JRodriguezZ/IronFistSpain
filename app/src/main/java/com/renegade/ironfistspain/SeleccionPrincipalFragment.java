package com.renegade.ironfistspain;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.renegade.ironfistspain.databinding.FragmentSeleccionPrincipalBinding;
import com.renegade.ironfistspain.databinding.ViewholderSeleccionPersonajeBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SeleccionPrincipalFragment extends BaseDialogFragment {

    private FragmentSeleccionPrincipalBinding binding;
    List<Personaje> personajes = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentSeleccionPrincipalBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PersonajesAdapter personajesAdapter = new PersonajesAdapter();

        binding.listaJugadores.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        binding.listaJugadores.setAdapter(personajesAdapter);


        // Lo que hay aqui se debe cambiar por una consulta a la base de datos. Es decir, el arrayList se debe rellenar con los datos de la consulta

        personajes = Arrays.asList(
            new Personaje("King", "https://e00-marca.uecdn.es/assets/multimedia/imagenes/2020/09/12/15999204285953.jpg"),
                new Personaje("Kunimitsu", "https://f.rpp-noticias.io/2020/12/15/tekken_1035156.jpg"),
                new Personaje("Lidia", "https://puregaming.es/wp-content/uploads/2021/03/Tekken-7-926x1024.jpg")
                );

        personajesAdapter.notifyDataSetChanged();
    }


    class PersonajesAdapter extends RecyclerView.Adapter<PersonajeViewHolder> {

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
                viewModel.nombreLiveData.setValue(personaje.nombre);   // aqui poner el ID del personaje
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
