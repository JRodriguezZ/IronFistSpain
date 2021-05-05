package com.renegade.ironfistspain;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
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

        binding.progressBar.setVisibility(View.GONE);


        firebaseAuthWithGoogle(GoogleSignIn.getLastSignedInAccount(requireContext()));


        binding.redirigirRegistroJugador.setOnClickListener(v ->
                signInClient1.launch(GoogleSignIn.getClient(requireContext(), new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).build()).getSignInIntent()));


        binding.redirigirRegistroEspectador.setOnClickListener(v ->
                signInClient2.launch(GoogleSignIn.getClient(requireContext(), new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).build()).getSignInIntent()));

//        binding.redirigirRegistroOrganizador.setOnClickListener(v ->
//                nav.navigate(R.id.action_registroFragment_to_registroOrganizadorFragment));

    }

    ActivityResultLauncher<Intent> signInClient1 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        try {
            FirebaseAuth.getInstance().signInWithCredential(GoogleAuthProvider.getCredential(GoogleSignIn.getSignedInAccountFromIntent(result.getData()).getResult(ApiException.class).getIdToken(), null))
                    .addOnSuccessListener(authResult -> nav.navigate(R.id.action_registroFragment_to_registroJugadorFragment));

        } catch (ApiException e) {}
    });

    ActivityResultLauncher<Intent> signInClient2 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        try {
            FirebaseAuth.getInstance().signInWithCredential(GoogleAuthProvider.getCredential(GoogleSignIn.getSignedInAccountFromIntent(result.getData()).getResult(ApiException.class).getIdToken(), null))
                    .addOnSuccessListener(authResult -> {
                        db.collection("usuarios").document(authResult.getUser().getUid()).get().addOnCompleteListener(task -> {
                            if(task.isSuccessful()) {
                                db.collection("usuarios").document().ref.get()
                            }
                        });
                        nav.navigate(R.id.action_registroFragment_to_registroEspectadorFragment2);
                    });

        } catch (ApiException e) {}
    });


    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        if(account == null) return;

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.formularioRegistro.setVisibility(View.GONE);

        auth.signInWithCredential(GoogleAuthProvider.getCredential(account.getIdToken(), null))
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        nav.navigate(R.id.action_registroFragment_to_inicioFragment);
                    } else {
                        binding.progressBar.setVisibility(View.GONE);
                        binding.formularioRegistro.setVisibility(View.VISIBLE);
                    }
                });
    }
}