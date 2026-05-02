package com.podconnect.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button button = new Button(this);
        button.setText("Send Test Message");

        button.setOnClickListener(v -> {
            Intent intent = new Intent(this, AutomationService.class);
            intent.putExtra("phone", "1234567890");
            intent.putExtra("message", "Hello from PodConnect");
            startService(intent);
        });

        setContentView(button);
    }
}
