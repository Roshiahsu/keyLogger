package org.example.keyActionStrategy;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import org.example.model.MissionDTO;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RecordingStrategy implements KeyActionStrategy {
    private Map<Integer, Long> keyPressTimes = new HashMap<>();  // 存储每个按键按下的时间
    private List<MissionDTO> recordedKeys;

    private Map<Integer, Boolean> keyAlreadyPressed;  // 存储每个按键是否已被记录


    public RecordingStrategy(List<MissionDTO> recordedKeys) {
        this.recordedKeys = recordedKeys;
        this.keyAlreadyPressed = new HashMap<>();
    }
    @Override
    public void onKeyPressed(NativeKeyEvent e, JTextArea textArea) {
        int keyCode = mapKeyCode(e.getRawCode());
        if (!keyAlreadyPressed.getOrDefault(keyCode, false)) {
            keyPressTimes.put(keyCode, System.currentTimeMillis());
            keyAlreadyPressed.put(keyCode, true);
            recordedKeys.add(new MissionDTO(keyCode, true, System.currentTimeMillis()));
            String log = "录制 - 按下: " + NativeKeyEvent.getKeyText(keyCode) + " at " + (System.currentTimeMillis()) + " ms\n";
            textArea.append(log);
        }
    }

    @Override
    public void onKeyReleased(NativeKeyEvent e, JTextArea textArea) {
        int keyCode = mapKeyCode(e.getRawCode());
        Long pressTime = keyPressTimes.get(keyCode);
        if (pressTime != null) {
            long releaseTime = System.currentTimeMillis();
            long duration = releaseTime - pressTime;
            recordedKeys.add(new MissionDTO(keyCode, false, releaseTime));
            String log = "录制 - 释放: " + NativeKeyEvent.getKeyText(keyCode) + " after " + duration + " ms\n";
            textArea.append(log);
            keyPressTimes.remove(keyCode);
            keyAlreadyPressed.put(keyCode, false);
        }
    }

    private int mapKeyCode(int keyCode) {
        switch (keyCode) {
            case 162:
            case 163:
                return KeyEvent.VK_CONTROL;
            // 添加更多键的映射
            default:
                return keyCode; // 如果没有映射，则直接返回原码
        }
    }
}