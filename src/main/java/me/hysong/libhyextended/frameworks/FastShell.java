package me.hysong.libhyextended.frameworks;

import lombok.Getter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class FastShell {

    public final String TAG = "FastShell";
    public final String VERSION = "1.0";
    public final int sdkVersion = 1;
    private boolean running = false;
    @Getter private Scanner input = null;

    private final ArrayList<FastShellCommandBox> boxes = new ArrayList<>();
    @Getter private final HashMap<String, Method> commandMap = new HashMap<>();
    @Getter private final HashMap<String, String> commandManualMap = new HashMap<>();
    @Getter private final HashMap<String, String> environmentVariables = new HashMap<>();
    private final boolean useIndexing;
    private final boolean verbose;

    public FastShell(boolean useIndexing, boolean verbose, FastShellCommandBox... commandBoxes) {
        this.useIndexing = useIndexing;
        this.verbose = verbose;
        boxes.add(new FastShellBasicCommandBox());
        boxes.addAll(Arrays.asList(commandBoxes));
        index();
    }

    public FastShell(FastShellCommandBox ... commandBoxes) {
        this(true, false, commandBoxes);
    }

    public void addCommandBox(FastShellCommandBox box) {
        boxes.add(box);
        index();
    }

    public void removeCommandBox(FastShellCommandBox box) {
        boxes.remove(box);
        index();
    }

    public void index() {
        if (!useIndexing) {
            if (verbose) System.out.println("Indexing is disabled, skipping... This may cause slight performance issues.");
            return;
        }

        if (verbose) System.out.println("Indexing commands...");

        for (FastShellCommandBox box : boxes) {
            // Check for SDKSpec annotation
            if (box.getClass().isAnnotationPresent(FastShellSDKSpec.class)) {
                FastShellSDKSpec spec = box.getClass().getAnnotation(FastShellSDKSpec.class);
                if (spec.sdkVersion() > sdkVersion) {
                    System.out.println("ERROR: Command box " + box.getClass().getName() + " has invalid SDK version. (Too high) Skipping...");
                    continue;
                } else if (spec.sdkVersion() < sdkVersion) {
                    System.out.println("WARNING: Command box " + box.getClass().getName() + " has outdated SDK version. (Too low) This may cause issues.");
                }
            } else {
                System.out.println("WARNING: Command box " + box.getClass().getName() + " does not have SDK version specified. This may cause issues.");
            }

            for (Method method : box.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(FastShellCommand.class)) {

                    // Method with FastShellCommand annotation must have a specific signature
                    // public static int methodName(FastShell sh, String[] args)
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length != 2) {
                        System.out.println("ERROR: Command method " + method.getName() + " has invalid signature. (Size Mismatch) Skipping...");
                        commandMap.remove(method.getName());
                        continue;
                    }
                    if (parameterTypes[0] != FastShell.class) {
                        System.out.println("ERROR: Command method " + method.getName() + " has invalid signature. (P1 Mismatch) Skipping...");
                        commandMap.remove(method.getName());
                        continue;
                    }
                    if (parameterTypes[1] != String[].class) {
                        System.out.println("ERROR: Command method " + method.getName() + " has invalid signature. (P2 Mismatch) Skipping...");
                        commandMap.remove(method.getName());
                    }
                    if (method.getReturnType() != int.class) {
                        System.out.println("ERROR: Command method " + method.getName() + " has invalid signature. (Return Mismatch) Skipping...");
                        commandMap.remove(method.getName());
                        continue;
                    }
                    if (!java.lang.reflect.Modifier.isStatic(method.getModifiers())) {
                        System.out.println("ERROR: Command method " + method.getName() + " has invalid signature. (Not Static) Skipping...");
                        commandMap.remove(method.getName());
                        continue;
                    }

                    // Verify success
                    commandMap.put(method.getName(), method);
                }
                if (method.isAnnotationPresent(FastShellCommandManual.class)) {
                    commandManualMap.put(method.getName(), method.getAnnotation(FastShellCommandManual.class).value());
                }
            }
        }

        if (verbose) {
            System.out.println("Indexing complete.");
            System.out.println("Commands indexed: " + commandMap.size());
            System.out.println("Manuals indexed: " + commandManualMap.size());
            if (commandMap.isEmpty()) {
                System.out.println("WARNING: No commands indexed. This may cause issues.");
            }
            if (commandMap.size() != commandManualMap.size()) {
                System.out.println("WARNING: Command count and manual count mismatch. This may cause issues.");
            }
        }
    }

    public Method getCommandMethod(String command) {
        Method m = null;
        if (useIndexing) {
            m = commandMap.get(command);
        } else {
            boolean found = false;
            for (FastShellCommandBox box : boxes) {
                if (found) break;
                for (Method method : box.getClass().getDeclaredMethods()) {
                    if (method.isAnnotationPresent(FastShellCommand.class) && method.getName().equals(command)) {
                        m = method;
                        found = true;
                        break;
                    }
                }
            }
        }
        return m;
    }

    public void stop() {
        running = false;
    }

    public void start() {
        System.out.println(TAG + " " + VERSION);
        input = new Scanner(System.in);
        running = true;
        while (running) {
            System.out.print("> ");
            String[] args = input.nextLine().split(" ");
            String command = args[0];
            args = Arrays.copyOfRange(args, 1, args.length);
            Method m = getCommandMethod(command);
            if (m != null) {
                try {
                    int status = (int) m.invoke(null, this, args);
                    if (status != 0) {
                        System.out.println("Command exited with status " + status);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Command not found.");
            }
        }
    }
}
