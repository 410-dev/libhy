package me.hysong.libhyextended.mail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HEMailObject {
    private String senderName;
    private String recipientAddress;

    private HEMailBodyType bodyType;

    private String subject;
    private String body;

    public HEMailObject(String senderName, String recipientAddress, HEMailBodyType bodyType, String subject, String body) {
        this.senderName = senderName;
        this.recipientAddress = recipientAddress;
        this.bodyType = bodyType;
        this.subject = subject;
        this.body = body;
    }
}
