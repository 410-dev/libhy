package me.hy.libhycore;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Simple date and time functions.
 */
public class CoreDate {

    private static ZoneOffset zoneOffset = ZoneOffset.UTC;

    /**
     * This generates a timestamp with the given format.
     * @param format The format of the timestamp. See {@link DateTimeFormatter} for more information.
     * @return The timestamp string.
     */
    public static @NotNull String timestamp(String format) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime currentTime = LocalDateTime.now();
        OffsetDateTime odt = currentTime.atOffset(zoneOffset);
        return timeFormatter.format(odt);
    }


    /**
     * This sets the zone offset for the timestamp function.
     * @param zoneOffset The zone offset to use.
     */
    public static void setZoneOffset(ZoneOffset zoneOffset) {
        CoreDate.zoneOffset = zoneOffset;
    }

    /**
     * This sets the zone offset for the timestamp function to the system's default.
     */
    public static void useSystemZoneOffset() {
        CoreDate.zoneOffset = ZoneOffset.systemDefault().getRules().getOffset(LocalDateTime.now());
    }

    /**
     * This generates a timestamp with the format "yyyy-MM-dd HH:mm:ss".
     * @return The timestamp string.
     */
    public static @NotNull String timestamp() {
        return timestamp("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * @return The current year in 2 digits.
     */
    public static @NotNull String year2Digits() {
        return timestamp("yyyy").substring(2);
    }

    /**
     * @return The current year in 4 digits.
     */
    public static @NotNull String year4Digits() {
        return timestamp("yyyy");
    }

    /**
     * @return The current month in 2 digits.
     */
    public static @NotNull String month() {
        return timestamp("MM");
    }

    /**
     * @return The current day in 2 digits.
     */
    public static @NotNull String date() {
        return timestamp("dd");
    }

    /**
     * @return The current hour in 24-hour format.
     */
    public static @NotNull String hour24() {
        return timestamp("HH");
    }

    /**
     * @return The current hour in 12-hour format.
     */
    public static @NotNull String hour12() {
        int integerForm = Integer.parseInt(hour24());
        if (integerForm > 12) return (integerForm - 12) + "";
        return integerForm + "";
    }

    /**
     * @return The current AM/PM in 12-hour format.
     */
    public static @NotNull String ampm() {
        if (Integer.parseInt(hour24()) >= 12) return "PM";
        return "AM";
    }

    /**
     * @return Current minute in 2 digits.
     */
    public static @NotNull String minute() {
        return timestamp("mm");
    }

    /**
     * @return Current second in 2 digits.
     */
    public static @NotNull String second() {
        return timestamp("ss");
    }

    /**
     * @return Current seconds since 1970.
     */
    public static long secondsSince1970() {
        return mSecSince1970() / 1000;
    }

    /**
     * @param offsetSec Seconds to offset from current time.
     * @return Seconds since 1970 + offset.
     */
    public static long secondsSince1970(long offsetSec) {
        return secondsSince1970() + offsetSec;
    }

    /**
     * @return Current milliseconds since 1970.
     */
    public static long mSecSince1970() {
        return System.currentTimeMillis();
    }

    /**
     * @param offsetMsec Milliseconds to offset from current time.
     * @return Milliseconds since 1970 + offset.
     */
    public static long mSecSince1970(long offsetMsec) {
        return System.currentTimeMillis() + offsetMsec;
    }

    /**
     * @return Current time offset.
     */
    public static @NotNull String currentTimeOffset() {
        return timestamp("O");
    }

    /**
     * @deprecated
     * Do not use this constructor. It does not have any functionality.
     */
    @Deprecated
    public CoreDate() {}
}