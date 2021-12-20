package com.example.mobilebank;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {
    EditText namaregis, emailregis, pinregis, telpregis;
    Button btnregisconfirm,btnback;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        btnback = (Button) findViewById(R.id.btnback1);
        namaregis = findViewById(R.id.regisnama);
        emailregis = findViewById(R.id.regisemail);
        pinregis = findViewById(R.id.regispin);
        telpregis = findViewById(R.id.registelp);
        btnregisconfirm = (Button) findViewById(R.id.btnregisconfirm);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        btnregisconfirm.setOnClickListener((v) ->{
            String email = emailregis.getText().toString().trim();
            String password = pinregis.getText().toString().trim();
            String fullname = namaregis.getText().toString();
            String phone = telpregis.getText().toString();

            if(TextUtils.isEmpty(email)){
                emailregis.setError("Email tidak boleh kosong");
                return;
            }
            if(TextUtils.isEmpty(password)){
                pinregis.setError("PIN tidak boleh kosong");
                return;
            }
            if(password.length() < 6){
                pinregis.setError("PIN harus 6 digit");
                return;
            }
            if(password.length() > 6) {
                pinregis.setError("PIN harus 6 digit");
                return;
            }

            fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener((task -> {
                if(task.isSuccessful()){
                    Toast.makeText(register.this, "Berhasil membuat akun", Toast.LENGTH_SHORT).show();
                    userId = fAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fStore.collection("users").document(userId);
                    Map<String,Object> user = new HashMap<>();
                    user.put("nama",fullname);
                    user.put("email",email);
                    user.put("NoTelp",phone);
                    user.put("pin",password);
                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                        }
                    });
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }else{
                    Toast.makeText(register.this, "Gagal membuat akun", Toast.LENGTH_SHORT).show();
                }
            }));
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
