package com.renegade.ironfistspain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.renegade.ironfistspain.databinding.FragmentRegistroEspectadorBinding;


public class RegistroEspectadorFragment extends BaseFragment {

    private FragmentRegistroEspectadorBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentRegistroEspectadorBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.botonRegistroEspectador.setOnClickListener(v -> {

            db.collection(CollectionDB.USUARIOS).document(user.getUid()).set(binding.editTextTextPersonName5.getText().toString());
            nav.navigate(R.id.action_registroEspectadorFragment2_to_inicioFragment);

        });


    }
}
