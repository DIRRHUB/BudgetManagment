package com.example.budgetmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.example.budgetmanagement.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private static ActivityLoginBinding binding;
    private DatabaseContent databaseContent;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        intent = new Intent(this, MainActivity.class);
        databaseContent = new DatabaseContent().init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (databaseContent.checkAuth()) {
            startActivity(intent);
        }
    }

    public void onClickRegister(View view) {
        SpecialFunction.hideKeyboard(view);
        if (!TextUtils.isEmpty(binding.textEmail.getText().toString()) && !TextUtils.isEmpty(binding.textPassword.getText().toString())) {
            databaseContent.register(binding.textEmail.getText().toString(), binding.textPassword.getText().toString());
        }
    }

    public void onClickLogin(View view) {
        SpecialFunction.hideKeyboard(view);
        if (!TextUtils.isEmpty(binding.textEmail.getText().toString()) && !TextUtils.isEmpty(binding.textPassword.getText().toString())) {
            databaseContent.login(binding.textEmail.getText().toString(), binding.textPassword.getText().toString());
        }
    }

    public void onClickNext(View view) {
        if (databaseContent.checkAuth()) {
            startActivity(intent);
        }
    }

    public static void updateUILoggedIn() {
        binding.bNext.setVisibility(View.VISIBLE);
        binding.textHello.setVisibility(View.VISIBLE);
        binding.constraintLayout.setVisibility(View.GONE);
        binding.textRegistration.setVisibility(View.GONE);
    }

    public static void updateUILoggedOut() {
        binding.bNext.setVisibility(View.GONE);
        binding.textHello.setVisibility(View.GONE);
        binding.constraintLayout.setVisibility(View.VISIBLE);
        binding.textRegistration.setVisibility(View.VISIBLE);
    }
}