package com.podconnect.tests;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class WhatsAppSendTest {

    @Test
    public void sendMessage() throws Exception {
        WhatsAppDriver.send(
                "971557074568",
                "Hello from PodConnect Engine"
        );
    }
}
