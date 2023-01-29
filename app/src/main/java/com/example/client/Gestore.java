package com.example.client;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
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
import java.util.Scanner;

public class Gestore {

    private AppCompatActivity activity = null;

    private BgTask runningTask = null;

    public Gestore(){}

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
        Client client = new Client(extras.getString("username"),this);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                client.sendMsg(msg.getText().toString());
                msg.setText("");
            }
        });
    }

    public void newUser(String name, String lastName) {
        String ip = null;
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
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }
        }catch (Exception e){}
    }

    public void startBgTask(String params){
        if(runningTask != null){
            runningTask.cancel(true);
        }
        runningTask = new BgTask();
        runningTask.execute(params);
    }

    public String getContatti() {
        String data = "";
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

    private void populate(String data) {
        LinearLayout  layoutContatti = this.activity.findViewById(R.id.area_contatti);
        try {
            JSONArray js = new JSONArray(data);
            for (int i = 0; i<js.length(); i++){
                JSONObject value = js.getJSONObject(i);
                Contatto c = new Contatto(value.getString("firstname")+" "+value.getString("lastname"), (ContattiActivity) this.activity);
                layoutContatti.addView(c.getView());
            }
        }catch (Exception e){}
    }

    public void add(String aTesto, String card){
        LinearLayout layout = this.activity.findViewById(R.id.messageArea);
        this.activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Message m = new Message(aTesto,card, (MsgActivity) Gestore.this.activity);
                layout.addView(m.getView());
            }
        });
    }

    private class BgTask extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDialog = null;

        private String action = null;

        private String data = null;

        @Override
        protected String doInBackground(String... params) {
            //Start loading your data
            switch (params[0]){
                case "populate":
                    this.action = params[0];
                    this.data = getContatti();
                    break;
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            //Update your UI with data you loaded/start your activity with loaded data
            switch (action){
                case "populate":
                    populate(result);
                    break;
                default:
                    break;
            }
            progressDialog.dismiss();
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();            // display a progress dialog for good user experiance
            progressDialog = new ProgressDialog(Gestore.this.activity);
            progressDialog.setMessage("Fetching data...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}
