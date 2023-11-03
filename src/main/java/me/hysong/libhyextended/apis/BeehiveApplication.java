package me.hysong.libhyextended.apis;

import me.hysong.libhyextended.environment.SubsystemEnvironment;

import java.io.IOException;
import java.util.ArrayList;

public abstract class BeehiveApplication extends Thread implements BeehiveApplicationInterface {

    private final SubsystemEnvironment subsystemEnvironment;
    public final String[] args;

    public BeehiveApplication(String[] args, SubsystemEnvironment rootEnvironment, String environmentRoot) {
        super();
        this.args = args;
        if (rootEnvironment != null) {
            if (rootEnvironment.isUseSandbox() && (rootEnvironment.realpath(environmentRoot).equals(rootEnvironment.getRoot()))) {
                throw new RuntimeException("Sandbox subsystem environment cannot be in root location!");
            }
            subsystemEnvironment = rootEnvironment.generateChildEnvironment(environmentRoot);
        } else {
            subsystemEnvironment = null;
        }
    }

    public static final int SIGKILL = 9;
    public static final int SIGTERM = 15;
    public static final int SIGINT = 2;
    public static final int SIGSTOP = 17;
    public static final int SIGSEGV = 11;
    public int kill() {
        super.interrupt();
        super.setDaemon(true);
        return SIGKILL;
    }

    public int terminate() {
        kill();
        return SIGTERM;
    }

    public int segmentFault() {
        kill();
        return SIGSEGV;
    }

    public int kill(int killCode) {
        return switch (killCode) {
            case SIGTERM -> terminate();
            case SIGINT -> {
                interrupt();
                yield SIGINT;
            }
            case SIGSEGV -> segmentFault();
            default -> {
                kill();
                yield killCode;
            }
        };
    }

    // IO Passthrough with limitations
    public boolean mkdirs(String path) {
        return subsystemEnvironment.mkdirs(path);
    }

    public boolean mkdir(String path) {
        return subsystemEnvironment.mkdirs(path);
    }

    public boolean mkdirs(String... paths) {
        return subsystemEnvironment.mkdirs(paths);
    }

    public ArrayList<String> listRecursive(String path, int limitDepth) {
        return subsystemEnvironment.listRecursive(path, limitDepth);
    }

    public ArrayList<String> listRecursive(String path) {
        return subsystemEnvironment.listRecursive(path);
    }

    public ArrayList<String> list(String path) {
        return subsystemEnvironment.list(path);
    }

    public void writeString(String path, String content) throws IOException {
        subsystemEnvironment.writeString(path, content);
    }

    public String readString(String path) throws IOException {
        return subsystemEnvironment.readString(path);
    }

    public void delete(String path) throws IOException {
        subsystemEnvironment.delete(path);
    }

    public void rename(String path, String newPath) throws IOException {
        subsystemEnvironment.rename(path, newPath);
    }

    public void copy(String path, String newPath) throws IOException {
        subsystemEnvironment.copy(path, newPath);
    }
}
