package com.podconnect.tests;

import android.net.Uri;
import android.os.SystemClock;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.*;

public final class WhatsAppDriver {

    private static final int TIMEOUT = 8000;

    private WhatsAppDriver() {}

    public static void send(String phone, String message) throws Exception {

        UiDevice device =
                UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        String encoded = Uri.encode(message);
        String url = "https://wa.me/" + phone + "?text=" + encoded;

        // Launch intent safely
        device.executeShellCommand(
                "am start -a android.intent.action.VIEW -d \"" + url + "\""
        );

        UiObject2 sendButton = waitForSendButton(device);

        if (sendButton != null) {
            sendButton.click();
            return;
        }

        // fallback: input + enter
        UiObject2 input = device.wait(
                Until.findObject(By.clazz("android.widget.EditText")),
                TIMEOUT
        );

        if (input != null) {
            input.setText(message);
            device.pressEnter();
            return;
        }

        throw new IllegalStateException("Unable to find send UI");
    }

    private static UiObject2 waitForSendButton(UiDevice device) {

        return device.wait(
                Until.findObject(
                        By.descContains("Send")
                ),
                TIMEOUT
        );
    }
}
