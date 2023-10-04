package me.hysong.libhyextended;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Shell {
    public static int execute(String[] command, boolean silence, boolean waitFor) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(command);

        // Read and print the output of the script
        if (!silence) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }

        if (waitFor)
            return process.waitFor();
        else
            return 0;
    }

    public static int execute(String[] command, boolean silence) {
        try {
            return execute(command, silence, true);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int execute(String[] command) {
        try {
            return execute(command, false, true);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int execute(ArrayList<String> command) throws IOException, InterruptedException {
        return execute(command.toArray(new String[0]), false, true);
    }

    public static int execute(ArrayList<String> command, boolean silence) {
        try {
            return execute(command.toArray(new String[0]), silence, true);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int execute(ArrayList<String> command, boolean silence, boolean waitFor) throws IOException, InterruptedException {
        return execute(command.toArray(new String[0]), silence, waitFor);
    }

    public static String executeWithStdoutReturn(String[] command, boolean waitFor, String splicer) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(command);

        // Read and print the output of the script
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line).append(splicer);
        }

        if (waitFor)
            process.waitFor();

        return builder.toString();
    }

    public static String executeWithStdoutReturn(String[] command) {
        try {
            return executeWithStdoutReturn(command, true, "\n");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String executeWithStdoutReturn(ArrayList<String> command) {
        try {
            return executeWithStdoutReturn(command.toArray(new String[0]), true, "\n");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String executeWithStdoutReturn(ArrayList<String> command, boolean waitFor) {
        try {
            return executeWithStdoutReturn(command.toArray(new String[0]), waitFor, "\n");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object[] executeShell(String[] command, boolean silence, boolean waitFor, String splicer) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        // Read and print the output of the script
        BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader stderrReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        StringBuilder stdoutBuilder = new StringBuilder();
        StringBuilder stderrBuilder = new StringBuilder();
        String line;
        while ((line = stdoutReader.readLine()) != null) {
            stdoutBuilder.append(line).append(splicer);
        }
        while ((line = stderrReader.readLine()) != null) {
            stderrBuilder.append(line).append(splicer);
        }

        int exitCode = 0;
        if (waitFor) {
            exitCode = process.waitFor();
        }

        if (!silence) {
            System.out.println(stdoutBuilder.toString());
            System.err.println(stderrBuilder.toString());
        }

        return new Object[]{exitCode, stdoutBuilder.toString(), stderrBuilder.toString()};
    }

    public static Object[] executeShell(ArrayList<String> command, boolean silence, boolean waitFor) throws IOException, InterruptedException {
        return executeShell(command.toArray(new String[0]), silence, waitFor, "\n");
    }

    public static Object[] executeShell(ArrayList<String> command, boolean waitFor) throws IOException, InterruptedException {
        return executeShell(command.toArray(new String[0]), waitFor);
    }

    public static Object[] executeShell(ArrayList<String> command) throws IOException, InterruptedException {
        return executeShell(command.toArray(new String[0]));
    }

    public static Object[] executeShell(String[] command) throws IOException, InterruptedException {
        return executeShell(command, true);
    }

    public static Object[] executeShell(String[] command, boolean silence) throws IOException, InterruptedException {
        return executeShell(command, silence, true, "\n");
    }

}
