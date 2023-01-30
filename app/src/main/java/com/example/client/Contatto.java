package com.example.client;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class Contatto implements Parcelable {

    private String nome;

    private View v = null;

    public Contatto(String aNome, ContattiActivity c){
        this.nome = aNome;

        this.v = c.getLayoutInflater().inflate(R.layout.card_contatti, null);

        TextView nome = v.findViewById(R.id.nome);

        nome.setText(this.nome);
    }

    protected Contatto(Parcel in) {
        nome = in.readString();
    }

    public static final Creator<Contatto> CREATOR = new Creator<Contatto>() {
        @Override
        public Contatto createFromParcel(Parcel in) {
            return new Contatto(in);
        }

        @Override
        public Contatto[] newArray(int size) {
            return new Contatto[size];
        }
    };

    public View getView(){
        return v;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(nome);
    }
}
