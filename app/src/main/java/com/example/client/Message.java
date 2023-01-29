package com.example.client;

import android.view.View;
import android.widget.TextView;

public class Message {

    private String testo = null;

    private View v = null;

    public Message(String aTesto, String card, MsgActivity m){
        this.testo = aTesto;

        if(card.equals("to")) {
            this.v = m.getLayoutInflater().inflate(R.layout.card_to, null);
        }else if (card.equals("from")){
            this.v = m.getLayoutInflater().inflate(R.layout.card_from, null);
        }
        TextView msg = v.findViewById(R.id.message);
        TextView sndr = v.findViewById(R.id.sender);
        if(testo.contains("msgfrm")){
            String sender = testo.split(" ")[1].replaceAll(":","");
            String target = "msgfrm "+sender+": ";
            testo = testo.replace(target,"");
            sndr.setText(sender);
        }
        msg.setText(this.testo);
    }

    public View getView(){
        return v;
    }
}
