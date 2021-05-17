package com.renegade.ironfistspain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.renegade.ironfistspain.databinding.FragmentRankingBinding;


public class RankingFragment extends BaseFragment {

    private FragmentRankingBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentRankingBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Hola Dani, aqui hay dos opciones a valorar:
        // Opcion 1: Hacer una vista normal de la clasificacion actual donde clickas a los jugadores y puedes ver sus perfiles e historial.
        // Opcion 2: Hacer una vista con un par de Tabs rollo Whatsapp donde una muestre la clasificacion actual y otra los ultimos partidos jugados y los resultados.

        // En la primera opcion la parte de ver los ultimos partidos estaria en otro lado como en el drawer por ejemplo.

    }
}