package com.thanh.timphongtro;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

public class UploadFragment extends Fragment {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user;
    EditText edtTieuDe, edtGia, edtDienTich, edtDiaChi, edtSDT, edtMoTa;
    Button btnUpload, btnDelete;
    ImageView imgHinh;
    int REQUEST_CODE_IMAGE = 1;
    DatabaseReference mData;

    FirebaseStorage storage = FirebaseStorage.getInstance();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         user = mAuth.getCurrentUser();
//        if(user!= null){
//            Toast.makeText(getContext(), user.getEmail().toString(), Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(getContext(), "Chưa đăng nhập", Toast.LENGTH_SHORT).show();
//        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgHinh     = view.findViewById(R.id.imageViewAddHinh);
        edtTieuDe   = view.findViewById(R.id.editTextAddTieuDe);
        edtGia      = view.findViewById(R.id.editTextTextAddGia);
        edtDienTich = view.findViewById(R.id.editTextAddDienTich);
        edtDiaChi   = view.findViewById(R.id.editTextAddDiaChi);
        edtSDT      = view.findViewById(R.id.editTextSDT);
        edtMoTa     = view.findViewById(R.id.editTextMoTa);
        btnUpload   = view.findViewById(R.id.buttonUpload);
        btnDelete   = view.findViewById(R.id.buttonDelete);
        final StorageReference storageRef = storage.getReferenceFromUrl("gs://timphongtro-6748e.appspot.com");
        mData = FirebaseDatabase.getInstance().getReference();


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgHinh.setImageBitmap(null);
                edtTieuDe.setText("");
                edtGia.setText("");
                edtDienTich.setText("");
                edtDiaChi.setText("");
                edtSDT.setText("");
                edtMoTa.setText("");

            }
        });
        imgHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_IMAGE);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();
                final String nameImg = "Img"+calendar.getTimeInMillis()+".png";
                StorageReference mountainsRef = storageRef.child(nameImg);
                imgHinh.setDrawingCacheEnabled(true);
                imgHinh.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) imgHinh.getDrawable()).getBitmap();
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
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.

                        Toast.makeText(getContext(), "Lưu ảnh Thành công", Toast.LENGTH_SHORT).show();
                        Calendar cal = Calendar.getInstance();
                        InfoPhongTro info = new InfoPhongTro(cal.getTimeInMillis(),
                                nameImg,
                                edtTieuDe.getText().toString(),
                                edtGia.getText().toString(),
                                edtDienTich.getText().toString(),
                                edtSDT.getText().toString(),
                                edtDiaChi.getText().toString(),
                                edtMoTa.getText().toString(),
                                cal.getTime());

                        mData.child("InfoPhongTro").child(user.getUid()).push().setValue(info, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                if(error == null){
                                    Toast.makeText(getContext(), "Lưu dữ liệu thành công!!", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                });
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgHinh.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upload, container, false);

    }
}