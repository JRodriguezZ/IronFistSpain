package com.renegade.ironfistspain;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.renegade.ironfistspain.databinding.FragmentNotificacionesBinding;
import com.renegade.ironfistspain.databinding.ViewholderNotificacionBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NotificacionesFragment extends BaseFragment {

    private FragmentNotificacionesBinding binding;

    List<Notificacion> notificaciones = new ArrayList<>();

    NotificacionesAdapter notificacionesAdapter = new NotificacionesAdapter();

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

        LinearLayoutManager verticalLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.listaNotificaciones.setLayoutManager(verticalLayoutManager);
//        binding.listaNotificaciones.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        binding.listaNotificaciones.setAdapter(notificacionesAdapter);

        db.collection(CollectionDB.ENCUENTROS)
                .whereEqualTo("estado", "Enviado")
                .addSnapshotListener((value, error) -> {
                    notificaciones.clear();
                    for (QueryDocumentSnapshot noti : value) {
                        if (noti != null) {
                            if (noti.getString("uidVisitante").equals(user.getUid())){
                                String id = noti.getId();
                                String uidLocal = noti.getString("uidLocal");
                                String rangoHoraMin = noti.getString("rangoHoraMin");
                                String rangoHoraMax = noti.getString("rangoHoraMax");
                                List<String> diasDisponibles = (List<String>) noti.get("diasSeleccionados");

                                db.collection(CollectionDB.USUARIOS)
                                        .document(uidLocal)
                                        .get()
                                        .addOnSuccessListener(doc -> {
                                            String nicknameRival = doc.getString("nickname");

                                            Log.e("ABCD", "Nickname Rival: " + nicknameRival + " - Hora minima: " + rangoHoraMin + " - Hora maxima: " + rangoHoraMax + " - Dias Seleccionados: " + diasDisponibles);

                                            notificaciones.add(new Notificacion(nicknameRival, rangoHoraMin, rangoHoraMax, diasDisponibles, uidLocal, id));
                                            notificacionesAdapter.notifyDataSetChanged();

                                            if (notificaciones.size() == 0) binding.noTienesNotificaciones.setVisibility(View.VISIBLE);
                                            else binding.noTienesNotificaciones.setVisibility(View.GONE);

                                        });
                            }
                        }
                    }
                });


    }

    class NotificacionesAdapter extends RecyclerView.Adapter<NotificacionViewHolder> {

        @NonNull
        @Override
        public NotificacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new NotificacionViewHolder(ViewholderNotificacionBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull NotificacionViewHolder holder, int position) {
            Notificacion notificacion = notificaciones.get(position);

            holder.binding.nombreRivalNotificacion.setText(notificacion.nicknameRival);
//            holder.binding.rangoHora1.setText(notificacion.rangoHoraMin);
//            holder.binding.rangoHora2.setText(notificacion.rangoHoraMax);
//            holder.binding.diasDisponibles.setText(Arrays.toString(notificacion.diasDisponibles.toArray()));

            holder.itemView.setOnClickListener(v -> {

                viewModel.idNotiRivalLiveData.setValue(notificacion.id);
                viewModel.nombreRivalLiveData.setValue(notificacion.nicknameRival);
                viewModel.hora1RivalLiveData.setValue(notificacion.rangoHoraMin);
                viewModel.hora2RivalLiveData.setValue(notificacion.rangoHoraMax);
                viewModel.diasSelecRivalLiveData.setValue(notificacion.diasDisponibles);
                viewModel.uidRivalLiveData.setValue(notificacion.uidRival);

                Log.e("ABCD", "nombre: " + viewModel.nombreRivalLiveData.getValue() + " nombne: " + viewModel.hora1RivalLiveData.getValue() + " nombne: " + viewModel.hora2RivalLiveData.getValue() + " nombne: " + viewModel.diasSelecRivalLiveData.getValue());
                nav.navigate(R.id.action_notificacionesFragment_to_visualizacionNotificacionFragment);
            });
        }

        @Override
        public int getItemCount() {
            return notificaciones == null ? 10 : notificaciones.size();
        }

    }

    static class NotificacionViewHolder extends RecyclerView.ViewHolder {
        ViewholderNotificacionBinding binding;
        public NotificacionViewHolder(@NonNull ViewholderNotificacionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}


