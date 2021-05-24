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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class RetosPendientesFragment extends BaseFragment {

    private FragmentRetosPendientesBinding binding;

    List<Encuentro> retosPendientes = new ArrayList<>();

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
        binding.retosPendientesRecycler.setAdapter(retosPendientesAdapter);


        db.collection(CollectionDB.ENCUENTROS)
                .addSnapshotListener((value, error) -> {
                    retosPendientes.clear();
                    for (QueryDocumentSnapshot noti : value) {
                        if (noti != null) {
                            if (noti.getString("uidLocal").equals(user.getUid()) || noti.getString("uidVisitante").equals(user.getUid())) {
//                                if (){
//                                    Si el estado es diferente a enviado y el uidvisitante es diferente al tuyo
//                                }
                                String id = noti.getId();
                                String estado = noti.getString("estado");
//                                LocalDateTime fechaPeticion = noti.getDate("fechaPeticion");
                                String uidLocal = noti.getString("uidLocal");
                                String uidVisitante = noti.getString("uidVisitante");
                                List<Integer> diasDisponibles = (List<Integer>) noti.get("diasSeleccionados");
                                String rangoHoraMin = noti.getString("rangoHoraMin");
                                String rangoHoraMax = noti.getString("rangoHoraMax");

                                db.collection(CollectionDB.USUARIOS)
                                        .document(uidLocal)
                                        .get()
                                        .addOnSuccessListener(doc -> {
                                            String nicknameRival = doc.getString("nickname");

                                            Log.e("ABCD", "Nickname Rival: " + nicknameRival + " - Hora minima: " + rangoHoraMin + " - Hora maxima: " + rangoHoraMax + " - Dias Seleccionados: " + diasDisponibles);

                                            retosPendientes.add(new Encuentro(estado, uidLocal, uidVisitante, diasDisponibles, rangoHoraMin, rangoHoraMax, id));
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

            Encuentro encuentro = retosPendientes.get(position);

            holder.binding.imageEnProceso.setVisibility(View.GONE);
            holder.binding.imagePlanificado.setVisibility(View.GONE);
            holder.binding.imagePendiente.setVisibility(View.GONE);
            holder.binding.imageAceptado.setVisibility(View.GONE);
            holder.binding.imageCancelado.setVisibility(View.GONE);


            if (encuentro.uidLocal.equals(user.getUid())) {
                db.collection(CollectionDB.USUARIOS)
                        .document(encuentro.uidVisitante)
                        .get()
                        .addOnSuccessListener(doc -> {
                            holder.binding.nombreRivalNotificacion.setText(doc.getString("nickname"));
                        });
            } else if (encuentro.uidVisitante.equals(user.getUid())) {
                db.collection(CollectionDB.USUARIOS)
                        .document(encuentro.uidLocal)
                        .get()
                        .addOnSuccessListener(doc -> {
                            holder.binding.nombreRivalNotificacion.setText(doc.getString("nickname"));
                        });
            }

            if (encuentro.estado.equals("Enviado")) {
                holder.binding.imagePendiente.setVisibility(View.VISIBLE);
                holder.binding.textEstadoReto.setText("Enviado");
            }
            else if (encuentro.estado.equals("Aceptado")) {
                holder.binding.imageAceptado.setVisibility(View.VISIBLE);
                holder.binding.textEstadoReto.setText("Aceptado");
            }
            else if (encuentro.estado.equals("Cancelado")) {
                holder.binding.imageCancelado.setVisibility(View.VISIBLE);
                holder.binding.textEstadoReto.setText("Cancelado");
            }
            else if (encuentro.estado.equals("En proceso")) {
                holder.binding.imageEnProceso.setVisibility(View.VISIBLE);
                holder.binding.textEstadoReto.setText("En proceso");
            }
            else if (encuentro.estado.equals("Planificado")) {
                holder.binding.imagePlanificado.setVisibility(View.VISIBLE);
                holder.binding.textEstadoReto.setText("Planificado");
            }
            else if (encuentro.estado.equals("Finalizado")) {
                holder.binding.textEstadoReto.setText("Finalizado");
            }

            holder.itemView.setOnClickListener(v -> {
//                db.collection(CollectionDB.ENCUENTROS).document(encuentro.id).get().addOnSuccessListener(documentSnapshot -> {
//                    viewModel.idNotiRivalLiveData.setValue(documentSnapshot.getId());
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                });
//
//                db.collection(CollectionDB.ENCUENTROS).document(encuentro.id).get().addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        viewModel.idNotiRivalLiveData.setValue(task.getResult().getId());
//                        try {
//                            wait(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
                viewModel.idNotiRivalLiveData.setValue(encuentro.id);
                viewModel.estadoRetoLiveData.setValue(encuentro.estado);
                viewModel.hora1RivalLiveData.setValue(encuentro.rangoHoraMin);
                viewModel.hora2RivalLiveData.setValue(encuentro.rangoHoraMax);
                viewModel.diasSelecRivalLiveData.setValue(encuentro.diasSeleccionados);

                Log.e("ABCD", "nombre: " + viewModel.nombreRivalLiveData.getValue() + " nombne: " + viewModel.hora1RivalLiveData.getValue() + " nombne: " + viewModel.hora2RivalLiveData.getValue() + " nombne: " + viewModel.diasSelecRivalLiveData.getValue());
                nav.navigate(R.id.visualizacionRetoFragment);
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


