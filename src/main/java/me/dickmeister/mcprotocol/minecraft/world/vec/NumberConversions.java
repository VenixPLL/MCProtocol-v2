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

package me.dickmeister.mcprotocol.minecraft.world.vec;

/**
 * @author Unix
 * @since 20.08.2020
 */

public final class NumberConversions {
    private NumberConversions() {
    }

    public static int floor(double num) {
        int floor = (int) num;
        return (double) floor == num ? floor : floor - (int) (Double.doubleToRawLongBits(num) >>> 63);
    }

    public static int ceil(double num) {
        int floor = (int) num;
        return (double) floor == num ? floor : floor + (int) (~Double.doubleToRawLongBits(num) >>> 63);
    }

    public static int round(double num) {
        return floor(num + 0.5D);
    }

    public static double square(double num) {
        return num * num;
    }

    public static int toInt(Object object) {
        if (object instanceof Number) {
            return ((Number) object).intValue();
        } else {
            try {
                return Integer.parseInt(object.toString());
            } catch (NumberFormatException | NullPointerException ignored) {
            }

            return 0;
        }
    }

    public static float toFloat(Object object) {
        if (object instanceof Number) {
            return ((Number) object).floatValue();
        } else {
            try {
                return Float.parseFloat(object.toString());
            } catch (NumberFormatException | NullPointerException ignored) {
            }

            return 0.0F;
        }
    }

    public static double toDouble(Object object) {
        if (object instanceof Number) {
            return ((Number) object).doubleValue();
        } else {
            try {
                return Double.parseDouble(object.toString());
            } catch (NumberFormatException | NullPointerException ignored) {
            }

            return 0.0D;
        }
    }

    public static long toLong(Object object) {
        if (object instanceof Number) {
            return ((Number) object).longValue();
        } else {
            try {
                return Long.parseLong(object.toString());
            } catch (NumberFormatException | NullPointerException ignored) {
            }

            return 0L;
        }
    }

    public static short toShort(Object object) {
        if (object instanceof Number) {
            return ((Number) object).shortValue();
        } else {
            try {
                return Short.parseShort(object.toString());
            } catch (NumberFormatException | NullPointerException ignored) {
            }

            return 0;
        }
    }

    public static byte toByte(Object object) {
        if (object instanceof Number) {
            return ((Number) object).byteValue();
        } else {
            try {
                return Byte.parseByte(object.toString());
            } catch (NumberFormatException | NullPointerException ignored) {
            }

            return 0;
        }
    }

    public static boolean isFinite(double d) {
        return Math.abs(d) <= Double.MAX_VALUE;
    }

    public static boolean isFinite(float f) {
        return Math.abs(f) <= Float.MAX_VALUE;
    }

    public static void checkFinite(double d, String message) {
        if (!isFinite(d)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkFinite(float d, String message) {
        if (!isFinite(d)) {
            throw new IllegalArgumentException(message);
        }
    }
}
