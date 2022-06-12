package com.example.app.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.app.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminTablouNavigatie extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_tablou_navigatie);
        BottomNavigationView navigationView = findViewById(R.id.admin_bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        String name = getIntent().getStringExtra("PAGE");
        if (name != null) {
            if (name.equalsIgnoreCase("orderPage")) {
                loadAdminFragment(new AdminComenziInAsteptareFragment());
            } else if (name.equalsIgnoreCase("confirmPage")) {
                loadAdminFragment(new AdminComenziNoiFragment());
            } else if (name.equalsIgnoreCase("acceptOrderPage")) {
                loadAdminFragment(new AdminComenziNoiFragment());
            } else if (name.equalsIgnoreCase("deliveredPage")) {
                loadAdminFragment(new AdminComenziNoiFragment());
            }
        } else {
            loadAdminFragment(new AdminAcasaFragment());
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.adminHome:
                fragment = new AdminAcasaFragment();
                break;
            case R.id.pendingOrders:
                fragment = new AdminComenziInAsteptareFragment();
                break;
            case R.id.Orders:
                fragment = new AdminComenziNoiFragment();
                break;
            case R.id.adminProfile:
                fragment = new AdminProfilFragment();
                break;
        }
        return loadAdminFragment(fragment);
    }

    private boolean loadAdminFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
            return true;
        }
        return false;
    }
}