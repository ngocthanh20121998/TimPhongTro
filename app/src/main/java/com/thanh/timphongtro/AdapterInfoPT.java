package com.thanh.timphongtro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Date;

public class AdapterInfoPT extends BaseAdapter {
    private final Context context;
    private final int layout;
    private final ArrayList<InfoPhongTro> listPT;
    FirebaseStorage storage = FirebaseStorage.getInstance();


    public AdapterInfoPT(Context context, int layout, ArrayList<InfoPhongTro> listPT) {
        this.context = context;
        this.layout = layout;
        this.listPT = listPT;
    }

    @Override
    public int getCount() {
        return listPT.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final StorageReference storageRef = storage.getReferenceFromUrl("gs://timphongtro-6748e.appspot.com");

        convertView = inflater.inflate(layout,null);
        final ImageView ivHinh = convertView.findViewById(R.id.imageViewHinh);
        TextView tvTieuDe = convertView.findViewById(R.id.textViewTieuDe);
        TextView tvGia = convertView.findViewById(R.id.textViewGia);
        TextView tvDienTich = convertView.findViewById(R.id.textViewDienTich);

        InfoPhongTro infoPhongTro = listPT.get(position);
        tvTieuDe.setText(infoPhongTro.tieuDe);
        tvGia.setText(String.format("Giá: %s Đồng",infoPhongTro.giaPhong));
        tvDienTich.setText(String.format("Diện tích: %s m2",infoPhongTro.dienTich));

        StorageReference pathReference = storageRef.child(String.format("%s",infoPhongTro.hinh));
        final long ONE_MEGABYTE = 512 * 512;
        pathReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap  bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                ivHinh.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        return convertView;
    }
}
