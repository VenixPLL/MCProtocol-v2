package me.dickmeister.mcprotocol.logging;

import java.util.ArrayList;
import java.util.List;

public class SimpleErrorReporting {

    private final List<String> entries = new ArrayList<>();

    private String header_name;
    private String header_error;

    public SimpleErrorReporting add(final String entry) {
        this.entries.add(entry);
        return this;
    }

    public SimpleErrorReporting header(final String name, final String error) {
        this.header_name = name;
        this.header_error = error;
        return this;
    }

    public SimpleErrorReporting print() {
        System.out.println("Error report:");
        System.out.println("  " + this.header_name);
        System.out.println("  " + this.header_error);
        entries.forEach(e -> System.out.println("     " + e));

        return this;
    }

}
