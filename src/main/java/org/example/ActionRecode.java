package org.example;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import org.example.keyActionStrategy.*;
import org.example.model.MissionDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 錄製鍵盤活動
 */
public class ActionRecode extends JFrame implements NativeKeyListener {
    private JTextArea textArea;
    private List<MissionDTO> recordedKeys;
    private Mode currentMode;
    private KeyActionStrategy keyActionStrategy;
    public ActionRecode() {
        // 設置窗口
        setTitle("Key Logger Demo");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 創建文本區域來顯示按鍵記錄
        textArea = new JTextArea();
        textArea.setEditable(false);
        add(new JScrollPane(textArea)); // 加入滾動條

        // 添加模式切換按鈕
        JButton switchModeButton = new JButton("切換模式");
        switchModeButton.addActionListener(e -> switchMode());
        add(switchModeButton, BorderLayout.SOUTH);

        setVisible(true);
        currentMode = Mode.RECORDING; // 初始模式
        recordedKeys = new ArrayList<>();

        setStrategyForCurrentMode();
        disableJNativeHookLogging();

        // 初始化全局鍵盤監聽
        initGlobalKeyListener();
    }

    private void setStrategyForCurrentMode() {
        switch (currentMode) {
            case RECORDING:
                keyActionStrategy = new RecordingStrategy(recordedKeys);
                break;
            case PLAYBACK:
                keyActionStrategy = new PlaybackStrategy(recordedKeys);
                break;

        }
    }

    private void switchMode() {
        // 簡單的模式切換示例
        if (currentMode == Mode.RECORDING) {
            currentMode = Mode.PLAYBACK;
        } else {
            currentMode = Mode.RECORDING;
        }
        setStrategyForCurrentMode();
        textArea.append("切換到模式: " + currentMode + "\n");
    }

    private void disableJNativeHookLogging() {
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);
    }

    // 初始化全局鍵盤監聽
    private void initGlobalKeyListener() {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            System.exit(-1);
        }

        GlobalScreen.addNativeKeyListener(this);
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        keyActionStrategy.onKeyPressed(e, textArea);
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        keyActionStrategy.onKeyReleased(e, textArea);
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
    }

    public static void main(String[] args) {
        new ActionRecode();
    }
}
