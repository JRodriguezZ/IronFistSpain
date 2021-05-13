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

import com.dpro.widgets.WeekdaysPicker;
import com.renegade.ironfistspain.databinding.FragmentCrearRetoBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CrearRetoFragment extends BaseFragment {
    int hora1, minutos1;
    int hora2, minutos2;
    private FragmentCrearRetoBinding binding;

    List<Integer> diasSeleccionados;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentCrearRetoBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

            //                navController.navigate(R.id.action_crearRetoFragment_to_inicioFragment));
            //db.collection("Encuentros").document().set(new Encuentro("Enviado", user.getUid(), usuarioSeleccionado.getName()));
            Log.e("ABCD", "Dias seleccionados: " + diasSeleccionados);
//            db.collection("Encuentros").add(new Encuentro("Enviado", user.getDisplayName(),jugadorSeleccionado.toString()));
            Toast.makeText(getActivity(), "¡Se ha enviado el reto correctamente!", Toast.LENGTH_SHORT).show();
        });

    }
}