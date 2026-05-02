package com.podconnect.app;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

public class AutomationService extends Service {

    private final Handler handler = new Handler();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String phone = intent.getStringExtra("phone");
        String message = intent.getStringExtra("message");
        int delay = intent.getIntExtra("delay", 5);

        handler.postDelayed(() -> {
            RootExecutor.sendWhatsApp(phone, message);
        }, delay * 1000L);

        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
