package org.example;


import lombok.Data;
import org.example.model.MissionDTO;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class RobotAct {

    private static final RobotAct instance = new RobotAct();
    public static RobotAct getInstance() {
        return instance;
    }
    private Robot robot;
    private boolean isRunning;
    private List<MissionDTO> missions;
    private Map<String, List<MissionDTO>> missionMap = new HashMap<>();

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
        regular();
    }

    public void start() {
        missions = missionMap.get("regular");
        do {
            runMission();
        } while (true);
        //change missions
        // runMission();

    }

    public void runMission() {
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
    private void regular() {
        List<MissionDTO> list = new ArrayList<>();
        list.add(new MissionDTO(KeyEvent.VK_A, 650));
        list.add(new MissionDTO(KeyEvent.VK_W, 650));
        list.add(new MissionDTO(KeyEvent.VK_D, 650));
        list.add(new MissionDTO(KeyEvent.VK_S, 650));
        list.add(new MissionDTO(KeyEvent.VK_A, 300));
        list.add(new MissionDTO(KeyEvent.VK_W, 350));
        list.add(new MissionDTO(KeyEvent.VK_A, 300));
        list.add(new MissionDTO(KeyEvent.VK_W, 350));
        list.add(new MissionDTO(KeyEvent.VK_D, 600));
        list.add(new MissionDTO(KeyEvent.VK_S, 700));
        createMissionMap("regular", list);
    }

    public void createMissionMap(String key, List<MissionDTO> missions) {
        missionMap.put(key, missions);
    }
}
