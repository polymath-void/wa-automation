package com.podconnect.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EditText number = new EditText(this);
        number.setHint("Phone number (with country code)");

        EditText message = new EditText(this);
        message.setHint("Message");

        EditText delay = new EditText(this);
        delay.setHint("Delay seconds");

        Button send = new Button(this);
        send.setText("Schedule");

        send.setOnClickListener(v -> {
            Intent i = new Intent(this, AutomationService.class);
            i.putExtra("phone", number.getText().toString());
            i.putExtra("message", message.getText().toString());
            i.putExtra("delay", Integer.parseInt(delay.getText().toString()));
            startService(i);
        });

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(number);
        layout.addView(message);
        layout.addView(delay);
        layout.addView(send);

        setContentView(layout);
    }
}
