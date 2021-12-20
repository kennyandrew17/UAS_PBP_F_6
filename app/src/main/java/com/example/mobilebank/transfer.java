package com.example.mobilebank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class transfer extends AppCompatActivity {
    Button lanjut, btnback, btnlogout;
    TextView norektuju, emailtuju, namatuju, nominaltuju, kettuju;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer);

        btnback = (Button) findViewById(R.id.btnback);
        btnlogout = (Button) findViewById(R.id.btnlogout);
        lanjut = (Button) findViewById(R.id.btnlanjuttransfer);
        emailtuju = (TextView) findViewById(R.id.tvemailpenerimatransfer);
        namatuju = (TextView) findViewById(R.id.tvnamatransfer);
        nominaltuju = (TextView) findViewById(R.id.tvnominaltransfer);
        kettuju = (TextView) findViewById(R.id.tvkettransfer);

        lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (transfer.this, validasi.class);
                i.putExtra("namatrf", namatuju.getText().toString());
                i.putExtra("emailtrf", emailtuju.getText().toString());
                i.putExtra("nominaltrf", nominaltuju.getText().toString());
                i.putExtra("kettrf", kettuju.getText().toString());

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
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}