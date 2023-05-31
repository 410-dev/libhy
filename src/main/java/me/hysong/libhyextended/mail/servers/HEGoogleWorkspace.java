package me.hysong.libhyextended.mail.servers;

public class HEGoogleWorkspace extends HEMailServer {
    public HEGoogleWorkspace(String senderAddress, String password, String senderName) {
        super();
        this.host              = "smtp-relay.gmail.com";
        this.port              = 587;
        this.senderMailAddress = senderAddress;
        this.senderName        = senderName;
        this.password          = password;
    }
}
