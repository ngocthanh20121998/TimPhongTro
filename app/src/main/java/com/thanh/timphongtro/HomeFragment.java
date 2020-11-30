package com.thanh.timphongtro;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    ArrayList<InfoPhongTro> listPT;
    AdapterInfoPT adapterInfoPT;
    ListView lvPT;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listPT = new ArrayList<>();
        listPT.add(new InfoPhongTro(123,"abc","Phòng trọ 123","1000000","32",
                "123123123", "123 abc", "abc xyz", null));
        listPT.add(new InfoPhongTro(321,"abc","abcd2","1232","122",
                "123123123", "123 abc", "abc xyz", null));
        listPT.add(new InfoPhongTro(3212,"abc","abcd3","1233","123",
                "123123123", "123 abc", "abc xyz", null));
        listPT.add(new InfoPhongTro(3211,"abc","abcd4","1234","124",
                "123123123", "123 abc", "abc xyz", null));
        adapterInfoPT = new AdapterInfoPT(getContext(),R.layout.layout_info_pt, listPT);
        lvPT = view.findViewById(R.id.listViewPhongTro);
        lvPT.setAdapter(adapterInfoPT);
    }

}