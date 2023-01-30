package com.example.client;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RegistratiActivity extends AppCompatActivity {

    private EditText name = null, lastName = null;

    private Gestore gestore = null;

    private Button submit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrati);
        name = findViewById(R.id.name);
        lastName = findViewById(R.id.lastname);
        submit = findViewById(R.id.submit);
        gestore = new Gestore(this);

        submit.setOnClickListener(view -> gestore.startBgTask("newUser",name.getText().toString(), lastName.getText().toString()));
    }
}