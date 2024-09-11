package org.example;


import org.example.model.MissionDTO;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class RobotAct {

    private static final RobotAct instance = new RobotAct();
    public static RobotAct getInstance() {
        return instance;
    }
    private Robot robot;
    private boolean isRunning;

    /**
     * tmp 用lombok後可以刪除
     * @param running
     */
    public void setRunning(boolean running) {
        isRunning = running;
    }
    private List<MissionDTO> missions = new ArrayList<>();

    /**
     * 建構子
     */
    public RobotAct() {
        // 初始化 Robot
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
            System.exit(1);
        }
        tmpTestingSource();
    }

    public void start() {
    for (MissionDTO mission : missions) {
            if (!isRunning) {
                break;
            }
            keyAct(mission.getKeyEvent(), mission.getMs());
        }
    }

    private void keyAct(int key, long ms) {
        // 模擬按下 "A" 鍵
        robot.keyPress(key);

        try {
            // 每隔一秒按一次
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        robot.keyRelease(key);
    }
    public void addMission(MissionDTO mission) {
        missions.add(mission);
    }

    private void tmpTestingSource() {
        addMission(new MissionDTO(KeyEvent.VK_A, 650));
        addMission(new MissionDTO(KeyEvent.VK_W, 650));
        addMission(new MissionDTO(KeyEvent.VK_D, 650));
        addMission(new MissionDTO(KeyEvent.VK_S, 650));
        addMission(new MissionDTO(KeyEvent.VK_A, 300));
        addMission(new MissionDTO(KeyEvent.VK_W, 350));
        addMission(new MissionDTO(KeyEvent.VK_A, 300));
        addMission(new MissionDTO(KeyEvent.VK_W, 350));
        addMission(new MissionDTO(KeyEvent.VK_D, 600));
        addMission(new MissionDTO(KeyEvent.VK_S, 700));
    }

}
