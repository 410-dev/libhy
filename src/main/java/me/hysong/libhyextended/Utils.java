package me.hysong.libhyextended;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static int max(int[] list) {
        int max = Integer.MIN_VALUE;
        for (int i : list) {
            if (i > max) max = i;
        }
        return max;
    }

    public static int min(int[] list) {
        int min = Integer.MAX_VALUE;
        for (int i : list) {
            if (i < min) min = i;
        }
        return min;
    }

    public static int sum(int[] list) {
        int sum = 0;
        for (int i : list) {
            sum += i;
        }
        return sum;
    }

    public static int avg(int[] list) {
        return sum(list) / list.length;
    }

    public static int median(int[] list) {
        return list[list.length / 2];
    }


    public static int mode(int[] list) {
        int mode = 0;
        int max = 0;
        for (int i : list) {
            int count = 0;
            for (int j : list) {
                if (i == j) count++;
            }
            if (count > max) {
                max = count;
                mode = i;
            }
        }
        return mode;
    }


    public static int range(int[] list) {
        return max(list) - min(list);
    }

    public static int[] range(int min, int max, int jumpBy) {
        int[] range = new int[(max - min) / jumpBy];
        for (int i = 0; i < range.length; i++) {
            range[i] = min + (i * jumpBy);
        }
        return range;
    }

    public static int[] range(int min, int max) {
        return range(min, max, 1);
    }

    public static int[] range(int max) {
        return range(0, max, 1);
    }

    public static short max(short[] list) {
        short max = Short.MIN_VALUE;
        for (short i : list) {
            if (i > max) max = i;
        }
        return max;
    }

    public static short min(short[] list) {
        short min = Short.MAX_VALUE;
        for (short i : list) {
            if (i < min) min = i;
        }
        return min;
    }

    public static short sum(short[] list) {
        short sum = 0;
        for (short i : list) {
            sum += i;
        }
        return sum;
    }

    public static short avg(short[] list) {
        return (short) (sum(list) / list.length);
    }

    public static short median(short[] list) {
        return list[list.length / 2];
    }

    public static short mode(short[] list) {
        short mode = 0;
        short max = 0;
        for (short i : list) {
            short count = 0;
            for (short j : list) {
                if (i == j) count++;
            }
            if (count > max) {
                max = count;
                mode = i;
            }
        }
        return mode;
    }

    public static short range(short[] list) {
        return (short) (max(list) - min(list));
    }

    public static short[] range(short min, short max, short jumpBy) {
        short[] range = new short[(max - min) / jumpBy];
        for (short i = 0; i < range.length; i++) {
            range[i] = (short) (min + (i * jumpBy));
        }
        return range;
    }

    public static short[] range(short min, short max) {
        return range(min, max, (short) 1);
    }

    public static short[] range(short max) {
        return range((short) 0, max, (short) 1);
    }

    public static long max(long[] list) {
        long max = Long.MIN_VALUE;
        for (long i : list) {
            if (i > max) max = i;
        }
        return max;
    }

    public static long min(long[] list) {
        long min = Long.MAX_VALUE;
        for (long i : list) {
            if (i < min) min = i;
        }
        return min;
    }

    public static long sum(long[] list) {
        long sum = 0;
        for (long i : list) {
            sum += i;
        }
        return sum;
    }

    public static long avg(long[] list) {
        return sum(list) / list.length;
    }

    public static long median(long[] list) {
        return list[list.length / 2];
    }

    public static long mode(long[] list) {
        long mode = 0;
        long max = 0;
        for (long i : list) {
            long count = 0;
            for (long j : list) {
                if (i == j) count++;
            }
            if (count > max) {
                max = count;
                mode = i;
            }
        }
        return mode;
    }

    public static long range(long[] list) {
        return max(list) - min(list);
    }

    public static long[] range(long min, long max, long jumpBy) {
        long[] range = new long[(int) ((max - min) / jumpBy)];
        for (int i = 0; i < range.length; i++) {
            range[i] = min + (i * jumpBy);
        }
        return range;
    }

    public static long[] range(long min, long max) {
        return range(min, max, 1);
    }

    public static long[] range(long max) {
        return range(0, max, 1);
    }

    public static float max(float[] list) {
        float max = Float.MIN_VALUE;
        for (float i : list) {
            if (i > max) max = i;
        }
        return max;
    }

    public static float min(float[] list) {
        float min = Float.MAX_VALUE;
        for (float i : list) {
            if (i < min) min = i;
        }
        return min;
    }

    public static float sum(float[] list) {
        float sum = 0;
        for (float i : list) {
            sum += i;
        }
        return sum;
    }

    public static float avg(float[] list) {
        return sum(list) / list.length;
    }

    public static float median(float[] list) {
        return list[list.length / 2];
    }

    public static float mode(float[] list) {
        float mode = 0;
        float max = 0;
        for (float i : list) {
            float count = 0;
            for (float j : list) {
                if (i == j) count++;
            }
            if (count > max) {
                max = count;
                mode = i;
            }
        }
        return mode;
    }

    public static float range(float[] list) {
        return max(list) - min(list);
    }

    public static float[] range(float min, float max, float jumpBy) {
        float[] range = new float[(int) ((max - min) / jumpBy)];
        for (int i = 0; i < range.length; i++) {
            range[i] = min + (i * jumpBy);
        }
        return range;
    }

    public static float[] range(float min, float max) {
        return range(min, max, 1);
    }

    public static float[] range(float max) {
        return range(0, max, 1);
    }

    public static double max(double[] list) {
        double max = Double.MIN_VALUE;
        for (double i : list) {
            if (i > max) max = i;
        }
        return max;
    }

    public static double min(double[] list) {
        double min = Double.MAX_VALUE;
        for (double i : list) {
            if (i < min) min = i;
        }
        return min;
    }

    public static double sum(double[] list) {
        double sum = 0;
        for (double i : list) {
            sum += i;
        }
        return sum;
    }

    public static double avg(double[] list) {
        return sum(list) / list.length;
    }

    public static double median(double[] list) {
        return list[list.length / 2];
    }

    public static double mode(double[] list) {
        double mode = 0;
        double max = 0;
        for (double i : list) {
            double count = 0;
            for (double j : list) {
                if (i == j) count++;
            }
            if (count > max) {
                max = count;
                mode = i;
            }
        }
        return mode;
    }

    public static double range(double[] list) {
        return max(list) - min(list);
    }

    public static double[] range(double min, double max, double jumpBy) {
        double[] range = new double[(int) ((max - min) / jumpBy)];
        for (int i = 0; i < range.length; i++) {
            range[i] = min + (i * jumpBy);
        }
        return range;
    }

    public static double[] range(double min, double max) {
        return range(min, max, 1);
    }

    public static double[] range(double max) {
        return range(0, max, 1);
    }

    public static byte max(byte[] list) {
        byte max = Byte.MIN_VALUE;
        for (byte i : list) {
            if (i > max) max = i;
        }
        return max;
    }

    public static byte min(byte[] list) {
        byte min = Byte.MAX_VALUE;
        for (byte i : list) {
            if (i < min) min = i;
        }
        return min;
    }

    public static byte sum(byte[] list) {
        byte sum = 0;
        for (byte i : list) {
            sum += i;
        }
        return sum;
    }

    public static byte avg(byte[] list) {
        return (byte) (sum(list) / list.length);
    }

    public static byte median(byte[] list) {
        return list[list.length / 2];
    }

    public static byte mode(byte[] list) {
        byte mode = 0;
        byte max = 0;
        for (byte i : list) {
            byte count = 0;
            for (byte j : list) {
                if (i == j) count++;
            }
            if (count > max) {
                max = count;
                mode = i;
            }
        }
        return mode;
    }

    public static byte range(byte[] list) {
        return (byte) (max(list) - min(list));
    }

    public static byte[] range(byte min, byte max, byte jumpBy) {
        byte[] range = new byte[(int) ((max - min) / jumpBy)];
        for (int i = 0; i < range.length; i++) {
            range[i] = (byte) (min + (i * jumpBy));
        }
        return range;
    }

    public static byte[] range(byte min, byte max) {
        return range(min, max, (byte) 1);
    }

    public static byte[] range(byte max) {
        return range((byte) 0, max, (byte) 1);
    }

    public static String[] splitStringBySpaceWithQuotationConsideration(String s) {

        // Regular expression to match quoted or non-quoted parts of the command
        String regex = "\"([^\"]*)\"|'([^']*)'|\\S+";
        List<String> arguments = new ArrayList<>();

        // Use regex pattern to split the command into arguments
        Matcher matcher = Pattern.compile(regex).matcher(s);
        while (matcher.find()) {
            // Add the matched group to the arguments list
            String argument = matcher.group();
            // Remove the surrounding quotes if present
            if (argument.startsWith("\"") && argument.endsWith("\"") ||
                    argument.startsWith("'") && argument.endsWith("'")) {
                argument = argument.substring(1, argument.length() - 1);
            }
            arguments.add(argument);
        }

        // Convert the list to an array and return
        return arguments.toArray(new String[0]);
    }
}
