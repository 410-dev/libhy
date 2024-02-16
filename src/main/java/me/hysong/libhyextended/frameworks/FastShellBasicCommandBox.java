package me.hysong.libhyextended.frameworks;

@FastShellSDKSpec(sdkVersion = 1)
public class FastShellBasicCommandBox implements FastShellCommandBox {

    @FastShellCommand
    @FastShellCommandManual("Prints the manual for a command, or lists all commands if no command is provided.")
    public static int help(FastShell sh, String[] args) {
        if (args.length == 0) {
            for (String cmd : sh.getCommandManualMap().keySet()) {
                System.out.println(cmd);
            }
            return 0;
        }
        if (sh.getCommandManualMap().containsKey(args[0])) {
            System.out.println(args[0] + ":\n" + sh.getCommandManualMap().get(args[0]));
            return 0;
        } else {
            System.out.println("Command not found.");
        }
        return 0;
    }

    @FastShellCommand
    @FastShellCommandManual("Exits the shell with a status code. If no status code is provided, exits with 0.")
    public static int exit(FastShell sh, String[] args) {
        try {
            System.exit(Integer.parseInt(args[0]));
        } catch (Exception e) {
            System.exit(0);
        }
        return 0;
    }

    @FastShellCommand
    @FastShellCommandManual("Prints the arguments provided.")
    public static int echo(FastShell sh, String[] args) {
        for (String arg : args) {
            System.out.print(arg + " ");
        }
        return 0;
    }

    @FastShellCommand
    @FastShellCommandManual("Prints the version of the shell.")
    public static int version(FastShell sh, String[] args) {
        System.out.println(sh.TAG + " v" + sh.VERSION);
        return 0;
    }

    @FastShellCommand
    @FastShellCommandManual("Prints the environment variables, or sets a variable if a value is provided.")
    public static int var(FastShell sh, String[] args) {
        if (args.length == 0) {
            for (String var : sh.getEnvironmentVariables().keySet()) {
                System.out.println(var + " = " + sh.getEnvironmentVariables().get(var));
            }
            return 0;
        }
        if (args.length == 1) {
            if (sh.getEnvironmentVariables().containsKey(args[0])) {
                System.out.println(args[0] + " = " + sh.getEnvironmentVariables().get(args[0]));
            } else {
                System.out.println("Variable not found.");
            }
            return 0;
        }
        if (args.length == 2) {
            sh.getEnvironmentVariables().put(args[0], args[1]);
            return 0;
        }
        System.out.println("Invalid arguments.");
        return 1;
    }

}
