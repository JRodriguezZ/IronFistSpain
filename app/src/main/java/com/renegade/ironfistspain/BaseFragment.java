package com.renegade.ironfistspain;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;

public class BaseFragment extends Fragment {

    IFSViewModel viewModel;
    NavController nav;
    FirebaseAuth auth;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nav = Navigation.findNavController(view);
        viewModel = new ViewModelProvider(requireActivity()).get(IFSViewModel.class);
        auth = FirebaseAuth.getInstance();
    }
}
