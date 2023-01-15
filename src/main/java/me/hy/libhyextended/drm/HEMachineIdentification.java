package me.hy.libhyextended.drm;

import me.hy.libhyextended.UnsupportedOperatingSystemException;
import me.hy.libhycore.CoreSHA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class HEMachineIdentification {

    public static enum OSTypes {
        WINDOWS, MACOS, LINUX, OTHER
    }

    public static OSTypes getOperatingSystem() {
        String os = System.getProperty("os.name");
        if (os.contains("Windows")) {
            return OSTypes.WINDOWS;
        } else if (os.contains("Mac")) {
            return OSTypes.MACOS;
        } else {
            return OSTypes.OTHER;
        }
    }

    public static String getMachineUnique() throws IOException, InterruptedException, UnsupportedOperatingSystemException {

        String systemUniqueness = "";
        if (getOperatingSystem() == OSTypes.MACOS) {

            // MacOS
            // Get macOS serial number

            Process proc = Runtime.getRuntime().exec("system_profiler SPHardwareDataType");
            BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Serial Number")) {
                    systemUniqueness = line.split(":")[1].trim();
                    break;
                }
            }

            proc.waitFor();
        }else if (getOperatingSystem() == OSTypes.WINDOWS) {

            // Windows
            // Get MAC address

            InetAddress ip;
            try {
                ip = InetAddress.getLocalHost();
                NetworkInterface network = NetworkInterface.getByInetAddress(ip);
                byte[] mac = network.getHardwareAddress();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mac.length; i++) {
                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                }
                systemUniqueness = sb.toString();
            } catch (UnknownHostException | SocketException e) {
                e.printStackTrace();
            }
        }else {
            throw new UnsupportedOperatingSystemException(getOperatingSystem().toString());
        }

        return CoreSHA.hash256(systemUniqueness, systemUniqueness);
    }
}
