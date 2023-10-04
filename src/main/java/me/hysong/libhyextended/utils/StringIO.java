package me.hysong.libhyextended.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class StringIO {

    public static String readFileFromDisk(String path, String lineSeparator) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line).append(lineSeparator);
        }
        reader.close();
        return builder.toString();
    }

    public static String readFileFromDisk(String path) throws IOException {
        return readFileFromDisk(path, "\n");
    }

    public static void writeFileToDisk(String path, String content) throws IOException {
//        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8)) {
//            writer.write(content);
//        }

        FileOutputStream output=new FileOutputStream(path,false);
        OutputStreamWriter writer=new OutputStreamWriter(output,StandardCharsets.UTF_8);
        BufferedWriter out=new BufferedWriter(writer);
        out.write(content);
        out.flush();
        out.close();
        writer.close();
        output.close();
    }

    public static void appendFileToDisk(String path, String content) throws IOException {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(path, true), StandardCharsets.UTF_8)) {
            writer.write(content);
        }
    }
}
