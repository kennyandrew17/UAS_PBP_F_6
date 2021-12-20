package com.example.mobilebank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
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

public class isisaldo extends AppCompatActivity {
    EditText emailisisaldo, pinisisaldo, nominalisisaldo;
    Button btnback, btnlogout, btnconfirmisisaldo;
    TextView pin,email1;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.isisaldo);

        btnback = (Button) findViewById(R.id.btnback);
        btnlogout = (Button) findViewById(R.id.btnlogout);
        btnconfirmisisaldo = (Button) findViewById(R.id.btnconfirmisisaldo);
        emailisisaldo = (EditText) findViewById(R.id.emailisisaldo);
        pinisisaldo = (EditText) findViewById(R.id.pinisisaldo);
        nominalisisaldo = (EditText) findViewById(R.id.nominalisisaldo);
        pin = (TextView) findViewById(R.id.isipinisisaldo);
        email1 = (TextView) findViewById(R.id.isiemailisisaldo);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        UserId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(UserId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                pin.setText(documentSnapshot.getString("pin"));
                email1.setText(documentSnapshot.getString("email"));
            }
        });
        btnconfirmisisaldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = pinisisaldo.getText().toString();
                String email = emailisisaldo.getText().toString();
                if(password.equals(pin.getText().toString()) && email.equals(email1.getText().toString())){
                    Intent i = new Intent(isisaldo.this, home.class);
                    Toast.makeText(getApplicationContext(), "Isi Saldo Sukses", Toast.LENGTH_SHORT).show();
                    i.putExtra("isisaldoinfo", nominalisisaldo.getText().toString());
                }else{
                    Toast.makeText(getApplicationContext(), "Isi Saldo Gagal", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
