package com.example.client;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.Scanner;

public class Gestore {

    private AppCompatActivity activity;

    private LinkedList<Contatto> contatti = new LinkedList<>();

    private BgTask runningTask = null;

    private Client client = null;

    public Gestore(AppCompatActivity aAppCompatActivity){
        this.activity = aAppCompatActivity;
    }

    public AppCompatActivity getActivity() {
        return activity;
    }

    public void createMsgActivity(){
        Button send = this.activity.findViewById(R.id.send);
        EditText msg = this.activity.findViewById(R.id.msg);
        Bundle extras = this.activity.getIntent().getExtras();
        this.startBgTask("client",extras.getString("username"));
        send.setOnClickListener(view -> {
            if (!msg.getText().toString().matches("")) {
                client.sendMsg(msg.getText().toString());
                msg.setText("");
            }
        });
    }

    public void newUser(String name, String lastName) {
        String ip;
        String uName = name;
        String uLastName = lastName;
        try (Scanner s = new java.util.Scanner(new URL("https://api.ipify.org").openStream(), "UTF-8").useDelimiter("\\A")) {
            ip = s.next();
        } catch (Exception e) {
            return;
        }
        User u = new User(uName, uLastName, ip);
        try {
            URL url = new URL("http://129.152.16.174:8888/person");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            String jsonInputString = u.getJson();
            try(OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }
        }catch (Exception e){}
    }

    public void startBgTask(String... params){
        if(runningTask != null){
            runningTask = null;
        }
        runningTask = new BgTask(this,params);
    }

    public String getContatti() {
        String data;
        try {
            String txt = "";
            String url_str = "http://129.152.16.174:8888/person";
            URL url = new URL(url_str);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                txt += line;
            }
            br.close();
            data = txt;
        }catch(Exception e){
            return e.getMessage();
        }
        return data;
    }

    public void populate(String data) {
        LinearLayout  layoutContatti = this.activity.findViewById(R.id.area_contatti);
        try {
            JSONArray js = new JSONArray(data);
            for (int i = 0; i<js.length(); i++){
                JSONObject value = js.getJSONObject(i);
                Contatto c = new Contatto(value.getString("firstname")+" "+value.getString("lastname"), (ContattiActivity) this.activity);
                contatti.add(c);
                layoutContatti.addView(c.getView());
            }
        }catch (Exception e){}
    }

    public void add(String aTesto, String card){
        LinearLayout layout = ((MsgActivity)this.activity).findViewById(R.id.messageArea);
        this.activity.runOnUiThread(() -> {
            Message m = new Message(aTesto,card, (MsgActivity) Gestore.this.activity);
            layout.addView(m.getView());
        });
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
