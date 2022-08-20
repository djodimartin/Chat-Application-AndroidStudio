package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText edtUserName, edtEmail, edtPassword;
    private Button btnSubmit;
    private TextView txtLoginInfo;
    private FirebaseAuth mAuth;

    private boolean isSigningUp = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtUserName = findViewById(R.id.edtUsername);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);

        btnSubmit = findViewById(R.id.btnSubmit);
        txtLoginInfo = findViewById(R.id.txtLoginInfo);
        // firebase
        if (FirebaseAuth.getInstance().getCurrentUser()!=null) {
            startActivity(new Intent(MainActivity.this, FriendsActivity.class));
            finish();
        }
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edtEmail.getText().toString().isEmpty() || edtPassword.getText().toString().isEmpty()) {
                    if (isSigningUp && edtUserName.getText().toString().isEmpty()) {
                        Toast.makeText(MainActivity.this, "Gagal Input Data", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if (isSigningUp) {
                    handelSignUp();
                }else {
                    handlelogin();
                }
            }
        });

        txtLoginInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSigningUp) {
                    isSigningUp = false;
                    edtUserName.setVisibility(View.GONE);
                    btnSubmit.setText("Login");
                    txtLoginInfo.setText("Belum Punya Akun? Sign Up");
                }else {
                    isSigningUp = true;
                    edtUserName.setVisibility(View.VISIBLE);
                    btnSubmit.setText("Sign Up");
                    txtLoginInfo.setText("Sudah Punya Akun? Login");
                }
            }
        });
    }

    private void handelSignUp() {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(edtEmail.getText().toString(), edtPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseDatabase.getInstance().getReference("user/" + FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(new User(edtUserName.getText().toString(), edtEmail.getText().toString(), ""));
                            startActivity(new Intent(MainActivity.this, FriendsActivity.class));
                            Toast.makeText(MainActivity.this, "Daftar Akun Sukses!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void handlelogin() {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(edtEmail.getText().toString(), edtPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            startActivity(new Intent(MainActivity.this, FriendsActivity.class));
                            Toast.makeText(MainActivity.this, "Login Telah Berhasil!", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}