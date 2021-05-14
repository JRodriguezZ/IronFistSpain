package com.renegade.ironfistspain;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dpro.widgets.WeekdaysPicker;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.renegade.ironfistspain.databinding.FragmentCrearRetoBinding;
import com.renegade.ironfistspain.databinding.ViewholderSeleccionPersonajeBinding;
import com.renegade.ironfistspain.databinding.ViewholderSeleccionRangoBinding;
import com.renegade.ironfistspain.databinding.ViewholderSeleccionRivalBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class CrearRetoFragment extends BaseFragment {
    int hora1, minutos1;
    int hora2, minutos2;
    private FragmentCrearRetoBinding binding;

    List<Integer> diasSeleccionados = new ArrayList<>();
    List<Rival> rivalesDisponibles = new ArrayList<>();

    RivalesAdapter rivalesAdapter = new RivalesAdapter();

    class Rival { String nombre; int puntuacion; String imagen; public Rival(String nombre, int puntuacion, String imagen) { this.nombre = nombre; this.puntuacion = puntuacion; this.imagen = imagen;}}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentCrearRetoBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerViewRivales.setLayoutManager(horizontalLayoutManager);
        binding.recyclerViewRivales.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL));
        binding.recyclerViewRivales.setAdapter(rivalesAdapter);

        db.collection(CollectionDB.USUARIOS).document(user.getUid()).get().addOnSuccessListener(doc -> {
//            int p;
//            String s = null;
//            try {

            int p = Integer.parseInt(doc.getString("puntuacion")); // 1000
            String s = doc.getString("nickname");
                Log.e("ABCD", doc.getString("puntuacion"));

//            } catch (Exception e){
//                System.out.println("PUNTUACION INVALIDA EN LA BASE DE DATOS");
//                p = 1000;
//            }

            db.collection(CollectionDB.USUARIOS)
                    .whereGreaterThanOrEqualTo("puntuacion", ""+(p-100))
                    .whereLessThanOrEqualTo("puntuacion", ""+(p+100))
//                    .orderBy("puntuacion", Query.Direction.valueOf("asc"))
                    .addSnapshotListener((value, error) -> {
                        rivalesDisponibles.clear();
                        for (QueryDocumentSnapshot rival : value) {
                            if (!Objects.equals(rival.getString("nickname"), s)) {
                                String nombre = rival.getString("nickname");
                                int puntuacion = Integer.parseInt(rival.getString("puntuacion"));
                                String imagen = rival.getString("imagen");

                                Log.e("ABCD", "Consulta: " + s + " = " + nombre + ", " + puntuacion);

                                rivalesDisponibles.add(new Rival(nombre, puntuacion, imagen));
                            }

                            rivalesAdapter.notifyDataSetChanged();
                        }
            });
        });

        WeekdaysPicker weekdaysPicker = binding.weekdays;
        weekdaysPicker.setOnWeekdaysChangeListener((view1, clickedDayOfWeek, selectedDays) -> {
                diasSeleccionados = weekdaysPicker.getSelectedDays();
        });

        binding.botonHora1.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    getContext(),
                    android.R.style.Theme_Black,
                    (view1, hourOfDay, minute) -> {
                        hora1 = hourOfDay;
                        minutos1 = minute;
                        String tiempo = hora1 + ":" + minutos1;
                        SimpleDateFormat f24horas = new SimpleDateFormat("HH:mm");
                        try {
                            Date date = f24horas.parse(tiempo);
                            binding.botonHora1.setText(f24horas.format(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Log.e("ABCD", "Hora 1: " + binding.botonHora1.getText());
                    },12,0,true
            );
            timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            timePickerDialog.updateTime(hora1, minutos1);
            timePickerDialog.show();
        });

        binding.botonHora2.setOnClickListener(view1 -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    getContext(),
                    android.R.style.Theme_Black,
                    (view2, hourOfDay, minute) -> {
                        hora2 = hourOfDay;
                        minutos2 = minute;
                        String tiempo = hora2 + ":" + minutos2;
                        SimpleDateFormat f24horas = new SimpleDateFormat("HH:mm");
                        try {
                            Date date = f24horas.parse(tiempo);
                            binding.botonHora2.setText(f24horas.format(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Log.e("ABCD", "Hora 2: " + binding.botonHora2.getText());
                    },12,0,true
            );
            timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            timePickerDialog.updateTime(hora1, minutos1);
            timePickerDialog.show();
        });

        binding.enviarRetoButton.setOnClickListener(v -> {
//                            navController.navigate(R.id.action_crearRetoFragment_to_inicioFragment));
//            db.collection("Encuentros").document().set(new Encuentro("Enviado", user.getUid(), usuarioSeleccionado.getName()));
            Log.e("ABCD", "Dias seleccionados: " + diasSeleccionados);
//            db.collection("Encuentros").add(new Encuentro("Enviado", user.getDisplayName(),jugadorSeleccionado.toString()));
            Toast.makeText(getActivity(), "¡Se ha enviado el reto correctamente!", Toast.LENGTH_SHORT).show();
        });

    }

    class RivalesAdapter extends RecyclerView.Adapter<RivalViewHolder> {

        @NonNull
        @Override
        public RivalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RivalViewHolder(ViewholderSeleccionRivalBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull CrearRetoFragment.RivalViewHolder holder, int position) {
            Rival rival = rivalesDisponibles.get(position);

            holder.binding.nombreRival.setText(rival.nombre);
            Glide.with(requireContext()).load(rival.imagen).circleCrop().into(holder.binding.imagenRival);
            holder.binding.puntuacionRival.setText(""+rival.puntuacion);

            holder.itemView.setOnClickListener(v -> {
                viewModel.nombreRivalLiveData.setValue(rival.nombre);   // aqui poner el ID del personaje
                viewModel.imagenRivalLiveData.setValue(rival.imagen);
                viewModel.puntuacionRangoLiveData.setValue(String.valueOf(rival.puntuacion));
            });
        }

        @Override
        public int getItemCount() {
            return rivalesDisponibles == null ? 10 : rivalesDisponibles.size();
        }
    }

    static class RivalViewHolder extends RecyclerView.ViewHolder {
        ViewholderSeleccionRivalBinding binding;
        public RivalViewHolder(@NonNull ViewholderSeleccionRivalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}