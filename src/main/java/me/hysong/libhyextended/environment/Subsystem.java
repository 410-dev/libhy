package me.hysong.libhyextended.environment;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class Subsystem {

    private static ArrayList<SubsystemEnvironment> subsystems = new ArrayList<>();

    public static SubsystemEnvironment newSubsystem(String name, String root) {
        SubsystemEnvironment env = new SubsystemEnvironment(name, root);
        subsystems.add(env);
        return env;
    }

    public static SubsystemEnvironment newSubsystem(String root) {
        SubsystemEnvironment env = new SubsystemEnvironment(root);
        subsystems.add(env);
        return env;
    }

    public static SubsystemEnvironment getSubsystem(String name) {
        for (SubsystemEnvironment env : subsystems) {
            if (env.getName().equals(name)) {
                return env;
            }
        }
        return null;
    }

    public static boolean purgeSubsystem(String name) {
        if (name == null) {
            name = SubsystemEnvironment.defaultName;
        }
        try {
            Objects.requireNonNull(getSubsystem(name)).clearDisk();
        } catch (Exception e) {
            return false;
        }
        if (!new File(Objects.requireNonNull(getSubsystem(name)).getRoot()).delete()) {
            return false;
        }
        subsystems.remove(getSubsystem(name));
        return true;
    }

    public static boolean addSubsystem(SubsystemEnvironment env) {
        if (getSubsystem(env.getName()) != null) {
            return false;
        }
        subsystems.add(env);
        return true;
    }
}
