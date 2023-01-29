package com.example.client;

import android.view.View;
import android.widget.TextView;

public class Contatto {

    private String nome = null;

    private View v = null;

    public Contatto(String aNome, ContattiActivity c){
        this.nome = aNome;

        this.v = c.getLayoutInflater().inflate(R.layout.card_contatti, null);

        TextView nome = v.findViewById(R.id.nome);

        nome.setText(this.nome);
    }

    public View getView(){
        return v;
    }
}
