package com.example.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText username;
    private Button connect, contatti, iscriviti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        connect = findViewById(R.id.connect);
        /*contatti = findViewById(R.id.contatti);
        iscriviti = findViewById(R.id.iscriviti);

        iscriviti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reg = new Intent(MainActivity.this, RegistratiActivity.class);
                startActivity(reg);
                finish();
            }
        });*/

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                startMsgActivity();
            }
        });

        /*contatti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cont = new Intent(MainActivity.this, ContattiActivity.class);
                startActivity(cont);
            }
        });*/
    }

    private void startMsgActivity() {
        Intent msg = new Intent(this, MsgActivity.class);
        msg.putExtra("username",username.getText().toString());
        startActivity(msg);
    }
}