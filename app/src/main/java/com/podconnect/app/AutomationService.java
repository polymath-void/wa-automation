package com.podconnect.app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AutomationService extends Service {

    private JobQueue queue;

    @Override
    public void onCreate() {
        super.onCreate();
        queue = new JobQueue(getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(() -> queue.processJobs()).start();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
