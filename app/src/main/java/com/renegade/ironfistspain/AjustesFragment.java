package com.renegade.ironfistspain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.renegade.ironfistspain.databinding.FragmentAjustesBinding;
import com.renegade.ironfistspain.databinding.FragmentCrearRetoBinding;


public class AjustesFragment extends BaseFragment {

    private FragmentAjustesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentAjustesBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.signOutButton.setOnClickListener(v -> {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .build();
            GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);

            auth.signOut();
            mGoogleSignInClient.revokeAccess();
            nav.navigate(R.id.startFragment);
        });
    }
}