package org.example.keyActionStrategy;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import org.example.KeyEventEnum;
import org.example.model.MissionDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class PlaybackStrategy implements KeyActionStrategy {
    private List<MissionDTO> recordedKeys;
    private static Robot robot;
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);
    private static final ConcurrentMap<Integer, ScheduledFuture<?>> keyTaskMap = new ConcurrentHashMap<>();

    // 初始化Robot
    static {
        try {
            robot = new Robot();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PlaybackStrategy(List<MissionDTO> recordedKeys) {
        this.recordedKeys = recordedKeys;
    }

    @Override
    public void onKeyPressed(NativeKeyEvent e, JTextArea textArea) {
        if (e.getKeyCode() == NativeKeyEvent.VC_SHIFT) {
            System.out.println("play recode");
            playback();
        }
    }

    @Override
    public void onKeyReleased(NativeKeyEvent e, JTextArea textArea) {
        // 在播放模式下可能不需要處理按鍵
    }

    public void playback() {
        if (recordedKeys.isEmpty()) {
            return;
        }

        new Thread(() -> {
            try {
                long playbackStartTime = System.currentTimeMillis();
                long baseTime = recordedKeys.get(0).getDuration();
                for (MissionDTO mission : recordedKeys) {
                    long delay = mission.getDuration() - baseTime;
                    long currentTime = System.currentTimeMillis() - playbackStartTime;
                    if (delay > currentTime) {
                        Thread.sleep(delay - currentTime);
                    }

                    if (mission.isPressed()) {
                        System.out.println(mission.getKeyCode());
                        robot.keyPress(mission.getKeyCode());
                    } else {
                        robot.keyRelease(mission.getKeyCode());
                    }
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }).start();
    }
}