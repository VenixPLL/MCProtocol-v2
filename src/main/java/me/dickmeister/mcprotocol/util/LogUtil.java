package me.dickmeister.mcprotocol.util;

public final class LogUtil {


    public static final void log(final Class c,final String message,final Object... args){
        System.out.println("[" + c.getSimpleName() + "] " + format(message,args));
    }

    public static final void err(final Class c,final String message,final Object... args){
        System.err.println("[" + c.getSimpleName() + "] " + format(message,args));
    }

    public static final void log(final String message,final Object... args){
        System.out.println(format(message,args));
    }

    public static final void err(final String message,final Object... args){
        System.err.println(format(message,args));
    }

    private static final String format(final String m,final Object... a){
        return String.format(m,a);
    }
}
