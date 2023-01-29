package com.example.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Client extends Thread{
    private String message = null;

    private String SERVERNAME = "129.152.16.174";
    private int port = 9999;
    private String userName = "";

    private Gestore gestore = null;

    private PrintStream out = null;
    private Socket s = null;
    private BufferedReader br = null;
    private String prev = "";

    public Client(String aUname, Gestore gestore) {
        this.userName = aUname;
        this.gestore = gestore;

        try {
            this.s = new Socket(this.SERVERNAME, this.port);
            this.br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            this.out = new PrintStream(s.getOutputStream());

            start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            if (br != null) {
                                String msg = br.readLine();
                                Client.this.gestore.add(msg, "from");
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }).start();
        }catch(Exception e){}
    }

    public void sendMsg(String s){
        this.message = s;
    }

    @Override
    public void run() {
        try {
            while(true){
                if(message != null){
                    this.out.println(message);
                    String show = "msgfrm "+userName+": "+message;
                    Client.this.gestore.add(show, "to");
                    message = null;
                }
            }
        }catch(Exception e){}
    }
}
