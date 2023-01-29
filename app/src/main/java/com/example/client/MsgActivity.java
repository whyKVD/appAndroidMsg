package com.example.client;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MsgActivity extends AppCompatActivity {

    private Gestore gestore = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);
        gestore = new Gestore(this);
        gestore.createMsgActivity();
    }

    /*private void add(String aTesto, String card){
        Message m = new Message(aTesto,card, this);
        layout.addView(m.getView());
    }


    private class ClientTcp extends Thread{

        private String message = null;

        private String serverName = null;
        private int port = 0;

        private String userName = "";

        private PrintStream out = null;
        private Socket s = null;
        private BufferedReader br = null;
        private String prev = "";

        public ClientTcp(String serverName, int port, String aUname) {
            this.serverName = serverName;
            this.port = port;
            this.userName = aUname;

            start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while(true){
                            if(br != null){
                            String msg = br.readLine();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    add(msg,"from");
                                }
                            });}
                        }
                    } catch (Exception e) {}
                }
            }).start();
        }

        public void sendMsg(String s){
            this.message = s;
        }

        @Override
        public void run() {
            try {
                this.s = new Socket(this.serverName, this.port);
                this.br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                this.out = new PrintStream(s.getOutputStream());
                while(true){
                    if(message != null){
                        if(message != prev) {
                            this.out.println(message);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String show = "msgfrm "+userName+": "+message;
                                    add(show, "to");
                                }
                            });
                            prev = message;
                        }
                    }
                }
            }catch(Exception e){}
        }
    }*/
}