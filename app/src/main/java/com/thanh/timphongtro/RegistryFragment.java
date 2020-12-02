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




public class RegistryFragment extends Fragment {
    EditText edtEmailDangKy, edtPasswordDangKy;
    Button btnDangKy;

    FirebaseAuth mAuthentication;

   // final Context context = getContext();


    public RegistryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registry, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuthentication = FirebaseAuth.getInstance();
        Button btnTroVe = view.findViewById(R.id.buttonTroVe);
        btnDangKy = view.findViewById(R.id.buttonDangKy);
        edtEmailDangKy = view.findViewById(R.id.editTextEmail);
        edtPasswordDangKy = view.findViewById(R.id.editTextPassword);

        final NavController navController = Navigation.findNavController(view);

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DangKyTK();
            }
        });

        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_dangKy_to_dangNhap);
            }
        });
    }

    private void DangKyTK(){
        String email = edtEmailDangKy.getText().toString();
        String password = edtPasswordDangKy.getText().toString();
        if(email.equals("") || password.equals("")){
            Toast.makeText(getContext(), "Tài khoản hoặc mật khẫu trống!!", Toast.LENGTH_SHORT).show();
        }else{
            mAuthentication.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getContext(), "Đăng ký thành công!!", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getContext(), "Tài khoản đã tồn tại!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }


    }

    }