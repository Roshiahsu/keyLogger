package org.example;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

/**
 * 錄製鍵盤活動
 */
public class ActionRecode extends JFrame implements KeyListener {

    private JTextArea textArea;
    private Map<Integer, Long> keyPressTimes;  // 存储每个按键按下的时间
    private Map<Integer, Boolean> keyAlreadyPressed;  // 存储每个按键是否已被记录

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

        // 添加键盘监听器
        addKeyListener(this);
        textArea.addKeyListener(this);

        setVisible(true);
        keyPressTimes = new HashMap<>();
        keyAlreadyPressed = new HashMap<>();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // 如果按键还没有被记录，记录按下时间
        if (!keyAlreadyPressed.getOrDefault(keyCode, false)) {
            keyPressTimes.put(keyCode, System.currentTimeMillis());  // 记录按下时间
            String log = "Key Pressed: " + KeyEvent.getKeyText(keyCode) + " at " + keyPressTimes.get(keyCode) + " ms\n";
            textArea.append(log);
            keyAlreadyPressed.put(keyCode, true);  // 标记该按键已被按下
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
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

    public static void main(String[] args) {
        new ActionRecode();
    }
}
