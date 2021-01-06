package me.dickmeister.mcprotocol.util;

import java.util.regex.Pattern;

public final class StringUtil {
    private static final Pattern patternControlCode = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");

    private StringUtil() {
    }

    /**
     * Returns the time elapsed for the given number of ticks, in "mm:ss" format.
     */
    public static String ticksToElapsedTime(int value) {
        int var1 = value / 20;
        int var2 = var1 / 60;
        var1 %= 60;
        return var1 < 10 ? var2 + ":0" + var1 : var2 + ":" + var1;
    }

    public static String stripControlCodes(String string) {
        return patternControlCode.matcher(string).replaceAll("");
    }

    public static String fixColor(String input) {
        return input.replace("&", "§");
    }

    /**
     * Returns a value indicating whether the given string is null or empty.
     */
    public static boolean isNullOrEmpty(String string) {
        return string.isEmpty();
    }
}
