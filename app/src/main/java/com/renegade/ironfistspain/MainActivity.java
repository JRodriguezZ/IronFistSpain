package com.renegade.ironfistspain;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.renegade.ironfistspain.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityMainBinding.inflate(getLayoutInflater())).getRoot());

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        FirebaseFirestore.getInstance().setFirestoreSettings(settings);

        setSupportActionBar(binding.toolbar);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                // Top-level destinations:
                R.id.crearRetoFragment, R.id.mensajesFragment, R.id.notificacionesFragment, R.id.rankingFragment, R.id.registroFragment, R.id.inicioFragment)
                .setOpenableLayout(binding.drawerLayout)
                .build();

        navController = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)).getNavController();
        NavigationUI.setupWithNavController(binding.navView, navController);
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.bottomNavView, navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {

            // toolbar
            if (destination.getId() == R.id.registroFragment ||
                    destination.getId() == R.id.registroJugadorFragment ||
                    destination.getId() == R.id.registroEspectadorFragment2 ||
                    destination.getId() == R.id.seleccionPrincipalFragment ||
                    destination.getId() == R.id.seleccionPjSecundarioFragment ||
                    destination.getId() == R.id.seleccionRangoFragment ||
                    destination.getId() == R.id.startFragment) {
                binding.toolbar.setVisibility(View.GONE);
            } else {
                binding.toolbar.setVisibility(View.VISIBLE);
            }

            // bottomNav
            if (destination.getId() == R.id.perfilJugadorFragment ||
                    destination.getId() == R.id.registroFragment ||
                    destination.getId() == R.id.registroJugadorFragment ||
                    destination.getId() == R.id.registroEspectadorFragment2 ||
                    destination.getId() == R.id.seleccionPrincipalFragment ||
                    destination.getId() == R.id.seleccionPjSecundarioFragment ||
                    destination.getId() == R.id.seleccionRangoFragment ||
                    destination.getId() == R.id.startFragment) {
                binding.bottomNavView.setVisibility(View.GONE);

            } else {
                binding.bottomNavView.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        navController = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)).getNavController();
        getMenuInflater().inflate(R.menu.perfil_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        navController = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)).getNavController();
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }
}