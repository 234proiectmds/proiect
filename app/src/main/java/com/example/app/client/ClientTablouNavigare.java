package com.example.app.client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.app.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ClientTablouNavigare extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_tablou_navigare);
        BottomNavigationView navigationView = findViewById(R.id.client_bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        String name = getIntent().getStringExtra("PAGE");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (name != null) {
            if (name.equalsIgnoreCase("Homepage")) {
                loadClientFragment(new ClientAcasaFragment());
            } else if (name.equalsIgnoreCase("PreparingPage")) {
                loadClientFragment(new ClientStatusComandaFragment());
            } else if (name.equalsIgnoreCase("DeliveryOrderPage")) {
                loadClientFragment(new ClientStatusComandaFragment());
            } else if (name.equalsIgnoreCase("ThankYouPage")) {
                loadClientFragment(new ClientAcasaFragment());
            }
        } else {
            loadClientFragment(new ClientAcasaFragment());
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.clientHome:
                fragment = new ClientAcasaFragment();
                break;
            case R.id.clientCart:
                fragment = new ClientCosFragment();
                break;
            case R.id.clientOrders:
                fragment = new ClientComenziFragment();
                break;
            case R.id.clientTrack:
                fragment = new ClientStatusComandaFragment();
                break;
            case R.id.clientProfile:
                fragment = new ClientProfilFragment();
                break;
        }
        return loadClientFragment(fragment);
    }

    private boolean loadClientFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
            return true;
        }
        return false;
    }
}