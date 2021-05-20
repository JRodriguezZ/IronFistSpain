package com.renegade.ironfistspain;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.renegade.ironfistspain.databinding.FragmentRetosPendientesBinding;
import com.renegade.ironfistspain.databinding.ViewholderNotificacionBinding;
import com.renegade.ironfistspain.databinding.ViewholderRetoPendienteBinding;

import java.util.ArrayList;
import java.util.List;


public class RetosPendientesFragment extends BaseFragment {

    private FragmentRetosPendientesBinding binding;

    List<Notificacion> retosPendientes = new ArrayList<>();

    RetosPendientesAdapter retosPendientesAdapter = new RetosPendientesAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentRetosPendientesBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (retosPendientes.size() == 0) binding.noTienesRetosPend.setVisibility(View.VISIBLE);
        else binding.noTienesRetosPend.setVisibility(View.GONE);

        LinearLayoutManager verticalLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.retosPendientesRecycler.setLayoutManager(verticalLayoutManager);
//        binding.listaNotificaciones.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        binding.retosPendientesRecycler.setAdapter(retosPendientesAdapter);


        db.collection(CollectionDB.ENCUENTROS)
                .whereEqualTo("estado", "Enviado")
                .addSnapshotListener((value, error) -> {
                    retosPendientes.clear();
                    for (QueryDocumentSnapshot noti : value) {
                        if (noti != null) {
                            if (noti.getString("uidLocal").equals(user.getUid())){
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

                                            retosPendientes.add(new Notificacion(nicknameRival, rangoHoraMin, rangoHoraMax, diasDisponibles, uidLocal, id));
                                            retosPendientesAdapter.notifyDataSetChanged();

                                            if (retosPendientes.size() == 0) binding.noTienesRetosPend.setVisibility(View.VISIBLE);
                                            else binding.noTienesRetosPend.setVisibility(View.GONE);

                                        });
                            }
                        }
                    }
                });
    }

    class RetosPendientesAdapter extends RecyclerView.Adapter<RetoPendienteViewHolder> {

        @NonNull
        @Override
        public RetoPendienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RetosPendientesFragment.RetoPendienteViewHolder(ViewholderRetoPendienteBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RetoPendienteViewHolder holder, int position) {

            Notificacion notificacion = retosPendientes.get(position);

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
//                nav.navigate(R.id.action_notificacionesFragment_to_visualizacionNotificacionFragment);
            });
        }

        @Override
        public int getItemCount() {
            return retosPendientes == null ? 10 : retosPendientes.size();
        }

    }

    static class RetoPendienteViewHolder extends RecyclerView.ViewHolder {
        ViewholderRetoPendienteBinding binding;
        public RetoPendienteViewHolder(@NonNull ViewholderRetoPendienteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}


