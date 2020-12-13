package com.thanh.timphongtro;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class InfoUserFragment extends Fragment {
    Button btnBack, btnUpdate;
    TextView tvTen, tvNgaySinh, tvSDT, tvDiaChi;
    ImageView imgUser;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = mAuth.getCurrentUser();
    }

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
        tvTen       = view.findViewById(R.id.textViewTenUser);
        tvNgaySinh  = view.findViewById(R.id.textViewNgaySinhUser);
        tvSDT       = view.findViewById(R.id.textViewSDTUser);
        tvDiaChi    = view.findViewById(R.id.textViewDiaChiUser);
        imgUser     = view.findViewById(R.id.imgInfoUser);


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
        getInfoUser();
    }

    public void getInfoUser(){
        DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
        final StorageReference storageRef = storage.getReferenceFromUrl("gs://timphongtro-6748e.appspot.com");

        final String hinh = "User"+user.getUid()+".png";
        mData.child("ThongTinUser").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ArrayList<InfoUser> listUser = new ArrayList<>();
                InfoUser infoUser = snapshot.getValue(InfoUser.class);
                listUser.add(infoUser);

                if(infoUser!=null){
                    for (InfoUser info : listUser){
                        if(info.hinh.equals(hinh)){
                            tvTen.setText(infoUser.ten);
                            tvNgaySinh.setText(infoUser.ngaySinh);
                            tvSDT.setText(infoUser.sdt);
                            tvDiaChi.setText(infoUser.diaChi);
                            StorageReference pathReference = storageRef.child(String.format("%s",infoUser.hinh));
                            final long ONE_MEGABYTE = 512 * 512;
                            pathReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                @Override
                                public void onSuccess(byte[] bytes) {
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                    imgUser.setImageBitmap(bitmap);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle any errors
                                }
                            });
                        }
                }

                }

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