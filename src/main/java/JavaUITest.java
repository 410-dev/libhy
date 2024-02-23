import me.hysong.libhyextended.environment.Subsystem;
import me.hysong.libhyextended.environment.SubsystemEnvironment;
import me.hysong.libhyextended.objects.DataObject;
import me.hysong.libhyextended.security.RSACryptography;

import java.io.File;
import java.util.*;

public class JavaUITest {
    public static void main(String[] args) {
        String[] rsa = RSACryptography.generateRSAKey();

        String encryptedWithPublicKey = RSACryptography.encrypt("hello", rsa[0]);
        String encryptedWithPrivateKey = RSACryptography.encrypt("hello", rsa[1]);

        String decryptedWithPrivateKey = RSACryptography.decrypt(encryptedWithPublicKey, rsa[1]);
        String decryptedWithPublicKey = RSACryptography.decrypt(encryptedWithPrivateKey, rsa[0]);

        System.out.println(decryptedWithPrivateKey);
        System.out.println(decryptedWithPublicKey);
    }
}

