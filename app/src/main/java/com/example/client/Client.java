package com.example.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Client{
    private String message = null;

    private Exception e = null;

    private final String SERVERNAME = "129.152.16.174";
    private final int port = 9999;
    private String userName;

    private Gestore gestore;

    private PrintStream out = null;
    private Socket s = null;
    private BufferedReader br = null;

    public Client(String aUname, Gestore gestore) {
        this.userName = aUname;
        this.gestore = gestore;


        try {
            this.s = new Socket(this.SERVERNAME, this.port);
            this.br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            this.out = new PrintStream(s.getOutputStream());

            //sender
            new Thread(() -> {
                try {
                    while(true) {
                        if (message != null) {
                            Client.this.out.println(message);
                            String show = "msgfrm " + userName + ": " + message;
                            Client.this.gestore.add(show, "to");
                            message = null;
                        }
                    }
                }catch(Exception e){}
            }).start();

            //receiver
            new Thread(() -> {
                try {
                    while (true) {
                        if (br != null) {
                            String msg = br.readLine();
                            Client.this.gestore.add(msg, "from");
                        }
                    }
                } catch (Exception e) {}
            }).start();
        }catch(Exception e){}
    }

    public void sendMsg(String s){
        this.message = s;
    }

    @Override
    public String toString() {
        return "Client{" +
                "message='" + message + '\'' +
                ", e=" + e +
                ", SERVERNAME='" + SERVERNAME + '\'' +
                ", port=" + port +
                ", userName='" + userName + '\'' +
                ", gestore=" + gestore +
                ", out=" + out +
                ", s=" + s +
                ", br=" + br +
                '}';
    }
}
