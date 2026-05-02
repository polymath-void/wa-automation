package com.podconnect.app;

import java.io.DataOutputStream;

public class RootExecutor {

    public static void sendWhatsApp(String phone, String message) {
        try {

            String url = "https://wa.me/" + phone + "?text=" + message;

            // Step 1: open WhatsApp
            exec("am start -a android.intent.action.VIEW -d \"" + url + "\"");

            Thread.sleep(4000);

            // Step 2: press send (most WhatsApp versions auto-fill)
            exec("input keyevent 66");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void exec(String cmd) throws Exception {
        Process p = Runtime.getRuntime().exec("su");
        DataOutputStream os = new DataOutputStream(p.getOutputStream());

        os.writeBytes(cmd + "\n");
        os.writeBytes("exit\n");
        os.flush();

        p.waitFor();
    }
}
