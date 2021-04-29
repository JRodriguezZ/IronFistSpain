package com.renegade.ironfistspain;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class BaseDialogFragment extends DialogFragment {

    NavController nav;
    IFSViewModel viewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nav = Navigation.findNavController(requireParentFragment().getView());
        viewModel = new ViewModelProvider(requireActivity()).get(IFSViewModel.class);

    }
}
