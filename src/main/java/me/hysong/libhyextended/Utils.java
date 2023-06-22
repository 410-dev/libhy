package me.hysong.libhyextended;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Simple utilities
 */
public class Utils {

    /**
     * Print a string to stdout
     * @param s String to print
     * @param endsWith String to end with
     */
    public static void print(Object s, String endsWith) {
        System.out.print(s + endsWith);
    }

    /**
     * Print a string to stdout with a newline at the end
     * @param s String to print
     */
    public static void print(Object s) {
        print(s, "\n");
    }

    /**
     * Print a string to stderr
     * @param s String to print
     */
    public static void err(String s) {
        System.err.println(s);
    }

    /**
     * Find maximum integer in an array
     * @param list Array to search
     * @return Maximum integer
     */
    public static int max(int[] list) {
        int max = Integer.MIN_VALUE;
        for (int i : list) {
            if (i > max) max = i;
        }
        return max;
    }

    /**
     * Find minimum integer in an array
     * @param list Array to search
     * @return Minimum integer
     */
    public static int min(int[] list) {
        int min = Integer.MAX_VALUE;
        for (int i : list) {
            if (i < min) min = i;
        }
        return min;
    }

    /**
     * Sum all integers in an array
     * @param list Array to sum
     * @return Sum of all integers in an array
     */
    public static int sum(int[] list) {
        int sum = 0;
        for (int i : list) {
            sum += i;
        }
        return sum;
    }

    /**
     * Find average of all integers in an array
     * @param list Array to average
     * @return Average of all integers in an array
     */
    public static int avg(int[] list) {
        return sum(list) / list.length;
    }

    /**
     * Find median of all integers in an array
     * @param list Array to find median of
     * @return Median of all integers in an array
     */
    public static int median(int[] list) {
        return list[list.length / 2];
    }

    /**
     * Find mode of all integers in an array
     * @param list Array to find mode of
     * @return Mode of all integers in an array
     */
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

    /**
     * Find difference between maximum and minimum integers in an array
     * @param list Array to find difference of
     * @return Difference between maximum and minimum integers in an array
     */
    public static int maxDifference(int[] list) {
        return max(list) - min(list);
    }

    /**
     * Get array of integers in a range, jumping by a specified amount. Range is defined as [min, max), so max is not inclusive.
     * @param min Minimum integer
     * @param max Maximum integer
     * @param jumpBy Jump by this amount
     * @return Array of integers in a range
     */
    public static int[] range(int min, int max, int jumpBy) {
        int[] range = new int[(max - min) / jumpBy];
        for (int i = 0; i < range.length; i++) {
            range[i] = min + (i * jumpBy);
        }
        return range;
    }

    /**
     * Get array of integers in a range, jumping by 1. Range is defined as [min, max), so max is not inclusive.
     * @param min Minimum integer
     * @param max Maximum integer
     * @return Array of integers in a range
     */
    public static int[] range(int min, int max) {
        return range(min, max, 1);
    }

    /**
     * Get array of integers in a range starting from 0, jumping by 1. Range is defined as [0, max), so max is not inclusive.
     * @param max Maximum integer
     * @return Array of integers in a range
     */
    public static int[] range(int max) {
        return range(0, max, 1);
    }

    /**
     * Find maximum short in an array
     * @param list Array to search
     * @return  Maximum short
     */
    public static short max(short[] list) {
        short max = Short.MIN_VALUE;
        for (short i : list) {
            if (i > max) max = i;
        }
        return max;
    }

    /**
     * Find minimum short in an array
     * @param list Array to search
     * @return Minimum short
     */
    public static short min(short[] list) {
        short min = Short.MAX_VALUE;
        for (short i : list) {
            if (i < min) min = i;
        }
        return min;
    }

    /**
     * Sum all shorts in an array
     * @param list Array to sum
     * @return Sum of all shorts in an array
     */
    public static short sum(short[] list) {
        short sum = 0;
        for (short i : list) {
            sum += i;
        }
        return sum;
    }

    /**
     * Find average of all shorts in an array
     * @param list Array to average
     * @return Average of all shorts in an array
     */
    public static short avg(short[] list) {
        return (short) (sum(list) / list.length);
    }

    /**
     * Find median of all shorts in an array
     * @param list Array to find median of
     * @return Median of all shorts in an array
     */
    public static short median(short[] list) {
        return list[list.length / 2];
    }

    /**
     * Find mode of all shorts in an array
     * @param list Array to find mode of
     * @return Mode of all shorts in an array
     */
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

    /**
     * Find difference between maximum and minimum shorts in an array
     * @param list Array to find difference of
     * @return Difference between maximum and minimum shorts in an array
     */
    public static short maxDifference(short[] list) {
        return (short) (max(list) - min(list));
    }

    /**
     * Get array of shorts in a range, jumping by a specified amount. Range is defined as [min, max), so max is not inclusive.
     * @param min Minimum short
     * @param max Maximum short
     * @param jumpBy Jump by this amount
     * @return Array of shorts in a range
     */
    public static short[] range(short min, short max, short jumpBy) {
        short[] range = new short[(max - min) / jumpBy];
        for (short i = 0; i < range.length; i++) {
            range[i] = (short) (min + (i * jumpBy));
        }
        return range;
    }

    /**
     * Get array of shorts in a range, jumping by 1. Range is defined as [min, max), so max is not inclusive.
     * @param min Minimum short
     * @param max Maximum short
     * @return Array of shorts in a range
     */
    public static short[] range(short min, short max) {
        return range(min, max, (short) 1);
    }

    /**
     * Get array of shorts in a range starting from 0, jumping by 1. Range is defined as [0, max), so max is not inclusive.
     * @param max Maximum short
     * @return Array of shorts in a range
     */
    public static short[] range(short max) {
        return range((short) 0, max, (short) 1);
    }

    /**
     * Find maximum long in an array
     * @param list Array to search
     * @return  Maximum long
     */
    public static long max(long[] list) {
        long max = Long.MIN_VALUE;
        for (long i : list) {
            if (i > max) max = i;
        }
        return max;
    }

    /**
     * Find minimum long in an array
     * @param list Array to search
     * @return Minimum long
     */
    public static long min(long[] list) {
        long min = Long.MAX_VALUE;
        for (long i : list) {
            if (i < min) min = i;
        }
        return min;
    }

    /**
     * Sum all longs in an array
     * @param list Array to sum
     * @return Sum of all longs in an array
     */
    public static long sum(long[] list) {
        long sum = 0;
        for (long i : list) {
            sum += i;
        }
        return sum;
    }

    /**
     * Find average of all longs in an array
     * @param list Array to average
     * @return Average of all longs in an array
     */
    public static long avg(long[] list) {
        return sum(list) / list.length;
    }

    /**
     * Find median of all longs in an array
     * @param list Array to find median of
     * @return Median of all longs in an array
     */
    public static long median(long[] list) {
        return list[list.length / 2];
    }

    /**
     * Find mode of all longs in an array
     * @param list Array to find mode of
     * @return Mode of all longs in an array
     */
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

    /**
     * Find difference between maximum and minimum longs in an array
     * @param list Array to find difference of
     * @return Difference between maximum and minimum longs in an array
     */
    public static long maxDifference(long[] list) {
        return max(list) - min(list);
    }

    /**
     * Get array of longs in a range, jumping by a specified amount. Range is defined as [min, max), so max is not inclusive.
     * @param min Minimum long
     * @param max Maximum long
     * @param jumpBy Jump by this amount
     * @return Array of longs in a range
     */
    public static long[] range(long min, long max, long jumpBy) {
        long[] range = new long[(int) ((max - min) / jumpBy)];
        for (int i = 0; i < range.length; i++) {
            range[i] = min + (i * jumpBy);
        }
        return range;
    }

    /**
     * Get array of longs in a range, jumping by 1. Range is defined as [min, max), so max is not inclusive.
     * @param min Minimum long
     * @param max Maximum long
     * @return Array of longs in a range
     */
    public static long[] range(long min, long max) {
        return range(min, max, 1);
    }

    /**
     * Get array of longs in a range starting from 0, jumping by 1. Range is defined as [0, max), so max is not inclusive.
     * @param max Maximum long
     * @return Array of longs in a range
     */
    public static long[] range(long max) {
        return range(0, max, 1);
    }

    /**
     * Find maximum float in an array
     * @param list Array to search
     * @return  Maximum float
     */
    public static float max(float[] list) {
        float max = Float.MIN_VALUE;
        for (float i : list) {
            if (i > max) max = i;
        }
        return max;
    }

    /**
     * Find minimum float in an array
     * @param list Array to search
     * @return Minimum float
     */
    public static float min(float[] list) {
        float min = Float.MAX_VALUE;
        for (float i : list) {
            if (i < min) min = i;
        }
        return min;
    }

    /**
     * Sum all floats in an array
     * @param list Array to sum
     * @return Sum of all floats in an array
     */
    public static float sum(float[] list) {
        float sum = 0;
        for (float i : list) {
            sum += i;
        }
        return sum;
    }

    /**
     * Find average of all floats in an array
     * @param list Array to average
     * @return Average of all floats in an array
     */
    public static float avg(float[] list) {
        return sum(list) / list.length;
    }

    /**
     * Find median of all floats in an array
     * @param list Array to find median of
     * @return Median of all floats in an array
     */
    public static float median(float[] list) {
        return list[list.length / 2];
    }

    /**
     * Find mode of all floats in an array
     * @param list Array to find mode of
     * @return Mode of all floats in an array
     */
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

    /**
     * Find difference between maximum and minimum floats in an array
     * @param list Array to find difference of
     * @return Difference between maximum and minimum floats in an array
     */
    public static float maxDifference(float[] list) {
        return max(list) - min(list);
    }

    /**
     * Get array of floats in a range, jumping by a specified amount. Range is defined as [min, max), so max is not inclusive.
     * @param min Minimum float
     * @param max Maximum float
     * @param jumpBy Jump by this amount
     * @return Array of floats in a range
     */
    public static float[] range(float min, float max, float jumpBy) {
        float[] range = new float[(int) ((max - min) / jumpBy)];
        for (int i = 0; i < range.length; i++) {
            range[i] = min + (i * jumpBy);
        }
        return range;
    }

    /**
     * Get array of floats in a range, jumping by 1. Range is defined as [min, max), so max is not inclusive.
     * @param min Minimum float
     * @param max Maximum float
     * @return Array of floats in a range
     */
    public static float[] range(float min, float max) {
        return range(min, max, 1);
    }

    /**
     * Get array of floats in a range starting from 0, jumping by 1. Range is defined as [0, max), so max is not inclusive.
     * @param max Maximum float
     * @return Array of floats in a range
     */
    public static float[] range(float max) {
        return range(0, max, 1);
    }

    /**
     * Find maximum double in an array
     * @param list Array to search
     * @return  Maximum double
     */
    public static double max(double[] list) {
        double max = Double.MIN_VALUE;
        for (double i : list) {
            if (i > max) max = i;
        }
        return max;
    }

    /**
     * Find minimum double in an array
     * @param list Array to search
     * @return Minimum double
     */
    public static double min(double[] list) {
        double min = Double.MAX_VALUE;
        for (double i : list) {
            if (i < min) min = i;
        }
        return min;
    }

    /**
     * Sum all doubles in an array
     * @param list Array to sum
     * @return Sum of all doubles in an array
     */
    public static double sum(double[] list) {
        double sum = 0;
        for (double i : list) {
            sum += i;
        }
        return sum;
    }

    /**
     * Find average of all doubles in an array
     * @param list Array to average
     * @return Average of all doubles in an array
     */
    public static double avg(double[] list) {
        return sum(list) / list.length;
    }

    /**
     * Find median of all doubles in an array
     * @param list Array to find median of
     * @return Median of all doubles in an array
     */
    public static double median(double[] list) {
        return list[list.length / 2];
    }

    /**
     * Find mode of all doubles in an array
     * @param list Array to find mode of
     * @return Mode of all doubles in an array
     */
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

    /**
     * Find difference between maximum and minimum doubles in an array
     * @param list Array to find difference of
     * @return Difference between maximum and minimum doubles in an array
     */
    public static double maxDifference(double[] list) {
        return max(list) - min(list);
    }

    /**
     * Get array of doubles in a range, jumping by a specified amount. Range is defined as [min, max), so max is not inclusive.
     * @param min Minimum double
     * @param max Maximum double
     * @param jumpBy Jump by this amount
     * @return Array of doubles in a range
     */
    public static double[] range(double min, double max, double jumpBy) {
        double[] range = new double[(int) ((max - min) / jumpBy)];
        for (int i = 0; i < range.length; i++) {
            range[i] = min + (i * jumpBy);
        }
        return range;
    }

    /**
     * Get array of doubles in a range, jumping by 1. Range is defined as [min, max), so max is not inclusive.
     * @param min Minimum double
     * @param max Maximum double
     * @return Array of doubles in a range
     */
    public static double[] range(double min, double max) {
        return range(min, max, 1);
    }

    /**
     * Get array of doubles in a range starting from 0, jumping by 1. Range is defined as [0, max), so max is not inclusive.
     * @param max Maximum double
     * @return Array of doubles in a range
     */
    public static double[] range(double max) {
        return range(0, max, 1);
    }

    /**
     * Find maximum byte in an array
     * @param list Array to search
     * @return  Maximum byte
     */
    public static byte max(byte[] list) {
        byte max = Byte.MIN_VALUE;
        for (byte i : list) {
            if (i > max) max = i;
        }
        return max;
    }

    /**
     * Find minimum byte in an array
     * @param list Array to search
     * @return Minimum byte
     */
    public static byte min(byte[] list) {
        byte min = Byte.MAX_VALUE;
        for (byte i : list) {
            if (i < min) min = i;
        }
        return min;
    }

    /**
     * Sum all bytes in an array
     * @param list Array to sum
     * @return Sum of all bytes in an array
     */
    public static byte sum(byte[] list) {
        byte sum = 0;
        for (byte i : list) {
            sum += i;
        }
        return sum;
    }

    /**
     * Find average of all bytes in an array
     * @param list Array to average
     * @return Average of all bytes in an array
     */
    public static byte avg(byte[] list) {
        return (byte) (sum(list) / list.length);
    }

    /**
     * Find median of all bytes in an array
     * @param list Array to find median of
     * @return Median of all bytes in an array
     */
    public static byte median(byte[] list) {
        return list[list.length / 2];
    }

    /**
     * Find mode of all bytes in an array
     * @param list Array to find mode of
     * @return Mode of all bytes in an array
     */
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

    /**
     * Find difference between maximum and minimum bytes in an array
     * @param list Array to find difference of
     * @return Difference between maximum and minimum bytes in an array
     */
    public static byte maxDifference(byte[] list) {
        return (byte) (max(list) - min(list));
    }

    /**
     * Get array of bytes in a range, jumping by a specified amount. Range is defined as [min, max), so max is not inclusive.
     * @param min Minimum byte
     * @param max Maximum byte
     * @param jumpBy Jump by this amount
     * @return Array of bytes in a range
     */
    public static byte[] range(byte min, byte max, byte jumpBy) {
        byte[] range = new byte[(int) ((max - min) / jumpBy)];
        for (int i = 0; i < range.length; i++) {
            range[i] = (byte) (min + (i * jumpBy));
        }
        return range;
    }

    /**
     * Get array of bytes in a range, jumping by 1. Range is defined as [min, max), so max is not inclusive.
     * @param min Minimum byte
     * @param max Maximum byte
     * @return Array of bytes in a range
     */
    public static byte[] range(byte min, byte max) {
        return range(min, max, (byte) 1);
    }

    /**
     * Get array of bytes in a range starting from 0, jumping by 1. Range is defined as [0, max), so max is not inclusive.
     * @param max Maximum byte
     * @return Array of bytes in a range
     */
    public static byte[] range(byte max) {
        return range((byte) 0, max, (byte) 1);
    }

    /**
     * Split a string by space, but consider quoted strings as one argument.
     * @param s String to split
     * @return Array of arguments
     */
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
