package com.example.mobilebank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    Button btnlogin,btnregister;
    EditText editnama, editpin;
    FirebaseAuth fAuth;

    private String key_nama = "NAMA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnregister = (Button) findViewById(R.id.btnregister);
        btnlogin = (Button) findViewById(R.id.btnlogin);
        editnama = (EditText) findViewById(R.id.editnama);
        editpin = (EditText) findViewById(R.id.editpin);

        fAuth = FirebaseAuth.getInstance();

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, register.class);
                startActivity(i);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editnama.getText().toString().trim();
                String password = editpin.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    editnama.setError("Email tidak boleh kosong");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    editpin.setError("Password tidak boleh kosong");
                    return;
                }

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(), home.class));
                        }else {
                            Toast.makeText(MainActivity.this, "Login Gagal" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}