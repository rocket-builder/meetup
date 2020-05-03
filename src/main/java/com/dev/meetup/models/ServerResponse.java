package com.dev.meetup.models;

public class ServerResponse {

    private String message;
    private  boolean isError;
    private Object content;

    public ServerResponse() {}

    public ServerResponse(String message, boolean isError) {
        this.message = message;
        this.isError = isError;
    }

    public ServerResponse(String message, boolean isError, Object content) {
        this.message = message;
        this.isError = isError;
        this.content = content;
    }

    public String getMessage() {return message;}
    public void setMessage(String message) {this.message = message;}

    public boolean isError() {return isError;}
    public void setError(boolean error) {isError = error;}

    public Object getContent() {return content;}
    public void setContent(Object[] content) {this.content = content;}

}
