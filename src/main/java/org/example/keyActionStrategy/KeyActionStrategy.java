package org.example.keyActionStrategy;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import org.example.ActionRecode;

import javax.swing.*;
import java.util.Map;

public interface KeyActionStrategy {
    void onKeyPressed(NativeKeyEvent e, JTextArea textArea);
    void onKeyReleased(NativeKeyEvent e, JTextArea textArea);
}