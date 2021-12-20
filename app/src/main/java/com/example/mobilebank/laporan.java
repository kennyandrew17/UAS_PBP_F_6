package com.example.mobilebank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class laporan extends AppCompatActivity {
    TextView norektujuanlapor, namatujuanlapor, emailtujuanlapor, nominallapor, totallapor, biayadmin;
    Button btnback, btnlogout;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laporantrans);

        btnback = (Button) findViewById(R.id.btnback);
        btnlogout = (Button) findViewById(R.id.btnlogout);
        biayadmin = (TextView) findViewById(R.id.tvbiayaadmin);
        namatujuanlapor = (TextView) findViewById(R.id.tvnamatujuanlapor);
        emailtujuanlapor = (TextView) findViewById(R.id.tvemaillapor);
        nominallapor = (TextView) findViewById(R.id.tvnominallapor);
        totallapor = (TextView) findViewById(R.id.tvtotallapor);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        UserId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(UserId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                namatujuanlapor.setText(documentSnapshot.getString("nama"));
                emailtujuanlapor.setText(documentSnapshot.getString("email"));
            }
        });

        String adm = biayadmin.getText().toString();
        String namatrf = getIntent().getExtras().getString("namatrf");
        String norektrf = getIntent().getExtras().getString("norektrf");
        String emailtrf = getIntent().getExtras().getString("emailtrf");
        String nominaltrf = getIntent().getExtras().getString("nominaltrf");

        //String total = totallapor.getTransitionName().toString();

        namatujuanlapor.setText(namatrf);
        norektujuanlapor.setText(norektrf);
        emailtujuanlapor.setText(emailtrf);
        nominallapor.setText(nominaltrf);

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
