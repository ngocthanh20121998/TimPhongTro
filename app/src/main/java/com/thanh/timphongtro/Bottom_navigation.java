package com.thanh.timphongtro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

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

        navigationItemView = findViewById(R.id.navigation);
        navigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Toast.makeText(Bottom_navigation.this, "Home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.upload:
                        Toast.makeText(Bottom_navigation.this, "Upload", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.info:
                        Toast.makeText(Bottom_navigation.this, "Info", Toast.LENGTH_SHORT).show();
                        break;
                }

                return true;
            }
        });
    }
}