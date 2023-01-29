package com.example.client;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ContattiActivity extends AppCompatActivity {

    private Gestore gestore = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contatti);
        gestore = new Gestore(this);

        gestore.startBgTask("populate");
    }
}