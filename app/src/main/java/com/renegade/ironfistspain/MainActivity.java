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

import com.renegade.ironfistspain.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityMainBinding.inflate(getLayoutInflater())).getRoot());

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
            if (destination.getId() == R.id.registroFragment ||
                    destination.getId() == R.id.registroJugadorFragment ||
                    destination.getId() == R.id.registroEspectadorFragment2 ||
                    destination.getId() == R.id.registroOrganizadorFragment ||
                    destination.getId() == R.id.seleccionPrincipalFragment ||
                    destination.getId() == R.id.seleccionPjSecundarioFragment ||
                    destination.getId() == R.id.seleccionRangoFragment ||
                    destination.getId() == R.id.startFragment) {
                binding.bottomNavView.setVisibility(View.GONE);
                binding.toolbar.setVisibility(View.GONE);
            } else {
                binding.bottomNavView.setVisibility(View.VISIBLE);
                binding.toolbar.setVisibility(View.VISIBLE);
            }

            if (destination.getId() == R.id.perfilJugadorFragment) {
                binding.navProfileFragment.setVisibility(View.GONE);
                binding.bottomNavView.setVisibility(View.GONE);
            } else {
                binding.navProfileFragment.setVisibility(View.VISIBLE);
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