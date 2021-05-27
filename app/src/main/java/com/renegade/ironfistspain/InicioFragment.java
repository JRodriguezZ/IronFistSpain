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

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.renegade.ironfistspain.databinding.FragmentInicioBinding;
import com.renegade.ironfistspain.databinding.ViewholderProximoPartidoBinding;

import java.util.ArrayList;
import java.util.List;


public class InicioFragment extends BaseFragment {

    private FragmentInicioBinding binding;

    List<Encuentro> proximosRetos = new ArrayList<>();

    ProximosRetosAdapter proximosRetosAdapter = new ProximosRetosAdapter();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentInicioBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth.addAuthStateListener(firebaseAuth -> {
            if (auth.getCurrentUser() == null) {
                nav.navigate(R.id.startFragment);
            }
        });

        LinearLayoutManager verticalLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.listaProxRetos.setLayoutManager(verticalLayoutManager);
        binding.listaProxRetos.setAdapter(proximosRetosAdapter);

        db.collection(CollectionDB.ENCUENTROS).addSnapshotListener((value, error) -> {
            proximosRetos.clear();
            for (QueryDocumentSnapshot reto: value){
                if (reto.getString("estado").equals("Planificado")) {

                    String fechaEncuentro = reto.getString("fechaEncuentro");
                    String horaEncuentro = reto.getString("horaEncuentro");
                    String uidLocal = reto.getString("uidLocal");
                    String uidVisitante = reto.getString("uidVisitante");

                    db.collection(CollectionDB.USUARIOS).document(uidLocal).get().addOnSuccessListener(doc -> {
                        String imagenLocal = doc.getString("imagen");
                        String nombreLocal = doc.getString("nickname");

                        db.collection(CollectionDB.USUARIOS).document(uidVisitante).get().addOnSuccessListener(doc1 -> {
                            String imagenVisitante = doc1.getString("imagen");
                            String nombreVisitante = doc1.getString("nickname");

                            Log.e("ABCD", "Encuentro añadido " + nombreLocal + ", " + nombreVisitante + ", " + fechaEncuentro + ", " + horaEncuentro);
                            Encuentro encuentro = new Encuentro(fechaEncuentro, horaEncuentro, nombreLocal, imagenLocal, nombreVisitante, imagenVisitante);
                            proximosRetos.add(encuentro);
                            proximosRetosAdapter.notifyDataSetChanged();
                        });

                    });
                }
            }


        });

    }


    class ProximosRetosAdapter extends RecyclerView.Adapter<ProxRetoViewHolder>{

        @NonNull
        @Override
        public ProxRetoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ProxRetoViewHolder(ViewholderProximoPartidoBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ProxRetoViewHolder holder, int position) {
            Encuentro encuentro = proximosRetos.get(position);

            holder.binding.nombreLocal.setText(encuentro.nombreLocal);
            Glide.with(requireView()).load(encuentro.imagenLocal).circleCrop().into(holder.binding.imagenLocal);

            holder.binding.nombreVisitante.setText(encuentro.nombreVisitante);
            Glide.with(requireView()).load(encuentro.imagenVisitante).circleCrop().into(holder.binding.imagenVisitante);

            holder.binding.fechaReto.setText(encuentro.fechaEncuentro);
            holder.binding.horaReto.setText(encuentro.horaEncuentro);
        }

        @Override
        public int getItemCount() {
            return proximosRetos.size();
        }
    }

    static class ProxRetoViewHolder extends RecyclerView.ViewHolder{
        ViewholderProximoPartidoBinding binding;
        public ProxRetoViewHolder(@NonNull ViewholderProximoPartidoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}