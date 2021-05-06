package com.renegade.ironfistspain;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.renegade.ironfistspain.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityMainBinding.inflate(getLayoutInflater())).getRoot());

        setSupportActionBar(binding.toolbar);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                // Top-level destinations:
                R.id.crearRetoFragment, R.id.inicioFragment, R.id.mensajesFragment, R.id.notificacionesFragment, R.id.perfilJugadorFragment, R.id.rankingFragment, R.id.registroFragment, R.id.startFragment
                )
                .setOpenableLayout(binding.drawerLayout)
                .build();



        NavController navController = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)).getNavController();
        NavigationUI.setupWithNavController(binding.navView, navController);
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.bottomNavView, navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.registroFragment ||
                    destination.getId() == R.id.registroJugadorFragment ||
                    destination.getId() == R.id.registroEspectadorFragment2 ||
                    destination.getId() == R.id.registroOrganizadorFragment ||
                    destination.getId() == R.id.crearRetoFragment ||
                    destination.getId() == R.id.seleccionPrincipalFragment ||
                    destination.getId() == R.id.seleccionPjSecundarioFragment ||
                    destination.getId() == R.id.startFragment) {
                binding.bottomNavView.setVisibility(View.GONE);
                binding.toolbar.setVisibility(View.GONE);
            } else {
                binding.bottomNavView.setVisibility(View.VISIBLE);
                binding.toolbar.setVisibility(View.VISIBLE);
            }
        });
    }
}