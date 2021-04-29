package com.renegade.ironfistspain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.dpro.widgets.WeekdaysPicker;
import com.renegade.ironfistspain.databinding.FragmentCrearRetoBinding;

public class CrearRetoFragment extends Fragment {

    private FragmentCrearRetoBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentCrearRetoBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);


        WeekdaysPicker weekdaysPicker = binding.weekdays;
        weekdaysPicker.setOnWeekdaysChangeListener((view1, clickedDayOfWeek, selectedDays) -> {

        });

        binding.enviarRetoButton.setOnClickListener(v ->
                navController.navigate(R.id.action_crearRetoFragment_to_inicioFragment));

        binding.enviarRetoButton.setOnClickListener(v ->
                Toast.makeText(getActivity(), "¡Se ha enviado el reto correctamente!", Toast.LENGTH_SHORT).show());

    }
}