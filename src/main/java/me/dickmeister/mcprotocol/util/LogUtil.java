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

public final class LogUtil {

    public LogUtil() {
    }

    public static void log(Class<?> clazz, String message, Object... obj) {
        log("[%s] " + message, clazz.getSimpleName(), obj);
    }

    public static void err(Class<?> clazz, String message, Object... obj) {
        err("[%s] " + message, clazz.getSimpleName(), obj);
    }

    public static void log(String message, Object... obj) {
        System.out.printf((message) + "%n", obj);
    }

    public static void err(String message, Object... obj) {
        System.err.printf((message) + "%n", obj);
    }

    public static String format(String in, Object... obj) {
        return String.format(in,obj);
    }
}