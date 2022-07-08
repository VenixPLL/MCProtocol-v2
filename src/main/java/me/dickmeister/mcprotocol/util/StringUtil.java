/*
 * MCProtocol-v2
 * Copyright (C) 2022.  VenixPLL
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.dickmeister.mcprotocol.util;

import java.util.regex.Pattern;

/**
 * Skidded from Minecraft Client (Probably)
 */
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
