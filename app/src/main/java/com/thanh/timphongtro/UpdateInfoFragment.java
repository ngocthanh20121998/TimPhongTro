package com.thanh.timphongtro;

import android.content.Intent;
import android.graphics.Bitmap;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;


public class UpdateInfoFragment extends Fragment {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    DatabaseReference mData;
    Button btnSave, btnBack;
    EditText edtTen, edtNgaySinh, edtSDT, edtDiaChi;
    ImageView imgUser;
    int REQUEST_CODE_IMAGE = 1;
    boolean checkHinh = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = mAuth.getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Bottom_navigation mainActivity = (Bottom_navigation) getActivity();
        btnBack     = view.findViewById(R.id.buttonGoToInfoUser);
        btnSave     = view.findViewById(R.id.buttonSave);
        edtTen      = view.findViewById(R.id.edtTenUser);
        edtNgaySinh = view.findViewById(R.id.edtNgaySinhUser);
        edtSDT      = view.findViewById(R.id.edtSDTUser);
        edtDiaChi   = view.findViewById(R.id.edtDiaChiUser);
        imgUser     = view.findViewById(R.id.imgUpdateInfoUser);

        final StorageReference storageRef = storage.getReferenceFromUrl("gs://timphongtro-6748e.appspot.com");
        mData = FirebaseDatabase.getInstance().getReference();

        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_IMAGE);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String ten = edtTen.getText().toString();
                final String ngaySinh = edtNgaySinh.getText().toString();
                final String sdt =  edtSDT.getText().toString();
                final String diaChi = edtDiaChi.getText().toString();
                if(!checkHinh){
                    Toast.makeText(getContext(), "Bạn cần chụp hình", Toast.LENGTH_SHORT).show();
                } else{
                    if(ten.equals("")||ngaySinh.equals("")||sdt.equals("")||diaChi.equals("")){
                        Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    }else{
                        final String nameImg = "User"+user.getUid()+".png";
                        StorageReference mountainsRef = storageRef.child(nameImg);
                        imgUser.setDrawingCacheEnabled(true);
                        imgUser.buildDrawingCache();
                        Bitmap bitmap = ((BitmapDrawable) imgUser.getDrawable()).getBitmap();
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
                                InfoUser info = new InfoUser(nameImg,
                                        ten,
                                        ngaySinh,
                                        sdt,
                                        diaChi);
                                String childName = user.getUid();
                                mData.child("ThongTinUser").child(childName).setValue(info, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        if(error == null){
                                            Toast.makeText(getContext(), "Lưu dữ liệu thành công!!", Toast.LENGTH_SHORT).show();
                                            if(mainActivity!=null){
                                                mainActivity.goToInfoUser();
                                            }
                                        }else {
                                            Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }

                        });
                    }
                }


            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivity!=null){
                    mainActivity.goToInfoUser();
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgUser.setImageBitmap(bitmap);
            checkHinh = true;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}