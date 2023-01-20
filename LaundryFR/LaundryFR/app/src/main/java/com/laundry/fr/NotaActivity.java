package com.laundry.fr;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.laundry.fr.databinding.ActivityNotaBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NotaActivity extends AppCompatActivity {

    private ActivityNotaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getIntent() != null){
            SimpleDateFormat i = new SimpleDateFormat("d/M/yyyy");
            SimpleDateFormat hari = new SimpleDateFormat("EEEE");
            SimpleDateFormat tanggal = new SimpleDateFormat("d MMMM yyyy");

            Date date = null;
            try {
                date = i.parse(getIntent().getStringExtra("tanggal"));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            binding.hari.setText(hari.format(date));
            binding.tanggal.setText(tanggal.format(date));
            binding.nama.setText(getIntent().getStringExtra("nama"));
            binding.berat.setText(getIntent().getStringExtra("berat") + " Kg");
            binding.harga.setText(getIntent().getStringExtra("harga"));
        }
    }
}