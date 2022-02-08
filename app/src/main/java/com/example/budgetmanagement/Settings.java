package com.example.budgetmanagement;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.budgetmanagement.databinding.FragmentSettingsBinding;

import java.util.Objects;

public class Settings extends Fragment implements View.OnClickListener{
    private DatabaseContent databaseContent;
    private FragmentSettingsBinding binding;
    private Account account;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseContent = new DatabaseContent();
        databaseContent.init();
        account = new Account();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        binding.setName.setOnClickListener(this);
        account = databaseContent.loadAccountFromDatabase(account -> {
            setAccount(account);
            setUsername();
        });
        return binding.getRoot();
    }

    private void setUsername(){
        binding.username.setText(account.personName);
    }

    protected void setDefaultAccount(){
        account.setEmail(databaseContent.getEmail());
        account.setCurrencyType("USD");
        account.setId(databaseContent.getUID());
        account.setPersonName(getString(R.string.default_name));
        account.setBudget(100);
        account.setBudgetLastMonth(0);
        account.setBudgetLeft(100);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setName:
                if(!binding.username.getText().toString().equals(account.personName)){
                    account.personName = binding.username.getText().toString();
                    databaseContent.saveToDatabase(account);
                }
        }
    }

    protected void setAccount(Account account){
        this.account = account;
    }
}