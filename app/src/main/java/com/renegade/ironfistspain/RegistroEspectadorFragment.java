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

import com.renegade.ironfistspain.databinding.FragmentRegistroEspectadorBinding;
import com.renegade.ironfistspain.databinding.FragmentRegistroOrganizadorBinding;


public class RegistroEspectadorFragment extends Fragment {

    private FragmentRegistroEspectadorBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentRegistroEspectadorBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);

        binding.botonRegistroEspectador.setOnClickListener(v ->
                navController.navigate(R.id.action_registroEspectadorFragment2_to_inicioFragment));


    }
}
