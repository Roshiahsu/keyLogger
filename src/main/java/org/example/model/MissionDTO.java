package org.example.model;

/**
 * 包裝動作
 */
public class MissionDTO {
    private int keyEvent;
    private long ms;

    public MissionDTO(int keyEvent, long ms) {
        this.keyEvent = keyEvent;
        this.ms = ms;
    }

    public int getKeyEvent() {
        return keyEvent;
    }

    public void setKeyEvent(int keyEvent) {
        this.keyEvent = keyEvent;
    }

    public long getMs() {
        return ms;
    }

    public void setMs(int ms) {
        this.ms = ms;
    }
}
