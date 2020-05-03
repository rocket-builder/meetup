package com.dev.meetup.models;

public class WebSocketOutputMessage extends WebSocketMessage{
    private String time;

    public WebSocketOutputMessage(final String from, final String text, final String time) {
        setFrom(from);
        setText(text);
        this.time = time;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) { this.time = time; }
}
