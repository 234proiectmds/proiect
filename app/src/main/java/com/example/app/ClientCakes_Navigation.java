package com.example.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.app.clientCakesPanel.ClientCartFragment;
import com.example.app.clientCakesPanel.ClientHomeFragment;
import com.example.app.clientCakesPanel.ClientOrderFragment;
import com.example.app.clientCakesPanel.ClientProfileFragment;
import com.example.app.clientCakesPanel.ClientTrackFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ClientCakes_Navigation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_cakes_navigation);
        BottomNavigationView navigationView = findViewById(R.id.client_bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        String name = getIntent().getStringExtra("PAGE");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (name != null){
            if (name.equalsIgnoreCase("Homepage")){
                loadClientFragment(new ClientHomeFragment());
            }
            else if (name.equalsIgnoreCase("PreparingPage")){
                loadClientFragment(new ClientTrackFragment());
            }
            else if (name.equalsIgnoreCase("DeliveryOrderPage")){
                loadClientFragment(new ClientTrackFragment());
            }
            else if (name.equalsIgnoreCase("ThankYouPage")){
                loadClientFragment(new ClientHomeFragment());
            }
        }
        else {
            loadClientFragment(new ClientHomeFragment());
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.clientHome:
                fragment = new ClientHomeFragment();
                break;
            case R.id.clientCart:
                fragment = new ClientCartFragment();
                break;
            case R.id.clientOrders:
                fragment = new ClientOrderFragment();
                break;
            case R.id.clientTrack:
                fragment = new ClientTrackFragment();
                break;
            case R.id.clientProfile:
                fragment = new ClientProfileFragment();
                break;
        }
        return loadClientFragment(fragment);
    }
    private boolean loadClientFragment(Fragment fragment)
    {
        if (fragment != null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
            return true;
        }
        return false;
    }
}