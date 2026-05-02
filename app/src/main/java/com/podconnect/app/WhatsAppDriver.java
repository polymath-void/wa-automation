package com.podconnect.app;

import android.os.SystemClock;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiSelector;

public class WhatsAppDriver {

    public static void send(String phone, String message) throws Exception {

        UiDevice device =
                UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        String url = "https://wa.me/" + phone + "?text=" + message;

        device.executeShellCommand(
                "am start -a android.intent.action.VIEW -d \"" + url + "\""
        );

        SystemClock.sleep(4000);

        UiObject sendBtn = device.findObject(
                new UiSelector().descriptionContains("Send")
        );

        if (sendBtn.exists()) {
            sendBtn.click();
        } else {
            UiObject input = device.findObject(
                    new UiSelector().className("android.widget.EditText")
            );

            if (input.exists()) {
                input.setText(message);
                device.pressEnter();
            }
        }
    }
}
