package org.example.model;

import lombok.Data;
import org.example.KeyEventEnum;

/**
 * 包裝動作
 */
@Data
public class MissionDTO {
    private KeyEventEnum keyEvent;
    private long ms;

    public MissionDTO(KeyEventEnum keyEvent, long ms) {
        this.keyEvent = keyEvent;
        this.ms = ms;
    }

    public void press() {
        this.keyEvent.press(ms);
    }
}
