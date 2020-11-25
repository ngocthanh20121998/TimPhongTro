package com.thanh.timphongtro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;



public class Bottom_navigation extends AppCompatActivity {
    BottomNavigationView navigationItemView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        Intent user = getIntent();

        navigationItemView = findViewById(R.id.navigation);
        loadFragment(new HomeFragment());
        navigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.home:
                        fragment = new HomeFragment();
                        Toast.makeText(Bottom_navigation.this, "Home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.upload:
                        fragment = new UploadFragment();
                        Toast.makeText(Bottom_navigation.this, "Upload", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.info:
                        fragment = new InfoFragment();
                        Toast.makeText(Bottom_navigation.this, "Info", Toast.LENGTH_SHORT).show();
                        break;
                }

                return loadFragment(fragment);
            }
        });
    }
    private boolean loadFragment (Fragment fragment){
        if(fragment != null ){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            return true;
        }
        return false;
    }



}