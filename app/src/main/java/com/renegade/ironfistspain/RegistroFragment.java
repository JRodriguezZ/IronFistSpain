package com.renegade.ironfistspain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.renegade.ironfistspain.databinding.FragmentRegistroBinding;

public class RegistroFragment extends BaseFragment {

    private FragmentRegistroBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentRegistroBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.redirigirRegistroJugador.setOnClickListener(v ->
                        nav.navigate(R.id.action_registroFragment_to_registroJugadorFragment));


        binding.redirigirRegistroEspectador.setOnClickListener(v ->
                nav.navigate(R.id.action_registroFragment_to_registroEspectadorFragment2));

//        binding.redirigirRegistroOrganizador.setOnClickListener(v ->
//                nav.navigate(R.id.action_registroFragment_to_registroOrganizadorFragment));

    }


}