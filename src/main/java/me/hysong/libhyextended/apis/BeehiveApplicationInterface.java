package me.hysong.libhyextended.apis;

import me.hysong.libhyextended.environment.SubsystemEnvironment;

import java.io.IOException;
import java.util.ArrayList;

public interface BeehiveApplicationInterface {

    public int launch();
    public int kill();
    public int terminate();
    public int segmentFault();
    public int kill(int killCode);

    public boolean mkdirs(String path);
    public boolean mkdir(String path);
    public boolean mkdirs(String... paths);
    public ArrayList<String> listRecursive(String path, int limitDepth);
    public ArrayList<String> listRecursive(String path);
    public ArrayList<String> list(String path);
    public void writeString(String path, String content) throws IOException;
    public String readString(String path) throws IOException;
    public void delete(String path) throws IOException;
    public void rename(String path, String newPath) throws IOException;
    public void copy(String path, String newPath) throws IOException;

}
