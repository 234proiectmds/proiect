package com.example.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.app.adminCakesPanel.AdminHomeFragment;
import com.example.app.adminCakesPanel.AdminOrderFragment;
import com.example.app.adminCakesPanel.AdminPendingOrdersFragment;
import com.example.app.adminCakesPanel.AdminProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminCakes_Navigation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cakes_navigation);
        BottomNavigationView navigationView = findViewById(R.id.admin_bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.adminHome:
                fragment = new AdminHomeFragment();
                break;
            case R.id.pendingOrders:
                fragment = new AdminPendingOrdersFragment();
                break;
            case R.id.Orders:
                fragment = new AdminOrderFragment();
                break;
            case R.id.adminProfile:
                fragment = new AdminProfileFragment();
                break;
        }
        return loadAdminFragment(fragment);
    }

    private boolean loadAdminFragment(Fragment fragment)
    {
        if (fragment != null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
            return true;
        }
        return false;
    }
}