package com.thanh.timphongtro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;



public class Bottom_navigation extends AppCompatActivity {
    BottomNavigationView navigationItemView;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        Intent user = getIntent();

        navigationItemView = findViewById(R.id.navigation);
        loadFragment(new HomeFragment());
        navigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.home:
                        fragment = new HomeFragment();
//                        Toast.makeText(Bottom_navigation.this, "Home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.upload:
                        fragment = new UploadFragment();
//                        Toast.makeText(Bottom_navigation.this, "Upload", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.info:
                        fragment = new InfoFragment();
//                        Toast.makeText(Bottom_navigation.this, "Info", Toast.LENGTH_SHORT).show();
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

    public void goToHomeDetailFragment(InfoPhongTro info){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        HomeDetailFragment homeDetailFragment = new HomeDetailFragment();
        Bundle bundle = new Bundle();

        bundle.putParcelable("key", info);
        homeDetailFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment_container,homeDetailFragment);
        fragmentTransaction.commit();
    }
    public void goToHistoryDetailFragment(InfoPhongTro info){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        HistoryDetailFragment historyDetailFragment = new HistoryDetailFragment();
        Bundle bundle = new Bundle();

        bundle.putParcelable("key", info);
        historyDetailFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment_container,historyDetailFragment);
        fragmentTransaction.commit();
    }
    public void goToHomeFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        fragmentTransaction.replace(R.id.fragment_container,homeFragment);
        fragmentTransaction.commit();
    }
    public void goToInfoUser(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        InfoUserFragment infoUserFragment = new InfoUserFragment();
        fragmentTransaction.replace(R.id.fragment_container,infoUserFragment);
        fragmentTransaction.commit();
    }

    public void goToInfo(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        InfoFragment infoFragment = new InfoFragment();
        fragmentTransaction.replace(R.id.fragment_container,infoFragment);
        fragmentTransaction.commit();
    }

    public void goToHistory(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        HistoryFragment historyFragment = new HistoryFragment();
        fragmentTransaction.replace(R.id.fragment_container,historyFragment);
        fragmentTransaction.commit();
    }

    public void goToUpdateInfo() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        UpdateInfoFragment updateInfoFragment = new UpdateInfoFragment();
        fragmentTransaction.replace(R.id.fragment_container,updateInfoFragment);
        fragmentTransaction.commit();
    }
}