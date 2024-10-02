package org.example;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import org.example.model.MissionDTO;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 錄製鍵盤活動
 */
public class ActionRecode extends JFrame implements NativeKeyListener {

    private JTextArea textArea;
    private Map<Integer, Long> keyPressTimes;  // 存储每个按键按下的时间
    private Map<Integer, Boolean> keyAlreadyPressed;  // 存储每个按键是否已被记录
    private List<MissionDTO> keyRecode;  // 紀錄按鍵順序

   private final RobotAct robotAct = RobotAct.getInstance();

    public ActionRecode() {
        // 设置窗口
        setTitle("Key Logger Demo");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建文本区域来显示按键记录
        textArea = new JTextArea();
        textArea.setEditable(false);
        add(textArea);


        setVisible(true);
        keyPressTimes = new HashMap<>();
        keyAlreadyPressed = new HashMap<>();
        keyRecode = new ArrayList<>();
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent  e) {
        //NativeKeyEvent 的rawCode 對應 keyListener keyCode
        int keyCode = e.getRawCode();

        // 如果按键还没有被记录，记录按下时间
        if (!keyAlreadyPressed.getOrDefault(keyCode, false)) {
            keyPressTimes.put(keyCode, System.currentTimeMillis());  // 记录按下时间
            String log = "Key Pressed: " + KeyEvent.getKeyText(keyCode) + " at " + keyPressTimes.get(keyCode) + " ms\n";
            textArea.append(log);
            keyAlreadyPressed.put(keyCode, true);  // 标记该按键已被按下
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        //NativeKeyEvent 的rawCode 對應 keyListener keyCode
        int keyCode = e.getRawCode();
        Long pressTime = keyPressTimes.get(keyCode);

        // 只有当按键被按下时，才能计算按住时间
        if (pressTime != null) {
            long releaseTime = System.currentTimeMillis();  // 记录释放时间
            long duration = releaseTime - pressTime;  // 计算按住时间
            String log = "Key Released: " + KeyEvent.getKeyText(keyCode) + " after " + duration + " ms\n";
            textArea.append(log);

            // 按键已被释放，移除相关记录
            keyPressTimes.remove(keyCode);
            keyAlreadyPressed.put(keyCode, false);  // 重置按键状态
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
    }

    public static void main(String[] args) {
        // 添加键盘监听器
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        // 注册全局键盘监听器
        try {
            GlobalScreen.registerNativeHook();

            // 添加键盘事件监听器
            GlobalScreen.addNativeKeyListener(new ActionRecode());
        } catch (NativeHookException e) {
            throw new RuntimeException(e);
        }
    }
}
