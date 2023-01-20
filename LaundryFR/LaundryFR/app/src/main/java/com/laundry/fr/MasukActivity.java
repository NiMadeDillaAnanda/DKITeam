package com.laundry.fr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.laundry.fr.databinding.ActivityMasukBinding;

public class MasukActivity extends AppCompatActivity {

    private ActivityMasukBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMasukBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.masuk.setOnClickListener(view -> {
            Intent intent = new Intent(MasukActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}