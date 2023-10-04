package me.hysong.libhyextended.environment;

import lombok.Getter;
import lombok.Setter;
import me.hysong.libhycore.CoreAES;
import me.hysong.libhycore.CoreBase64;
import me.hysong.libhyextended.utils.StringIO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Getter
@Setter
public class SubsystemEnvironment {

    public static final String defaultName = "default-hermes-subsystem";
    private final String hostOS;
    private String name = defaultName;
    private String root;
    private String stringIOEncryptionKey = "keydefault";
    private boolean useStringIOEncryption = false;
    private boolean writeStringIOBase64 = false;

    public SubsystemEnvironment(String name, String root) {
        this();
        this.root = root;
        this.name = name;
        configure();
    }

    public SubsystemEnvironment(String root) {
        this();
        this.root = root;
        configure();
    }

    public SubsystemEnvironment() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("mac")) {
            hostOS = "mac";
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            hostOS = "linux";
        } else if (os.contains("win")) {
            hostOS = "windows";
        } else {
            throw new UnknownHostOSException(os);
        }
        configure();
    }

    public SubsystemEnvironment configure() {
        // Check if macOS, Linux, Windows
        boolean generateSuccess;
        switch (hostOS) {
            case "mac" -> {
                if (root == null) this.root = "/Users/" + System.getProperty("user.name") + "/Library/Application Support/Hermes Subsystem/" + name;
                if (new File(this.root).isDirectory()) generateSuccess = true;
                else generateSuccess = new File(this.root).mkdirs();
            }
            case "linux" -> {
                if (root == null) this.root = "/home/" + System.getProperty("user.name") + "/.local/share/hermes-subsystem/" + name;
                if (new File(this.root).isDirectory()) generateSuccess = true;
                else generateSuccess = new File(this.root).mkdirs();
            }
            case "windows" -> {
                if (root == null) this.root = "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\Hermes Subsystem\\" + name;
                if (new File(this.root).isDirectory()) generateSuccess = true;
                else generateSuccess = new File(this.root).mkdirs();
            }
            default -> throw new UnknownHostOSException(hostOS);
        }

        if (!generateSuccess) {
            throw new RuntimeException("Failed to generate subsystem environment.");
        }

        return this;
    }

    public String realpath(String path) {
        return root + File.separator + path.replace("/", File.separator);
    }

    public String subsystemPath(String realpath) {
        return realpath.replace(root, "").replace(File.separator, "/");
    }

    public boolean mkdirs(String path) {
        return new File(realpath(path)).mkdirs();
    }

    public boolean mkdirs(String... paths) {
        boolean success = false;
        for (String path : paths) {
            if (path == null) continue;
            if (new File(realpath(path)).isDirectory()) continue;
            success = mkdirs(path);
            if (!success) {
                break;
            }
        }
        return success;
    }

    public ArrayList<String> listRecursive(String path, int limitDepth, int currentDepth) {
        ArrayList<String> list = list(path);
        ArrayList<String> build = new ArrayList<>();
        for (String p : list) {
            File f = new File(realpath(p));
            build.add(p);
            if (f.isDirectory()) {
                if (limitDepth == -1 || currentDepth < limitDepth) {
                    build.addAll(listRecursive(p, limitDepth, currentDepth + 1));
                }
            }
        }
        return build;
    }

    public ArrayList<String> listRecursive(String path, int limitDepth) {
        return listRecursive(path, limitDepth, 0);
    }

    public ArrayList<String> listRecursive(String path) {
        return listRecursive(path, -1);
    }

    public ArrayList<String> list(String path) {
        File f = new File(realpath(path));
        File[] files = f.listFiles();
        ArrayList<String> list = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                list.add(subsystemPath(file.getAbsolutePath()));
            }
        }
        return list;
    }


    public void writeString(String path, String content) throws IOException {
        writeString(path, content, stringIOEncryptionKey);
    }

    public void writeString(String path, String content, String key) throws IOException {
        if (useStringIOEncryption) {
            try {
                content = CoreAES.encrypt(content, key);
            }catch (Exception e) {
                throw new IOException(e);
            }
        }
        if (writeStringIOBase64) {
            content = CoreBase64.encode(content);
        }
        StringIO.writeFileToDisk(realpath(path), content);
    }

    public String readString(String path) throws IOException {
        return readString(path, stringIOEncryptionKey);
    }

    public String readString(String path, String key) throws IOException {
        String content = StringIO.readFileFromDisk(realpath(path));
        if (writeStringIOBase64) {
            content = CoreBase64.decode(content);
        }
        if (useStringIOEncryption) {
            try {
                content = CoreAES.decrypt(content, key);
            }catch (Exception e) {
                throw new IOException(e);
            }
        }
        return content;
    }

    public void delete(String path) {
        File f = new File(realpath(path));
        if (f.isDirectory()) {
            for (String file : list(path)) {
                delete(file);
            }
        }
        if (!f.delete()) {
            throw new RuntimeException("Failed to delete file " + path);
        }
    }

    public void clearDisk() {
        delete("/");
    }

    public void generateSubsystemStructure() {
        mkdirs(
            "/Library/config",
                "/Library/Developer/Frameworks",
                "/Library/Developer/ZLang",
                "/Library/Logs",
                "/Library/nvram",
                "/Library/Preferences",
                "/Library/Services",
                "/System/bin",
                "/System/boot/bootloader",
                "/System/core/bin",
                "/System/core/extensions",
                "/System/core/functions",
                "/System/core/man",
                "/System/man",
                "/System/sys/Library",
                "/System/sys/Library/Developer",
                "/System/sys/Library/Functions",
                "/System/sys/Library/Hooks",
                "/System/sys/Library/Legacy",
                "/System/sys/Library/Services"
        );
    }

    public void setConfig(String id, String value) {
        generateSubsystemStructure();
        try {
            writeString("/Library/config/" + id + ".cfg", value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getConfig(String id, boolean noTrim) {
        try {
            return noTrim ? readString("/Library/config/" + id + ".cfg") : readString("/Library/config/" + id + ".cfg").trim();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getConfig(String id) {
        return getConfig(id, false);
    }

    public void initConfig(String id, String defaultValue) {
        try {
            readString("/Library/config/" + id + ".cfg");
        } catch (IOException e) {
            setConfig(id, defaultValue);
        }
    }
}
