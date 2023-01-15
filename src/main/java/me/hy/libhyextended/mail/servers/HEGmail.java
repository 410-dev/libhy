package me.hy.libhyextended.mail.servers;

public class HEGmail extends HEMailServer {
    public HEGmail(String senderAddress, String password, String senderName) {
        super();
        this.host              = "smtp.gmail.com";
        this.port              = 587;
        this.senderMailAddress = senderAddress;
        this.senderName        = senderName;
        this.password          = password;
    }
}
