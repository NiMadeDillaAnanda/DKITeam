package com.laundry.fr.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.FirebaseFirestore;
import com.laundry.fr.databinding.FragmentDoalogBinding;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DialogFragment extends androidx.fragment.app.DialogFragment {

    private FragmentDoalogBinding binding;
    String menu = "";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDoalogBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Bundle bundle = getArguments();
        if (bundle != null){
            menu = bundle.getString("menu");
        }

        if (menu.equals("pengeluaran")){
            binding.tTanggal.setVisibility(View.GONE);
            binding.tanggal.setVisibility(View.GONE);
            binding.tNama.setText("Belanja Umum");
            binding.berat.setVisibility(View.GONE);
            binding.tBerat.setVisibility(View.GONE);
        } else {
            binding.tanggal.setOnClickListener(view -> {
                Toast.makeText(requireActivity(), "Tanggal Masuk", Toast.LENGTH_SHORT).show();
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(requireActivity(), (view1, year, month, dayOfMonth) -> binding.tanggal.setText(dayOfMonth + "/" + (month+1) + "/" + year), calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            });
        }

        binding.simpan.setOnClickListener(view -> {
            if (menu.equals("pendapatan")){
                if (binding.tanggal.getText().toString().isEmpty()){
                    Toast.makeText(requireActivity(), "Tanggal Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                } else if (binding.nama.getText().toString().isEmpty()){
                    Toast.makeText(requireActivity(), "Nama Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                } else if (binding.berat.getText().toString().isEmpty()){
                    Toast.makeText(requireActivity(), "Berat Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                } else if (binding.harga.getText().toString().isEmpty()){
                    Toast.makeText(requireActivity(), "Harga Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, Object> data = new HashMap<>();
                    data.put("tanggal", binding.tanggal.getText().toString());
                    data.put("nama", binding.nama.getText().toString());
                    data.put("berat", binding.berat.getText().toString());
                    data.put("harga", binding.harga.getText().toString());
                    FirebaseFirestore.getInstance().collection(menu).add(data)
                            .addOnSuccessListener(task -> {
                                Toast.makeText(requireActivity(), "Data berhasil Di Tambah", Toast.LENGTH_SHORT).show();
                                dismiss();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(requireActivity(), "Periksa Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
                            });
                }
            } else {
                if (binding.nama.getText().toString().isEmpty()){
                    Toast.makeText(requireActivity(), "Belanja Umum Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                } else if (binding.harga.getText().toString().isEmpty()){
                    Toast.makeText(requireActivity(), "Harga Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, Object> data = new HashMap<>();
                    data.put("nama", binding.nama.getText().toString());
                    data.put("harga", binding.harga.getText().toString());
                    FirebaseFirestore.getInstance().collection(menu).add(data)
                            .addOnSuccessListener(task -> {
                                Toast.makeText(requireActivity(), "Data berhasil Di Tambah", Toast.LENGTH_SHORT).show();
                                dismiss();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(requireActivity(), "Periksa Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
                            });
                }
            }
        });
        binding.batal.setOnClickListener(view -> dismiss());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}