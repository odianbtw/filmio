package com.paradigma.filmio.core.model;

public class HtmlMessageContent implements MessageContent {

    private String receiverAddress;
    private String subject;
    private String htmlBody;


    @Override
    public String getReceiver() {
        return "";
    }

    @Override
    public String getSubject() {
        return "";
    }

    @Override
    public String getMessage() {
        return "";
    }
}
