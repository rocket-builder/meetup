package com.dev.meetup.models;

public class ServerResponse {

    private String message;
    private  boolean isError;
    private int inserted_id;

    public ServerResponse() {}

    public ServerResponse(String message, boolean isError) {
        this.message = message;
        this.isError = isError;
    }

    public ServerResponse(String message, boolean isError, int inserted_id) {
        this.message = message;
        this.isError = isError;
        this.inserted_id = inserted_id;
    }

    public String getMessage() {return message;}
    public void setMessage(String message) {this.message = message;}

    public boolean isError() {return isError;}
    public void setError(boolean error) {isError = error;}

    public int getInserted_id() {return inserted_id;}
    public void setInserted_id(int inserted_id) {this.inserted_id = inserted_id;}
}
