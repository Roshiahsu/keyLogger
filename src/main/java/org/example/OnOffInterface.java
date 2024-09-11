package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * UI
 */
public class OnOffInterface extends JFrame {
    private JButton onButton;
    private JButton offButton;
    private JLabel statusLabel;
    private boolean isRunning = false;
    private RobotAct robotAct = new RobotAct();

    public OnOffInterface() {
        setTitle("On and Off Interface");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 居中顯示

        // 初始化元件
        onButton = new JButton("On");
        offButton = new JButton("Off");
        statusLabel = new JLabel("狀態: Off", SwingConstants.CENTER);

        // 設定佈局
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(onButton);
        buttonPanel.add(offButton);

        add(buttonPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        // 添加按鈕事件監聽器
        onButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isRunning) {
                    isRunning = true;
                    robotAct.setRunning(true);
                    statusLabel.setText("狀態: On");
                    startKeyPress();
                }
            }
        });

        offButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isRunning) {
                    isRunning = false;
                    robotAct.setRunning(false);
                    statusLabel.setText("狀態: Off");
                }
            }
        });
    }

    // 模擬鍵盤按鍵的執行緒
    private void startKeyPress() {
        Thread keyPressThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning) {
                    robotAct.start();
                }
            }
        });
        keyPressThread.start();
    }
}
