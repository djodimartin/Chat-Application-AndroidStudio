package com.example.chatapp;

public class Message {

    private String sender;
    private String receiver;
    private String contet;

    public Message(){

    }


    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContet() {
        return contet;
    }

    public void setContet(String contet) {
        this.contet = contet;
    }

    public Message(String sender, String receiver, String contet) {
        this.sender = sender;
        this.receiver = receiver;
        this.contet = contet;
    }
}
