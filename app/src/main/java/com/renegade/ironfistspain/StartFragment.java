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
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
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

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        if (account == null) {
            Log.e("ABCD", "No hay ninguna cuenta de google seleccionada previamente");
            signInClient.launch(GoogleSignIn.getClient(requireContext(), new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()).getSignInIntent());
        } else {
            Log.e("ABCD", "Si que hay una cuenta de google seleccionada: " + account.getEmail());
            firebaseLogin(account);
        }
    }

    ActivityResultLauncher<Intent> signInClient = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        try {
            GoogleSignInAccount account = GoogleSignIn.getSignedInAccountFromIntent(result.getData()).getResult(ApiException.class);
            firebaseLogin(account);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    });

    void firebaseLogin(GoogleSignInAccount account){
        auth.signInWithCredential(GoogleAuthProvider.getCredential(account.getIdToken(), null))
                    .addOnSuccessListener(authResult -> {
            Log.e("ABCD", "LOGUEADO COMO " + authResult.getUser().getUid());
            db.collection(CollectionDB.USUARIOS).document(authResult.getUser().getUid()).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Log.e("ABCD", "LOGUEADO COMO " + document.getData());
                    if (document.exists()) {
                        Log.e("ABCD", "Entra a la app");
                        nav.navigate(R.id.action_startFragment_to_inicioFragment);
                    } else {
                        Log.e("ABCD", "Entra al registro");
                        nav.navigate(R.id.action_startFragment_to_registroFragment);
                    }
                } else {
                    // error de red
                    Toast.makeText(getActivity(),"Error de red. Compruebe la conexion a internet y reinicie la aplicación.",Toast.LENGTH_LONG).show();
                }
            });
        });
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