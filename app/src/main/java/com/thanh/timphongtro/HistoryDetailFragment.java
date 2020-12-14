package com.thanh.timphongtro;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;

public class HistoryDetailFragment extends Fragment {

    EditText edtTieuDe, edtGia, edtDienTich, edtDiaChi, edtSDT, edtMoTa;
    ImageView imgUpdate;
    Button btnCapNhat, btnXoa, btnHuy;
    DatabaseReference mdata;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    InfoPhongTro info;
    int REQUEST_CODE_IMAGE = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history_detail, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Bottom_navigation mainActivity = (Bottom_navigation) getActivity();
        mdata = FirebaseDatabase.getInstance().getReference();
        final StorageReference storageRef = storage.getReferenceFromUrl("gs://timphongtro-6748e.appspot.com");

        edtTieuDe   = view.findViewById(R.id.editTextUpdateTieuDe);
        edtGia      = view.findViewById(R.id.editTextUpdateGia);
        edtDienTich = view.findViewById(R.id.editTextUpdateDienTich);
        edtDiaChi   = view.findViewById(R.id.editTextUpdateDiaChi);
        edtSDT      = view.findViewById(R.id.editTextUpdateSDT);
        edtMoTa     = view.findViewById(R.id.editTextUpdateMoTa);
        imgUpdate   = view.findViewById(R.id.imageViewUpdateHinh);
        btnHuy      = view.findViewById(R.id.buttonHuy);
        btnCapNhat  = view.findViewById(R.id.buttonCapNhat);
        btnXoa      = view.findViewById(R.id.buttonXoa);


        Bundle bundle = getArguments();
        if(bundle!=null){
            info = bundle.getParcelable("key");
            edtTieuDe.setText(info.tieuDe);
            edtGia.setText(info.giaPhong);
            edtDienTich.setText(info.dienTich);
            edtDiaChi.setText(info.diaChi);
            edtSDT.setText(info.sdt);
            edtMoTa.setText(info.moTa);

            StorageReference pathReference = storageRef.child(String.format("%s",info.hinh));
            final long ONE_MEGABYTE = 512 * 512;
            pathReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imgUpdate.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });
        }

        imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_IMAGE);
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivity!=null){
                    mainActivity.goToHistory();
                }
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference myRef = db.getReference("ThongTinPhongTro");
                myRef.child(""+info.id).removeValue();

                StorageReference desertRef = storageRef.child(""+info.hinh);
                desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // File deleted successfully
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Uh-oh, an error occurred!
                    }
                });

                if (mainActivity!=null){
                    mainActivity.goToHistory();
                }
            }
        });

        btnCapNhat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String tieuDe = edtTieuDe.getText().toString();
                String gia = edtGia.getText().toString();
                String dienTich = edtDienTich.getText().toString();
                String diaChi = edtDiaChi.getText().toString();
                String sdt = edtSDT.getText().toString();
                String moTa = edtMoTa.getText().toString();
                if(tieuDe.equals("")||gia.equals("")||dienTich.equals("")||sdt.equals("")||diaChi.equals("")){
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    StorageReference mountainsRef = storageRef.child(info.hinh);
                    imgUpdate.setDrawingCacheEnabled(true);
                    imgUpdate.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) imgUpdate.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] data = baos.toByteArray();

                    UploadTask uploadTask = mountainsRef.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    });

                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = db.getReference("ThongTinPhongTro");
                    myRef.child("" + info.id).child("tieuDe").setValue(tieuDe);
                    myRef.child("" + info.id).child("giaPhong").setValue(gia);
                    myRef.child("" + info.id).child("dienTich").setValue(dienTich);
                    myRef.child("" + info.id).child("diaChi").setValue(diaChi);
                    myRef.child("" + info.id).child("sdt").setValue(sdt);
                    myRef.child("" + info.id).child("moTa").setValue(moTa);
                    Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    if(mainActivity!=null){
                        mainActivity.goToHistory();
                    }
                }
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgUpdate.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}