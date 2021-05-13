package com.renegade.ironfistspain;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.renegade.ironfistspain.databinding.FragmentSeleccionRangoBinding;
import com.renegade.ironfistspain.databinding.ViewholderSeleccionRangoBinding;

import java.util.ArrayList;
import java.util.List;


public class SeleccionRangoFragment extends BaseDialogFragment {

    private FragmentSeleccionRangoBinding binding;
    
    List<Rango> rangos = new ArrayList<>();
    List<Rango> rangosOriginal = new ArrayList<>();
    
    RangosAdapter rangosAdapter = new RangosAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentSeleccionRangoBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        

        binding.listaRangos.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        binding.listaRangos.setAdapter(rangosAdapter);


        binding.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                rangosAdapter.getFilter().filter(newText);
                return false;
            }
        });

        db.collection(CollectionDB.RANGOS)
                .addSnapshotListener((value, error) -> {
                    for (QueryDocumentSnapshot rango : value) {
                        String nombre = rango.getString("nombre");
                        String imagen = rango.getString("imagen");
                        String puntuacion = rango.getString("puntuacion");

                        rangos.add(new Rango(nombre, imagen, puntuacion));
                        rangosOriginal.add(new Rango(nombre, imagen, puntuacion));

                        rangosAdapter.notifyDataSetChanged();
                    }
                });

    }

    class RangosAdapter extends RecyclerView.Adapter<SeleccionRangoFragment.RangoViewHolder> implements Filterable {

        @NonNull
        @Override
        public RangoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new SeleccionRangoFragment.RangoViewHolder(ViewholderSeleccionRangoBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RangoViewHolder holder, int position) {
            Rango rango = rangos.get(position);

            Glide.with(requireContext()).load(rango.imagenUrl).into(holder.binding.imagenRango);

            holder.itemView.setOnClickListener(v -> {
                viewModel.nombreRangoLiveData.setValue(rango.nombre);
                viewModel.imagenRangoLiveData.setValue(rango.imagenUrl);
                viewModel.puntuacionRangoLiveData.setValue(rango.puntuacion);

                Log.e("ABCD", "La puntuacion equivalente a " + viewModel.nombreRangoLiveData.getValue() + " es " + viewModel.puntuacionRangoLiveData.getValue());
                nav.popBackStack();
            });
        }

        @Override
        public int getItemCount() {
            return rangos == null ? 10 : rangos.size();
        }

        @Override
        public Filter getFilter() {
            return busqueda;
        }


        private Filter busqueda = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Rango> rangosFiltrados = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    rangosFiltrados.addAll(rangosOriginal);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Rango rango : rangosOriginal) {
                        if (rango.getNombre().toLowerCase().contains(filterPattern)) {
                            rangosFiltrados.add(rango);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = rangosFiltrados;

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                rangos.clear();
                rangos.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };
    }

    static class RangoViewHolder extends RecyclerView.ViewHolder {
        ViewholderSeleccionRangoBinding binding;
        public RangoViewHolder(@NonNull ViewholderSeleccionRangoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}