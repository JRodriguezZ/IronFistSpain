package com.renegade.ironfistspain;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.renegade.ironfistspain.databinding.FragmentRegistroBinding;
import com.renegade.ironfistspain.databinding.FragmentRegistroOrganizadorBinding;

public class RegistroOrganizadorFragment extends Fragment {

    private FragmentRegistroOrganizadorBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentRegistroOrganizadorBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);

        binding.botonRegistroOrganizador.setOnClickListener(v ->
                navController.navigate(R.id.action_registroOrganizadorFragment_to_inicioFragment));


    }
}