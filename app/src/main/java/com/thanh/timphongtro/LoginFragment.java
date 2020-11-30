package com.thanh.timphongtro;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginFragment extends Fragment {
    Button btnDangNhap;
    EditText edtTaiKhoan, edtMatKhau;
    FirebaseAuth mAuthentication;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnTaoTk = view.findViewById(R.id.buttonTaoTaiKhoan);
        mAuthentication = FirebaseAuth.getInstance();
        btnDangNhap     = view.findViewById(R.id.buttonDangNhap);
        edtTaiKhoan     = view.findViewById(R.id.editTextEmailDangNhap);
        edtMatKhau      = view.findViewById(R.id.editTextPasswordDangNhap);

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DangNhapTK(v);
            }
        });

        final NavController navController = Navigation.findNavController(view);
        btnTaoTk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_dangNhap_to_dangKy);
            }
        });
    }
    public void DangNhapTK(View view){
        String email = edtTaiKhoan.getText().toString();
        String password = edtMatKhau.getText().toString();
        final NavController navController = Navigation.findNavController(view);
        mAuthentication.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext() , "Đăng nhập thành công!!", Toast.LENGTH_SHORT).show();
                            navController.navigate(R.id.action_dangNhap_to_bottom_navigation);

                        } else {

                            Toast.makeText(getContext() , "Tài khoản hoặc mật khẫu sai!!", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
}