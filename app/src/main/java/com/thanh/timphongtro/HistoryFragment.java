package com.thanh.timphongtro;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {
    ArrayList<InfoPhongTro> listHistory;
    AdapterInfoPT adapterHistory;
    ListView lvHistory;
    DatabaseReference mdata;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user;

    Button btnXoaAll, btnback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = mAuth.getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Bottom_navigation mainActivity = (Bottom_navigation) getActivity();
        btnback = view.findViewById(R.id.buttonHistoryTroVe);

        updateListHistory(view);
        lvHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InfoPhongTro data = listHistory.get(position);
                if (mainActivity != null) {
                    mainActivity.goToHistoryDetailFragment(data);
                }
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivity!=null){
                    mainActivity.goToInfo();
                }
            }
        });

    }

    public void updateListHistory(View view){
        mdata = FirebaseDatabase.getInstance().getReference();
        listHistory = new ArrayList<>();
        adapterHistory = new AdapterInfoPT(getContext(),R.layout.layout_info_pt, listHistory);
        lvHistory = view.findViewById(R.id.listViewHistory);
        lvHistory.setAdapter(adapterHistory);
        mdata.child("ThongTinPhongTro").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ArrayList<InfoPhongTro> list = new ArrayList<>();
                InfoPhongTro data = snapshot.getValue(InfoPhongTro.class);
                list.add(data);
                for (InfoPhongTro info : list){
                    if(info.idUser.equals(user.getUid())){
                        listHistory.add(info);
                    }
                }
                adapterHistory.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}