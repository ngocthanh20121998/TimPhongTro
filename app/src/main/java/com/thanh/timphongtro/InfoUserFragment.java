package com.thanh.timphongtro;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class InfoUserFragment extends Fragment {
    Button btnBack, btnUpdate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Bottom_navigation mainActivity = (Bottom_navigation) getActivity();

        btnBack     = view.findViewById(R.id.buttonGoToInfo);
        btnUpdate   = view.findViewById(R.id.buttonUpdate);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivity!=null){
                    mainActivity.goToInfo();
                }
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivity!=null){
                    mainActivity.goToUpdateInfo();
                }
            }
        });
    }
}