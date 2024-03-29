package me.hysong.libhyextended;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
     * @param array Array to search
     * @return Maximum integer
     */
    public static int max(int[] array) {
        int max = Integer.MIN_VALUE;
        for (int i : array) {
            if (i > max) max = i;
        }
        return max;
    }

    /**
     * Find minimum integer in an array
     * @param array Array to search
     * @return Minimum integer
     */
    public static int min(int[] array) {
        int min = Integer.MAX_VALUE;
        for (int i : array) {
            if (i < min) min = i;
        }
        return min;
    }

    /**
     * Sum all integers in an array
     * @param array Array to sum
     * @return Sum of all integers in an array
     */
    public static int sum(int[] array) {
        int sum = 0;
        for (int i : array) {
            sum += i;
        }
        return sum;
    }

    /**
     * Find average of all integers in an array
     * @param array Array to average
     * @return Average of all integers in an array
     */
    public static int avg(int[] array) {
        return sum(array) / array.length;
    }

    /**
     * Find median of all integers in an array
     * @param array Array to find median of
     * @return Median of all integers in an array
     */
    public static int median(int[] array) {
        return array[array.length / 2];
    }

    /**
     * Find mode of all integers in an array
     * @param array Array to find mode of
     * @return Mode of all integers in an array
     */
    public static int mode(int[] array) {
        int mode = 0;
        int max = 0;
        for (int i : array) {
            int count = 0;
            for (int j : array) {
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
     * @param array Array to find difference of
     * @return Difference between maximum and minimum integers in an array
     */
    public static int maxDifference(int[] array) {
        return max(array) - min(array);
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
     * @param array Array to search
     * @return  Maximum short
     */
    public static short max(short[] array) {
        short max = Short.MIN_VALUE;
        for (short i : array) {
            if (i > max) max = i;
        }
        return max;
    }

    /**
     * Find minimum short in an array
     * @param array Array to search
     * @return Minimum short
     */
    public static short min(short[] array) {
        short min = Short.MAX_VALUE;
        for (short i : array) {
            if (i < min) min = i;
        }
        return min;
    }

    /**
     * Sum all shorts in an array
     * @param array Array to sum
     * @return Sum of all shorts in an array
     */
    public static short sum(short[] array) {
        short sum = 0;
        for (short i : array) {
            sum += i;
        }
        return sum;
    }

    /**
     * Find average of all shorts in an array
     * @param array Array to average
     * @return Average of all shorts in an array
     */
    public static short avg(short[] array) {
        return (short) (sum(array) / array.length);
    }

    /**
     * Find median of all shorts in an array
     * @param array Array to find median of
     * @return Median of all shorts in an array
     */
    public static short median(short[] array) {
        return array[array.length / 2];
    }

    /**
     * Find mode of all shorts in an array
     * @param array Array to find mode of
     * @return Mode of all shorts in an array
     */
    public static short mode(short[] array) {
        short mode = 0;
        short max = 0;
        for (short i : array) {
            short count = 0;
            for (short j : array) {
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
     * @param array Array to find difference of
     * @return Difference between maximum and minimum shorts in an array
     */
    public static short maxDifference(short[] array) {
        return (short) (max(array) - min(array));
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
     * @param array Array to search
     * @return  Maximum long
     */
    public static long max(long[] array) {
        long max = Long.MIN_VALUE;
        for (long i : array) {
            if (i > max) max = i;
        }
        return max;
    }

    /**
     * Find minimum long in an array
     * @param array Array to search
     * @return Minimum long
     */
    public static long min(long[] array) {
        long min = Long.MAX_VALUE;
        for (long i : array) {
            if (i < min) min = i;
        }
        return min;
    }

    /**
     * Sum all longs in an array
     * @param array Array to sum
     * @return Sum of all longs in an array
     */
    public static long sum(long[] array) {
        long sum = 0;
        for (long i : array) {
            sum += i;
        }
        return sum;
    }

    /**
     * Find average of all longs in an array
     * @param array Array to average
     * @return Average of all longs in an array
     */
    public static long avg(long[] array) {
        return sum(array) / array.length;
    }

    /**
     * Find median of all longs in an array
     * @param array Array to find median of
     * @return Median of all longs in an array
     */
    public static long median(long[] array) {
        return array[array.length / 2];
    }

    /**
     * Find mode of all longs in an array
     * @param array Array to find mode of
     * @return Mode of all longs in an array
     */
    public static long mode(long[] array) {
        long mode = 0;
        long max = 0;
        for (long i : array) {
            long count = 0;
            for (long j : array) {
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
     * @param array Array to find difference of
     * @return Difference between maximum and minimum longs in an array
     */
    public static long maxDifference(long[] array) {
        return max(array) - min(array);
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
     * @param array Array to search
     * @return  Maximum float
     */
    public static float max(float[] array) {
        float max = Float.MIN_VALUE;
        for (float i : array) {
            if (i > max) max = i;
        }
        return max;
    }

    /**
     * Find minimum float in an array
     * @param array Array to search
     * @return Minimum float
     */
    public static float min(float[] array) {
        float min = Float.MAX_VALUE;
        for (float i : array) {
            if (i < min) min = i;
        }
        return min;
    }

    /**
     * Sum all floats in an array
     * @param array Array to sum
     * @return Sum of all floats in an array
     */
    public static float sum(float[] array) {
        float sum = 0;
        for (float i : array) {
            sum += i;
        }
        return sum;
    }

    /**
     * Find average of all floats in an array
     * @param array Array to average
     * @return Average of all floats in an array
     */
    public static float avg(float[] array) {
        return sum(array) / array.length;
    }

    /**
     * Find median of all floats in an array
     * @param array Array to find median of
     * @return Median of all floats in an array
     */
    public static float median(float[] array) {
        return array[array.length / 2];
    }

    /**
     * Find mode of all floats in an array
     * @param array Array to find mode of
     * @return Mode of all floats in an array
     */
    public static float mode(float[] array) {
        float mode = 0;
        float max = 0;
        for (float i : array) {
            float count = 0;
            for (float j : array) {
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
     * @param array Array to find difference of
     * @return Difference between maximum and minimum floats in an array
     */
    public static float maxDifference(float[] array) {
        return max(array) - min(array);
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
     * @param array Array to search
     * @return  Maximum double
     */
    public static double max(double[] array) {
        double max = Double.MIN_VALUE;
        for (double i : array) {
            if (i > max) max = i;
        }
        return max;
    }

    /**
     * Find minimum double in an array
     * @param array Array to search
     * @return Minimum double
     */
    public static double min(double[] array) {
        double min = Double.MAX_VALUE;
        for (double i : array) {
            if (i < min) min = i;
        }
        return min;
    }

    /**
     * Sum all doubles in an array
     * @param array Array to sum
     * @return Sum of all doubles in an array
     */
    public static double sum(double[] array) {
        double sum = 0;
        for (double i : array) {
            sum += i;
        }
        return sum;
    }

    /**
     * Find average of all doubles in an array
     * @param array Array to average
     * @return Average of all doubles in an array
     */
    public static double avg(double[] array) {
        return sum(array) / array.length;
    }

    /**
     * Find median of all doubles in an array
     * @param array Array to find median of
     * @return Median of all doubles in an array
     */
    public static double median(double[] array) {
        return array[array.length / 2];
    }

    /**
     * Find mode of all doubles in an array
     * @param array Array to find mode of
     * @return Mode of all doubles in an array
     */
    public static double mode(double[] array) {
        double mode = 0;
        double max = 0;
        for (double i : array) {
            double count = 0;
            for (double j : array) {
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
     * @param array Array to find difference of
     * @return Difference between maximum and minimum doubles in an array
     */
    public static double maxDifference(double[] array) {
        return max(array) - min(array);
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
     * @param array Array to search
     * @return  Maximum byte
     */
    public static byte max(byte[] array) {
        byte max = Byte.MIN_VALUE;
        for (byte i : array) {
            if (i > max) max = i;
        }
        return max;
    }

    /**
     * Find minimum byte in an array
     * @param array Array to search
     * @return Minimum byte
     */
    public static byte min(byte[] array) {
        byte min = Byte.MAX_VALUE;
        for (byte i : array) {
            if (i < min) min = i;
        }
        return min;
    }

    /**
     * Sum all bytes in an array
     * @param array Array to sum
     * @return Sum of all bytes in an array
     */
    public static byte sum(byte[] array) {
        byte sum = 0;
        for (byte i : array) {
            sum += i;
        }
        return sum;
    }

    /**
     * Find average of all bytes in an array
     * @param array Array to average
     * @return Average of all bytes in an array
     */
    public static byte avg(byte[] array) {
        return (byte) (sum(array) / array.length);
    }


    /**
     * Find median of all bytes in an array
     * @param array Array to find median of
     * @return Median of all bytes in an array
     */
    public static byte median(byte[] array) {
        return array[array.length / 2];
    }

    /**
     * Find mode of all bytes in an array
     * @param array Array to find mode of
     * @return Mode of all bytes in an array
     */
    public static byte mode(byte[] array) {
        byte mode = 0;
        byte max = 0;
        for (byte i : array) {
            byte count = 0;
            for (byte j : array) {
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
     * @param array Array to find difference of
     * @return Difference between maximum and minimum bytes in an array
     */
    public static byte maxDifference(byte[] array) {
        return (byte) (max(array) - min(array));
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
     * Find maximum object in a list, and returns the maximum object. The object has to be numeric (Integer, Long, Float, Double, Byte, Short).
     * @param list List to search
     * @return Maximum object
     */
    public static Object max(List<?> list) {
        // Check instance of first element
        if (list.isEmpty()) return 0;
        if (list.get(0) instanceof Integer) {
            return (int) max(list.stream().mapToInt(i -> (int) i).toArray());
        } else if (list.get(0) instanceof Long) {
            return (long) max(list.stream().mapToLong(i -> (long) i).toArray());
        } else if (list.get(0) instanceof Float) {
            return (float) max(list.stream().mapToDouble(i -> (float) i).toArray());
        } else if (list.get(0) instanceof Double) {
            return (double) max(list.stream().mapToDouble(i -> (double) i).toArray());
        } else if (list.get(0) instanceof Byte) {
            return (byte) max(list.stream().mapToInt(i -> (byte) i).toArray());
        } else if (list.get(0) instanceof Short) {
            return (short) max(list.stream().mapToInt(i -> (short) i).toArray());
        } else {
            throw new IllegalArgumentException("Unsupported type: " + list.get(0).getClass().getName());
        }
    }

    /**
     * Find minimum object in a list, and returns the minimum object. The object has to be numeric (Integer, Long, Float, Double, Byte, Short).
     * @param list List to search
     * @return Minimum object
     */
    public static Object min(List<?> list) {
        // Check instance of first element
        if (list.isEmpty()) return 0;
        if (list.get(0) instanceof Integer) {
            return (int) min(list.stream().mapToInt(i -> (int) i).toArray());
        } else if (list.get(0) instanceof Long) {
            return (long) min(list.stream().mapToLong(i -> (long) i).toArray());
        } else if (list.get(0) instanceof Float) {
            return (float) min(list.stream().mapToDouble(i -> (float) i).toArray());
        } else if (list.get(0) instanceof Double) {
            return (double) min(list.stream().mapToDouble(i -> (double) i).toArray());
        } else if (list.get(0) instanceof Byte) {
            return (byte) min(list.stream().mapToInt(i -> (byte) i).toArray());
        } else if (list.get(0) instanceof Short) {
            return (short) min(list.stream().mapToInt(i -> (short) i).toArray());
        } else {
            throw new IllegalArgumentException("Unsupported type: " + list.get(0).getClass().getName());
        }
    }

    /**
     * Finds sum of all objects in the list, and returns the sum as an object. The object has to be numeric (Integer, Long, Float, Double, Byte, Short).
     * @param list List to find median of
     * @return Median of all ints in a list
     */
    private static Object sum(List<?> list) {
        Object sum = 0;
        for (Object i : list) {
            if (i instanceof Integer) {
                sum = (int) sum + (int) i;
            } else if (i instanceof Long) {
                sum = (long) sum + (long) i;
            } else if (i instanceof Float) {
                sum = (float) sum + (float) i;
            } else if (i instanceof Double) {
                sum = (double) sum + (double) i;
            } else if (i instanceof Byte) {
                sum = (byte) sum + (byte) i;
            } else if (i instanceof Short) {
                sum = (short) sum + (short) i;
            } else {
                throw new IllegalArgumentException("Unsupported type: " + i.getClass().getName());
            }
        }
        return sum;
    }

    /**
     * Finds average of all objects in the list, and returns the average as an object. The object has to be numeric (Integer, Long, Float, Double, Byte, Short).
     * @param list List to average
     * @return Average of all ints in a list
     */
    public static Object avg(List<?> list) {
        // Check instance of first element
        if (list.isEmpty()) return 0;
        if (list.get(0) instanceof Integer) {
            return (int) sum(list) / list.size();
        } else if (list.get(0) instanceof Long) {
            return (long) sum(list) / list.size();
        } else if (list.get(0) instanceof Float) {
            return (float) sum(list) / list.size();
        } else if (list.get(0) instanceof Double) {
            return (double) sum(list) / list.size();
        } else if (list.get(0) instanceof Byte) {
            return (byte) sum(list) / list.size();
        } else if (list.get(0) instanceof Short) {
            return (short) sum(list) / list.size();
        } else {
            throw new IllegalArgumentException("Unsupported type: " + list.get(0).getClass().getName());
        }
    }

    /**
     * Finds median of all objects in the list, and returns the median as an object. The object has to be numeric (Integer, Long, Float, Double, Byte, Short).
     * @param list List to find median of
     * @return Median of all ints in a list
     */
    public static Object median(List<?> list) {
        // Check instance of first element
        if (list.isEmpty()) return 0;
        if (list.get(0) instanceof Integer) {
            return (int) median(list.stream().mapToInt(i -> (int) i).toArray());
        } else if (list.get(0) instanceof Long) {
            return (long) median(list.stream().mapToLong(i -> (long) i).toArray());
        } else if (list.get(0) instanceof Float) {
            return (float) median(list.stream().mapToDouble(i -> (float) i).toArray());
        } else if (list.get(0) instanceof Double) {
            return (double) median(list.stream().mapToDouble(i -> (double) i).toArray());
        } else if (list.get(0) instanceof Byte) {
            return (byte) median(list.stream().mapToInt(i -> (byte) i).toArray());
        } else if (list.get(0) instanceof Short) {
            return (short) median(list.stream().mapToInt(i -> (short) i).toArray());
        } else {
            throw new IllegalArgumentException("Unsupported type: " + list.get(0).getClass().getName());
        }
    }

    /**
     * Finds mode of all objects in the list, and returns the mode as an object. The object has to be numeric (Integer, Long, Float, Double, Byte, Short).
     * @param list List to find mode of
     * @return Mode of all ints in a list
     */
    public static Object mode(List<?> list) {
        // Check instance of first element
        if (list.isEmpty()) return 0;
        if (list.get(0) instanceof Integer) {
            return (int) mode(list.stream().mapToInt(i -> (int) i).toArray());
        } else if (list.get(0) instanceof Long) {
            return (long) mode(list.stream().mapToLong(i -> (long) i).toArray());
        } else if (list.get(0) instanceof Float) {
            return (float) mode(list.stream().mapToDouble(i -> (float) i).toArray());
        } else if (list.get(0) instanceof Double) {
            return (double) mode(list.stream().mapToDouble(i -> (double) i).toArray());
        } else if (list.get(0) instanceof Byte) {
            return (byte) mode(list.stream().mapToInt(i -> (byte) i).toArray());
        } else if (list.get(0) instanceof Short) {
            return (short) mode(list.stream().mapToInt(i -> (short) i).toArray());
        } else {
            throw new IllegalArgumentException("Unsupported type: " + list.get(0).getClass().getName());
        }
    }

    /**
     * Check if an element is in given array or list
     * @param element Element to check
     * @param array Array to check
     */
    public static <T> boolean arrayContains(T[] array, T element) {
        for (T i : array) {
            if (i.equals(element)) return true;
        }
        return false;
    }

    /**
     * Check if an element is in given array or list
     * @param element Element to check
     * @param list List to check
     */
    public static <T> boolean listContains(List<T> list, T element) {
        for (T i : list) {
            if (i.equals(element)) return true;
        }
        return false;
    }

    /**
     * If value is null, return defaultValue, else return value.
     * @param value Value to check - can be null
     * @param defaultValue Default value to return if value is null
     * @return Value if not null, else defaultValue
     * @param <T> Type of value
     */
    public static <T> T defaultValue(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }

    /**
     * Get last element of an array
     * @param array Array to get last element of
     * @param <T> Type of array
     * @return Last element of an array
     */
    public static <T> T last(T[] array) {
        return array[array.length - 1];
    }

    /**
     * Get last element of a list
     * @param list List to get last element of
     * @param <T> Type of list
     * @return Last element of a list
     */
    public static <T> T last(List<T> list) {
        return list.get(list.size() - 1);
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

    /**
     * Get a parameter from arguments. For example, if the arguments are ["-p", "1234", "-h", "localhost"], and the parameter is "-p", then "1234" will be returned.
     * @param args Arguments to search
     * @param parameter Parameter to search for
     * @return Value of parameter
     */
    public static String getParameterFromArguments(String[] args, String parameter) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(parameter) && i + 1 < args.length) {
                return args[i + 1];
            }
        }
        return null;
    }

    /**
     * Get a parameter from arguments. For example, if the arguments are ["-p", "1234", "-h", "localhost"], and the parameter is "-p", then "1234" will be returned. If the parameter is not found, then the default value will be returned.
     * @param args Arguments to search
     * @param parameter Parameter to search for
     * @param defaultValue Default value to return if parameter is not found
     * @return Value of parameter
     */
    public static String getParameterFromArguments(String[] args, String parameter, String defaultValue) {
        String value = getParameterFromArguments(args, parameter);
        return value == null ? defaultValue : value;
    }

    /**
     * Get the middle number between two numbers
     * @param start Start number
     * @param end End number
     * @return Middle number
     */
    public static int middle(int start, int end) {
        return ((end - start) / 2) + start;
    }

    /**
     * Copy a file, in binary format, from source to destination. The original file name will be used as a new file name.
     * @param source Source file
     * @param destination Destination directory
     * @throws IOException If an I/O error occurs
     */
    public static void copy(String source, String destination) throws IOException {
        File f = new File(source);
        copy(source, destination, f.getName());
    }

    /**
     * Copy a file, in binary format, from source to destination.
     * @param source Source file
     * @param destination Destination directory
     * @param fileName New file name
     * @throws IOException If an I/O error occurs
     */
    public static void copy(String source, String destination, String fileName) throws IOException {
        File sourceFile = new File(source);
        File destinationFile = new File(destination, fileName);

        try (FileInputStream fis = new FileInputStream(sourceFile);
             FileOutputStream fos = new FileOutputStream(destinationFile)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
        }
    }


    /**
     * Center a component in a parent component by calculating the middle point of the parent component and the component to be centered.
     * @param c Component to center
     * @param parentWidth Width of parent component
     * @param parentHeight Height of parent component
     * @return Centered component
     */
    public static Component center(Component c, int parentWidth, int parentHeight) {
        int midPWidth = middle(0, parentWidth);
        int midPHeight = middle(0, parentHeight);

        int midCWidth = middle(0, c.getWidth());
        int midCHeight = middle(0, c.getHeight());

        int x = midPWidth - midCWidth;
        int y = midPHeight - midCHeight;

        c.setLocation(x, y);
        return c;
    }

    /**
     * Drop special characters from a string.
     * @param original Original string
     * @param charactersToReplaceInString A string containing all characters to replace
     * @param replaceWith Replace each character with this string
     * @return String with special characters replaced
     */
    public static String dropSpecialCharacters(String original, String charactersToReplaceInString, String replaceWith) {
        String[] charactersToReplace = charactersToReplaceInString.split("");
        String newStr = "";
        for (String s : charactersToReplace) {
            newStr = original.replace(s, replaceWith);
        }
        return newStr;
    }

    /**
     * Drop special characters from a string. Default special characters are !@#$%^&*()+=`~/\][';?.,œ∑´®†¥¨ˆøπ“‘¡™£¢∞§¶•ªº–≠åß∂ƒ©˙∆˚¬…æΩ≈ç√∫˜µ≤≥µ
     * @param original Original string
     * @param replaceWith Replace each character with this string
     * @return String with special characters replaced
     */
    public static String dropSpecialCharacters(String original, String replaceWith) {
        return dropSpecialCharacters(original, "!@#$%^&*()+=`~/\\][';?.,œ∑´®†¥¨ˆøπ“‘¡™£¢∞§¶•ªº–≠åß∂ƒ©˙∆˚¬…æΩ≈ç√∫˜µ≤≥µ", replaceWith);
    }

    /**
     * Check if two arrays are equal. Order sensitive.
     * @param array1 Array 1
     * @param array2 Array 2
     * @return True if equal, else false
     */
    public static boolean arrayEquals(Object[] array1, Object[] array2) {
        // Order sensitive.
        if (array1.length != array2.length) return false;

        for (int i = 0; i < array1.length; i++) {
            if (!array1[i].equals(array2[i])) return false;
        }

        return true;
    }

    /**
     * Substitute in-line variable. For example, if value has "This is ${name}", then the variable name is "name". The given pairs has to be "name=John". The result should be "This is John".
     * @param line Line to substitute
     * @param pairs Pairs of variable and value
     */
    public static String inlineVariableSubstitutionPrefix = "${";
    public static String inlineVariableSubstitutionSuffix = "}";
    public static String inlineVariableSubstitution(String line, String ... pairs) {
        for (String kvpair : pairs) {
            try {
                String key = kvpair.split("=")[0];
                String value = kvpair.split("=")[1];
                line = line.replace(inlineVariableSubstitutionPrefix + key + inlineVariableSubstitutionSuffix, value);
            } catch (Exception e) {
                System.err.println("Invalid key-value pair: " + kvpair);
            }
        }
        return line;
    }

    /**
     * Pull variables in in-line variable string. For example, if value has "This is ${name}", then the variable name is "name". The result should be "name".
     * @param line Line to pull variables from
     * @return Array of variables
     */
    public static String[] pullVariablesFromInlineVariableSubstitution(String line) {
        List<String> variables = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\$\\{([^}]*)\\}");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            variables.add(matcher.group(1));
        }
        return variables.toArray(new String[0]);
    }

    /**
     * Simply put thread to sleep for a specified amount of time.
     * @param millis Milliseconds to sleep
     * @param silent If true, do not print stack trace if interrupted
     */
    public static void sleep(long millis, boolean silent) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            if (!silent) e.printStackTrace();
        }
    }

    /**
     * Simply put thread to sleep for a specified amount of time.
     * @param millis Milliseconds to sleep
     */
    public static void sleep(long millis) {
        sleep(millis, false);
    }

    /**
     * Generate a random string of a specified length and pattern
     * @param length Length of string
     * @param alphaLows Include lower case alphabets
     *                  (abcdefghijklmnopqrstuvwxyz)
     * @param alphaUps Include upper case alphabets
     *                  (ABCDEFGHIJKLMNOPQRSTUVWXYZ)
     * @param numbers Include numbers
     *                  (0123456789)
     * @param specialCharacters Include special characters
     *                  (!@#$%^&*()+=`~/\][';?.,)
     * @return Random string
     */
    public static String randomString(int length, boolean alphaLows, boolean alphaUps, boolean numbers, boolean specialCharacters) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random(); // Single Random instance

        String[] alphaLowsStr = "abcdefghijklmnopqrstuvwxyz".split("");
        String[] alphaUpsStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");
        String[] numbersStr = "0123456789".split("");
        String[] specialCharactersStr = "!@#$%^&*()+=`~/\\][';?.,".split("");

        // Combine all characters into one array if multiple categories are selected
        String combinedChars = "";
        if (alphaLows) combinedChars += String.join("", alphaLowsStr);
        if (alphaUps) combinedChars += String.join("", alphaUpsStr);
        if (numbers) combinedChars += String.join("", numbersStr);
        if (specialCharacters) combinedChars += String.join("", specialCharactersStr);
        String[] allChars = combinedChars.split("");

        for (int i = 0; i < length; i++) {
            sb.append(allChars[random.nextInt(allChars.length)]);
        }

        return sb.toString();
    }
}
