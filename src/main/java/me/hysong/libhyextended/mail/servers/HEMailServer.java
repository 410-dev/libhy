package me.hysong.libhyextended.mail.servers;

import lombok.Getter;

@Getter
public abstract class HEMailServer {
    protected String host;
    protected int port;
    protected String senderMailAddress;
    protected String senderName;
    protected String password;

    protected boolean useTLS = true;
    protected boolean useSMTPAuth = true;
}
