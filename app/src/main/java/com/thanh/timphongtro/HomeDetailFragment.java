package com.thanh.timphongtro;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class HomeDetailFragment extends Fragment {
    TextView tvTieuDe, tvGia, tvDienTich, tvDiaChi, tvMoTa;
    ImageView imageView;
    Button btCall, btSMS, btBack;
    FirebaseStorage storage = FirebaseStorage.getInstance();


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView   = view.findViewById(R.id.imageViewInfoHinhPhong);
        tvTieuDe    = view.findViewById(R.id.textViewTieuDePhong);
        tvGia       = view.findViewById(R.id.textViewGiaPhong);
        tvDienTich  = view.findViewById(R.id.textViewDienTichPhong);
        tvDiaChi    = view.findViewById(R.id.textViewDiaChiPhong);
        tvMoTa      = view.findViewById(R.id.textViewMoTaPhong);
        btCall      = view.findViewById(R.id.buttonCall);
        btSMS       = view.findViewById(R.id.buttonSMS);
        btBack      = view.findViewById(R.id.buttonBack);

        Bundle bundle = getArguments();
        if(bundle!= null){
            InfoPhongTro info = bundle.getParcelable("key");
            tvTieuDe.setText(info.tieuDe);
            tvGia.setText(String.format("%s Đồng",info.giaPhong));
            tvDienTich.setText(String.format("%s m2", info.dienTich));
            tvDiaChi.setText(info.diaChi);
            tvMoTa.setText(info.moTa);

            final StorageReference storageRef = storage.getReferenceFromUrl("gs://timphongtro-6748e.appspot.com");
            StorageReference pathReference = storageRef.child(String.format("%s",info.hinh));
            final long ONE_MEGABYTE = 512 * 512;
            pathReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imageView.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });

            clickCall(btCall,info.sdt);
            clickSMS(btSMS,info.sdt);
            clickBack(btBack);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_detail, container, false);
    }
    public void clickCall(Button call, final String phone){
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone)));
            }
        });
    }
    public void clickSMS(Button sms, final String phone){
        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phone)));
            }
        });
    }
    public void clickBack(Button back){
        back.setOnClickListener(new View.OnClickListener() {
            final Bottom_navigation mainActivity = (Bottom_navigation) getActivity();
            @Override
            public void onClick(View v) {
                if (mainActivity != null) {
                    mainActivity.goToHomeFragment();
                }
            }
        });
    }
}