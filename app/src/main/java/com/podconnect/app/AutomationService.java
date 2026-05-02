package com.podconnect.app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class AutomationService extends Service {

    private static final String TAG = "PodConnect";
    private JobQueue jobQueue;

    @Override
    public void onCreate() {
        super.onCreate();

        MessageSender sender = (phone, message) -> {
            // Stub sender (production-safe placeholder)
            Log.i(TAG, "SEND_STUB phone=" + phone + " message_len=" + message.length());
        };

        jobQueue = new JobQueue(sender);
        jobQueue.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {
            String phone = intent.getStringExtra("phone");
            String message = intent.getStringExtra("message");

            if (phone != null && !phone.isEmpty()
                    && message != null && !message.isEmpty()) {

                jobQueue.enqueue(phone, message);

                Log.i(TAG, "ENQUEUED phone=" + phone);
            }
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        if (jobQueue != null) {
            jobQueue.stop();
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
