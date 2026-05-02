package com.podconnect.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView view = new TextView(this);
        view.setText("PodConnect Automation Engine Running");
        view.setTextSize(18f);

        setContentView(view);
    }
}
