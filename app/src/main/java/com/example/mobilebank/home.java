package com.example.mobilebank;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

public class home extends AppCompatActivity {
    Button btnlogout, btninfo, btntransfer, btnisisaldo;
    TextView saldohome;
    private String nama;
    private String key_nama = "NAMA";
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        btntransfer = (Button) findViewById(R.id.btntransfer);
        btninfo = (Button) findViewById(R.id.btninfo);
        btnlogout = (Button) findViewById(R.id.btnlogout);
        btnisisaldo = (Button) findViewById(R.id.btnisisaldo) ;
        saldohome = (TextView) findViewById(R.id.saldohome);
        //tvselamat = (TextView) findViewById(R.id.tvselamat);

        Bundle extras = getIntent().getExtras();
        //nama = extras.getString(key_nama);

        //tvselamat.setText("Selamat Datang " + nama);

        btnisisaldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (home.this, isisaldo.class);
                startActivity(i);
            }
        });

        btntransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home.this, transfer.class);
                startActivity(i);
            }
        });

        btninfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home.this, informasi.class);
                startActivity(i);
            }
        });

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
    }
}