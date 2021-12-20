package com.example.mobilebank;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.auth.User;
import java.lang.reflect.Array;

public class informasi extends AppCompatActivity {
    TableLayout tabelinfo, tabelinfo2, tabelinfo3;
    Spinner spin1;
    String[] cek={"PROFIL", "SALDO", "EMAIL"};
    Button btnbackinfo, btnlogoutinfo;
    TextView namainfo,notelpinfo,emailinfo,saldoinfo;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.informasi);

        btnbackinfo = (Button) findViewById(R.id.btnback1);
        btnlogoutinfo = (Button) findViewById(R.id.btnlogout1);
        tabelinfo = (TableLayout) findViewById(R.id.tabelinfo);
        tabelinfo2 = (TableLayout) findViewById(R.id.tabelinfo2);
        tabelinfo3 = (TableLayout) findViewById(R.id.tabelinfo3);
        spin1 = (Spinner) findViewById(R.id.spin1);
        tabelinfo.setVisibility(View.GONE);
        tabelinfo2.setVisibility(View.GONE);
        tabelinfo3.setVisibility(View.GONE);
        namainfo = (TextView) findViewById(R.id.namainfo);
        notelpinfo = (TextView) findViewById(R.id.notelpinfo);
        emailinfo = (TextView) findViewById(R.id.emailinfo);
        saldoinfo = (TextView) findViewById(R.id.saldoinfo);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        UserId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(UserId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                namainfo.setText(documentSnapshot.getString("nama"));
                notelpinfo.setText(documentSnapshot.getString("NoTelp"));
                emailinfo.setText(documentSnapshot.getString("email"));
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cek);
        spin1.setAdapter(adapter);

        btnbackinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  i = new Intent(informasi.this, home.class);
                startActivity(i);
                finish();
            }
        });

        btnlogoutinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int saldo = spin1.getSelectedItemPosition();
                String infosaldo = String.valueOf(cek[saldo]);
                if(infosaldo.equals("PROFIL")){
                    tabelinfo.setVisibility(View.VISIBLE);
                    tabelinfo2.setVisibility(View.GONE);
                    tabelinfo3.setVisibility(View.GONE);
                }else if(infosaldo.equals("SALDO")){
                    tabelinfo.setVisibility(View.GONE);
                    tabelinfo2.setVisibility(View.VISIBLE);
                    tabelinfo3.setVisibility(View.GONE);
                }else{
                    tabelinfo.setVisibility(View.GONE);
                    tabelinfo2.setVisibility(View.GONE);
                    tabelinfo3.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
