package com.bluz.bluzfilestyle;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bluz.bluzfilestyle.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setTitle("Home");
        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    getSupportActionBar().setTitle("Home");
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.category:
                    getSupportActionBar().setTitle("Category");
                    replaceFragment(new CategoryFragment());
                    break;
                case R.id.cart:
                    getSupportActionBar().setTitle("Cart");
                    replaceFragment(new CartFragment());
                    break;
                case R.id.login:
                    getSupportActionBar().setTitle("Profile");
                    replaceFragment(new LoginFragment());
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();

    }
}