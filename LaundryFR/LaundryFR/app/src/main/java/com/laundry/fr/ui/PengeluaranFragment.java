package com.laundry.fr.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.laundry.fr.R;
import com.laundry.fr.databinding.FragmentPengeluaranBinding;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class PengeluaranFragment extends Fragment {

    private FragmentPengeluaranBinding binding;
    private final String menu = "pengeluaran";
    List<DocumentSnapshot> data = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPengeluaranBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        layoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        FirebaseFirestore.getInstance().collection(menu).get()
                .addOnSuccessListener(task -> {
                    data.addAll(task.getDocuments());
                    binding.recyclerview.setLayoutManager(layoutManager);
                    binding.recyclerview.setAdapter(new Pengeluaran());
                    Toast.makeText(requireActivity(), "Data berhasil Di Ambil", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(requireActivity(), "Periksa Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
                });

        binding.tambah.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("menu", menu);

            DialogFragment dialogFragment = new DialogFragment();
            dialogFragment.setArguments(bundle);
            dialogFragment.show(requireActivity().getSupportFragmentManager(), "Pengeluaran");
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private class Pengeluaran extends RecyclerView.Adapter<Pengeluaran.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pengeluaran, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.belanja.setText(data.get(position).get("nama").toString());
            holder.harga.setText("Rp " + NumberFormat.getInstance().format(Long.valueOf(data.get(position).get("harga").toString())));
            holder.hapus.setOnClickListener(view -> FirebaseFirestore.getInstance().collection(menu).document(data.get(position).getId()).delete()
                    .addOnSuccessListener(task -> {
                        data.remove(position);
                        binding.recyclerview.setLayoutManager(layoutManager);
                        binding.recyclerview.setAdapter(new Pengeluaran());
                        Toast.makeText(requireActivity(), "Data berhasil Di Ambil", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(requireActivity(), "Periksa Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
                    }));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView belanja, harga, hapus;

            ViewHolder(View v) {
                super(v);
                belanja = v.findViewById(R.id.nama_belanja);
                hapus = v.findViewById(R.id.hapus);
                harga = v.findViewById(R.id.harga);
            }
        }
    }
}