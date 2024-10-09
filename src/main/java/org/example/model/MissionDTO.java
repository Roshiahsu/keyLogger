package org.example.model;

import lombok.Data;
import org.example.KeyEventEnum;

/**
 * 包裝動作
 */
@Data
public class MissionDTO {
    private KeyEventEnum keyEvent;
    private long duration;
    private int keyCode;
    private boolean pressed;


    public MissionDTO(KeyEventEnum keyEvent, long duration) {
        this.keyEvent = keyEvent;
        this.duration = duration;
    }

    public MissionDTO(int keyCode, long duration) {
        this.keyCode = keyCode;
        this.duration = duration;
    }

    public MissionDTO(int keyCode, boolean pressed, long duration) {
        this.keyCode = keyCode;
        this.pressed = pressed;
        this.duration = duration;
    }

    public void press() {
        this.keyEvent.press(duration);
    }
}
