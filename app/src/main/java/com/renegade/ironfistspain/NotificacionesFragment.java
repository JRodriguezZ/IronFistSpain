package com.renegade.ironfistspain;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.renegade.ironfistspain.databinding.FragmentNotificacionesBinding;
import com.renegade.ironfistspain.databinding.ViewholderNotificacionBinding;
import com.renegade.ironfistspain.databinding.ViewholderSeleccionRangoBinding;

import java.util.ArrayList;
import java.util.List;

public class NotificacionesFragment extends BaseFragment {

    private FragmentNotificacionesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = (FragmentNotificacionesBinding.inflate(inflater, container, false))).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Opcion 1: Consultar a la base de datos todos los retos que te marquen como uidVisitante (y alomejor solo los enviados)
        // Opcion 2: Almacenar en una base de datos local los retos que te marquen como uidVisitatante y que el fragment consulte localmente.
        //           Solo actualizar los retos si son nuevos y no estaban antes ya almacenados.




    }

    class NotificacionesAdapter extends RecyclerView.Adapter<NotificacionViewHolder> {

        @NonNull
        @Override
        public NotificacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new NotificacionesFragment.NotificacionViewHolder(ViewholderNotificacionBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull NotificacionViewHolder holder, int position) {
            Notificacion notificacion = notificaciones.get(position);

            holder.binding.nombreRivalNotificacion.setText("");
            holder.binding.rangoHora1.setText(":");
            holder.binding.rangoHora2.setText(":");
            holder.binding.diasDisponibles.setText("");

            holder.itemView.setOnClickListener(v -> {
            });
        }

        @Override
        public int getItemCount() {
            return notificaciones == null ? 10 : notificaciones.size();
        }
//
//            @Override
//            public Filter getFilter() {
//                return busqueda;
//            }
//
//
//            private Filter busqueda = new Filter() {
//                @Override
//                protected FilterResults performFiltering(CharSequence constraint) {
//                    List<Rango> rangosFiltrados = new ArrayList<>();
//
//                    if (constraint == null || constraint.length() == 0) {
//                        rangosFiltrados.addAll(rangosOriginal);
//                    } else {
//                        String filterPattern = constraint.toString().toLowerCase().trim();
//
//                        for (Rango rango : rangosOriginal) {
//                            if (rango.getNombre().toLowerCase().contains(filterPattern)) {
//                                rangosFiltrados.add(rango);
//                            }
//                        }
//                    }
//
//                    FilterResults results = new FilterResults();
//                    results.values = rangosFiltrados;
//
//                    return results;
//                }
//
//                @Override
//                protected void publishResults(CharSequence constraint, FilterResults results) {
//                    rangos.clear();
//                    rangos.addAll((List) results.values);
//                    notifyDataSetChanged();
//                }
//            };
    }

    static class NotificacionViewHolder extends RecyclerView.ViewHolder {
        ViewholderNotificacionBinding binding;
        public NotificacionViewHolder(@NonNull ViewholderNotificacionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

}

