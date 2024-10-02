package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.concurrent.*;

@AllArgsConstructor
@Getter
public enum KeyEventEnum {
    KEY_A(KeyEvent.VK_A),
    KEY_W(KeyEvent.VK_W),
    KEY_S(KeyEvent.VK_S),
    KEY_D(KeyEvent.VK_D),

    ;

    private final int keyCode;
    private static Robot robot;
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final ConcurrentMap<KeyEventEnum, ScheduledFuture<?>> keyTaskMap = new ConcurrentHashMap<>();

    // 初始化Robot
    static {
        try {
            robot = new Robot();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static KeyEventEnum getByKeyCode(int keyCode) {
        for (KeyEventEnum value : values()) {
            if (value.getKeyCode() == keyCode) {
                return value;
            }
        }
        return null;
    }

    public void press(long duration) {
        ScheduledFuture<?> future = keyTaskMap.remove(this);
        if (future != null && !future.isDone()) {
            future.cancel(false);
        }

        // 按下按键
        robot.keyPress(this.keyCode);

        // 安排任务在指定的持续时间后释放按键
        ScheduledFuture<?> newFuture = scheduler.schedule(() -> {
            robot.keyRelease(this.keyCode);
        }, duration, TimeUnit.MILLISECONDS);

        // 将任务存入任务映射
        keyTaskMap.put(this, newFuture);
    }
}
