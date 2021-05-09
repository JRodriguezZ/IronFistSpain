package com.renegade.ironfistspain;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.renegade.ironfistspain.databinding.FragmentStartBinding;


public class StartFragment extends BaseFragment {

    private FragmentStartBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentStartBinding.inflate(inflater, container, false)).getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*
        Intenta loguear hacia el home si ya habia una sesion iniciada.

        haz que el usuario se loguee con una cuenta suya {
            si esa cuenta tiene un documento con el mismo uid del usuario loguealo.
            sino dirigir hacia el registro.

        */

        Log.e("ABCD", "Se crea el fragment");
        firebaseAuthWithGoogle(GoogleSignIn.getLastSignedInAccount(requireContext()));
    }

    ActivityResultLauncher<Intent> signInClient = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        try {
            FirebaseAuth.getInstance().signInWithCredential(GoogleAuthProvider.getCredential(GoogleSignIn.getSignedInAccountFromIntent(result.getData()).getResult(ApiException.class).getIdToken(), null))
                    .addOnSuccessListener(authResult -> {
                        db.collection("usuarios").document(authResult.getUser().getUid()).get().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                if (task.getResult().exists()) {
                                    nav.navigate(R.id.action_startFragment_to_inicioFragment);
                                } else {
                                    nav.navigate(R.id.action_startFragment_to_registroFragment);
                                }
                            } else {
                                // error de red
                                Toast.makeText(getActivity(),"Error de red. Comprueve la conexion a internet y reinicie la aplicación.",Toast.LENGTH_LONG).show();
                            }
                        });
                    });

        } catch (ApiException e) {
        }
    });

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        if (account == null) {
            signInClient.launch(GoogleSignIn.getClient(requireContext(), new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).build()).getSignInIntent());
            Log.e("ABCD", "No hay nadie logueado anteriormente");
        } else {
            Log.e("ABCD", "Entra directamente a la app");
            auth.signInWithCredential(GoogleAuthProvider.getCredential(account.getIdToken(), null))
                    .addOnCompleteListener(requireActivity(), task -> {
                        if (task.isSuccessful()) {
                            nav.navigate(R.id.action_startFragment_to_inicioFragment);
                        } else {
                            Toast.makeText(getActivity(),"Error de red. Comprueve la conexion a internet y reinicie la aplicación.", Toast.LENGTH_LONG).show();
                        }

                    });
        }
    }

}


/*

<FrameLayout>
    <ConstraintLayout id="formularioInicial">
        <GButton>
        <GButton>
    </ConstrintLayout>

    <ProgressBar id="pb">
</Layout>

 */