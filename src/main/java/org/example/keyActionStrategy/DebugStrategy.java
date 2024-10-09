package org.example.keyActionStrategy;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

import javax.swing.*;
import java.util.Map;

public class DebugStrategy implements KeyActionStrategy {
    @Override
    public void onKeyPressed(NativeKeyEvent e, JTextArea textArea) {
        int keyCode = e.getRawCode();
        String log = "Debug - Key Pressed: " + keyCode + "\n";
        textArea.append(log);
    }

    @Override
    public void onKeyReleased(NativeKeyEvent e, JTextArea textArea) {
        int keyCode = e.getRawCode();
        String log = "Debug - Key Released: " + keyCode + "\n";
        textArea.append(log);
    }
}
