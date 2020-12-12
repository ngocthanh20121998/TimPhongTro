package com.thanh.timphongtro;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


public class InfoFragment extends Fragment {
    Button btnInfoUser, btnHistory;
    ImageView imgUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Bottom_navigation mainActivity = (Bottom_navigation) getActivity();

        btnInfoUser = view.findViewById(R.id.btnInfoUser);
        btnHistory  = view.findViewById(R.id.btnHistory);
        imgUser     = view.findViewById(R.id.imgInfo);

        btnInfoUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivity!=null){
                    mainActivity.goToInfoUser();
                }
            }
        });
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivity!=null){
                    mainActivity.goToHistory();
                }
            }
        });
    }
}