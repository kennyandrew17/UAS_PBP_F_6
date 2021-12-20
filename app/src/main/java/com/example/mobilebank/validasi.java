package com.example.mobilebank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class validasi extends AppCompatActivity {
    Button lanjutvalid,btnback,btnlogout;
    TextView namavalid,rekvalid, emailvalid, nominalvalid, ketvalid,pin;
    EditText passvalid;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.validasi);
        btnback = (Button) findViewById(R.id.btnback);
        btnlogout = (Button) findViewById(R.id.btnlogout);
        lanjutvalid = (Button) findViewById(R.id.btnlanjutvalid);
        namavalid = (TextView) findViewById(R.id.tvnamavalid);
        emailvalid = (TextView) findViewById(R.id.tvemailvalid);
        nominalvalid = (TextView) findViewById(R.id.tvnominalvalid);
        ketvalid = (TextView) findViewById(R.id.tvketvalid);
        passvalid = (EditText) findViewById(R.id.etpassvalid);
        pin = (TextView) findViewById(R.id.pinvalid);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        UserId = fAuth.getCurrentUser().getUid();

        String namatrf = getIntent().getExtras().getString("namatrf");
        String norektrf = getIntent().getExtras().getString("norektrf");
        String emailtrf = getIntent().getExtras().getString("emailtrf");
        String nominaltrf = getIntent().getExtras().getString("nominaltrf");
        String kettrf = getIntent().getExtras().getString("kettrf");

        namavalid.setText(namatrf);
        rekvalid.setText(norektrf);
        emailvalid.setText(emailtrf);
        nominalvalid.setText(nominaltrf);
        ketvalid.setText(kettrf);

        DocumentReference documentReference = fStore.collection("users").document(UserId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                pin.setText(documentSnapshot.getString("pin"));
            }
        });

        lanjutvalid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = passvalid.getText().toString();
                if(password.equals(pin.getText().toString())){
                    Intent i = new Intent(validasi.this, laporan.class);
                    Toast.makeText(getApplicationContext(), "Transaksi Sukses", Toast.LENGTH_SHORT).show();
                    i.putExtra("namatrf", namavalid.getText().toString());
                    i.putExtra("norektrf", rekvalid.getText().toString());
                    i.putExtra("emailtrf", emailvalid.getText().toString());
                    i.putExtra("nominaltrf", nominalvalid.getText().toString());
                    i.putExtra("kettrf", ketvalid.getText().toString());
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(), "Transaksi Gagal", Toast.LENGTH_SHORT).show();
                }
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

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}