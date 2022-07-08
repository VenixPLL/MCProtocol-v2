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
