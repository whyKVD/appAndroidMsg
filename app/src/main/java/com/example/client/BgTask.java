package com.example.client;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BgTask {
    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper());
    private String action = null;
    private String data = null;

    public BgTask(Gestore g, String... params){
        executor.execute(() -> {
            switch (params[0]) {
                case "populate":
                    BgTask.this.action = params[0];
                    BgTask.this.data = g.getContatti();
                    break;
                case "client":
                    BgTask.this.action = params[0];
                    g.setClient(new Client(params[1], g));
                    break;
                case "newUser":
                    BgTask.this.action = params[0];
                    g.newUser(params[1], params[2]);
                    break;
                default:
                    break;
            }

            handler.post(() ->{
                switch (action){
                    case "populate":
                        g.populate(data);
                        break;
                    default:
                        break;
                }
            });
        });
    }
}
