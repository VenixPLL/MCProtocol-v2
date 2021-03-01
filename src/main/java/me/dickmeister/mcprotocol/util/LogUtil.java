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