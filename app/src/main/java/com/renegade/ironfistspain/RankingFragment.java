package com.renegade.ironfistspain;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.renegade.ironfistspain.databinding.FragmentRankingBinding;
import com.renegade.ironfistspain.databinding.ViewholderJugadorRankingBinding;
import com.renegade.ironfistspain.databinding.ViewholderNotificacionBinding;

import java.util.ArrayList;
import java.util.List;


public class RankingFragment extends BaseFragment {

    private FragmentRankingBinding binding;
    List<Jugador> jugadores = new ArrayList<>();
    JugadoresAdapter jugadoresAdapter = new JugadoresAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentRankingBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager verticalLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.listaRankingJugadores.setLayoutManager(verticalLayoutManager);
//        binding.listaNotificaciones.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        binding.listaRankingJugadores.setAdapter(jugadoresAdapter);

        db.collection(CollectionDB.USUARIOS)
                .orderBy("puntuacion", Query.Direction.DESCENDING)
//                .whereEqualTo("rol", "jugador")
                .addSnapshotListener((value, error) -> {
                    jugadores.clear();
                    for (QueryDocumentSnapshot jugador : value) {
                        if (jugador.get("rol").equals("jugador")) {
                            String nickname = jugador.getString("nickname");
                            Long puntuacion = jugador.getLong("puntuacion");

                            jugadores.add(new Jugador(nickname, puntuacion));
                            jugadoresAdapter.notifyDataSetChanged();

                        }
                    }
                });

    }

        class JugadoresAdapter extends RecyclerView.Adapter<JugadorViewHolder> {

            @NonNull
            @Override
            public JugadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new JugadorViewHolder(ViewholderJugadorRankingBinding.inflate(getLayoutInflater(), parent, false));
            }

            @Override
            public void onBindViewHolder(@NonNull JugadorViewHolder holder, int position) {
                Jugador jugador = jugadores.get(position);

                String posicionActual = String.valueOf(position+1);
                holder.binding.posicion.setText(posicionActual);
                holder.binding.nombreJugadorRanking.setText(jugador.nickname);
                holder.binding.puntuacionRanking.setText("" + jugador.puntuacion);
//            holder.binding.rangoHora1.setText(notificacion.rangoHoraMin);
//            holder.binding.rangoHora2.setText(notificacion.rangoHoraMax);
//            holder.binding.diasDisponibles.setText(Arrays.toString(notificacion.diasDisponibles.toArray()));

                holder.itemView.setOnClickListener(v -> {

                });
            }

            @Override
            public int getItemCount() {
                return jugadores == null ? 10 : jugadores.size();
            }


        }

        static class JugadorViewHolder extends RecyclerView.ViewHolder {
            ViewholderJugadorRankingBinding binding;

            public JugadorViewHolder(@NonNull ViewholderJugadorRankingBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }

