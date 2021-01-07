package me.dickmeister.mcprotocol.minecraft.world.vec;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Unix
 * @since 20.08.2020
 */

public class Vector implements Cloneable
{
    private static final long serialVersionUID = -2657651106777219169L;
    private static Random random = new Random();
    private static final double epsilon = 1.0E-6D;
    protected double x;
    protected double y;
    protected double z;

    public Vector() {
        this.x = 0.0D;
        this.y = 0.0D;
        this.z = 0.0D;
    }

    public Vector(int x, int y, int z) {
        this.x = (double)x;
        this.y = (double)y;
        this.z = (double)z;
    }

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector(float x, float y, float z) {
        this.x = (double)x;
        this.y = (double)y;
        this.z = (double)z;
    }

    public Vector add(Vector vec) {
        this.x += vec.x;
        this.y += vec.y;
        this.z += vec.z;
        return this;
    }

    public Vector subtract(Vector vec) {
        this.x -= vec.x;
        this.y -= vec.y;
        this.z -= vec.z;
        return this;
    }

    public Vector multiply(Vector vec) {
        this.x *= vec.x;
        this.y *= vec.y;
        this.z *= vec.z;
        return this;
    }

    public Vector divide(Vector vec) {
        this.x /= vec.x;
        this.y /= vec.y;
        this.z /= vec.z;
        return this;
    }

    public Vector copy(Vector vec) {
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
        return this;
    }

    public double length() {
        return Math.sqrt(NumberConversions.square(this.x) + NumberConversions.square(this.y) + NumberConversions.square(this.z));
    }

    public double lengthSquared() {
        return NumberConversions.square(this.x) + NumberConversions.square(this.y) + NumberConversions.square(this.z);
    }

    public double distance(Vector o) {
        return Math.sqrt(NumberConversions.square(this.x - o.x) + NumberConversions.square(this.y - o.y) + NumberConversions.square(this.z - o.z));
    }

    public double distanceSquared(Vector o) {
        return NumberConversions.square(this.x - o.x) + NumberConversions.square(this.y - o.y) + NumberConversions.square(this.z - o.z);
    }

    public float angle(Vector other) {
        double dot = this.dot(other) / (this.length() * other.length());
        return (float)Math.acos(dot);
    }

    public Vector midpoint(Vector other) {
        this.x = (this.x + other.x) / 2.0D;
        this.y = (this.y + other.y) / 2.0D;
        this.z = (this.z + other.z) / 2.0D;
        return this;
    }

    public Vector getMidpoint(Vector other) {
        double x = (this.x + other.x) / 2.0D;
        double y = (this.y + other.y) / 2.0D;
        double z = (this.z + other.z) / 2.0D;
        return new Vector(x, y, z);
    }

    public Vector multiply(int m) {
        this.x *= (double)m;
        this.y *= (double)m;
        this.z *= (double)m;
        return this;
    }

    public Vector multiply(double m) {
        this.x *= m;
        this.y *= m;
        this.z *= m;
        return this;
    }

    public Vector multiply(float m) {
        this.x *= (double)m;
        this.y *= (double)m;
        this.z *= (double)m;
        return this;
    }

    public double dot(Vector other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    public Vector crossProduct(Vector o) {
        double newX = this.y * o.z - o.y * this.z;
        double newY = this.z * o.x - o.z * this.x;
        double newZ = this.x * o.y - o.x * this.y;
        this.x = newX;
        this.y = newY;
        this.z = newZ;
        return this;
    }

    public Vector getCrossProduct(Vector o) {
        double x = this.y * o.z - o.y * this.z;
        double y = this.z * o.x - o.z * this.x;
        double z = this.x * o.y - o.x * this.y;
        return new Vector(x, y, z);
    }

    public Vector normalize() {
        double length = this.length();
        this.x /= length;
        this.y /= length;
        this.z /= length;
        return this;
    }

    public Vector zero() {
        this.x = 0.0D;
        this.y = 0.0D;
        this.z = 0.0D;
        return this;
    }

    public boolean isInAABB(Vector min, Vector max) {
        return this.x >= min.x && this.x <= max.x && this.y >= min.y && this.y <= max.y && this.z >= min.z && this.z <= max.z;
    }

    public boolean isInSphere(Vector origin, double radius) {
        return NumberConversions.square(origin.x - this.x) + NumberConversions.square(origin.y - this.y) + NumberConversions.square(origin.z - this.z) <= NumberConversions.square(radius);
    }

    public double getX() {
        return this.x;
    }

    public int getBlockX() {
        return NumberConversions.floor(this.x);
    }

    public double getY() {
        return this.y;
    }

    public int getBlockY() {
        return NumberConversions.floor(this.y);
    }

    public double getZ() {
        return this.z;
    }

    public int getBlockZ() {
        return NumberConversions.floor(this.z);
    }

    public Vector setX(int x) {
        this.x = (double)x;
        return this;
    }

    public Vector setX(double x) {
        this.x = x;
        return this;
    }

    public Vector setX(float x) {
        this.x = (double)x;
        return this;
    }

    public Vector setY(int y) {
        this.y = (double)y;
        return this;
    }

    public Vector setY(double y) {
        this.y = y;
        return this;
    }

    public Vector setY(float y) {
        this.y = (double)y;
        return this;
    }

    public Vector setZ(int z) {
        this.z = (double)z;
        return this;
    }

    public Vector setZ(double z) {
        this.z = z;
        return this;
    }

    public Vector setZ(float z) {
        this.z = (double)z;
        return this;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Vector)) {
            return false;
        } else {
            Vector other = (Vector)obj;
            return Math.abs(this.x - other.x) < 1.0E-6D && Math.abs(this.y - other.y) < 1.0E-6D && Math.abs(this.z - other.z) < 1.0E-6D && this.getClass().equals(obj.getClass());
        }
    }

    public int hashCode() {
        int hash = 79 * 7 + (int)(Double.doubleToLongBits(this.x) ^ Double.doubleToLongBits(this.x) >>> 32);
        hash = 79 * hash + (int)(Double.doubleToLongBits(this.y) ^ Double.doubleToLongBits(this.y) >>> 32);
        hash = 79 * hash + (int)(Double.doubleToLongBits(this.z) ^ Double.doubleToLongBits(this.z) >>> 32);
        return hash;
    }

    public Vector clone() {
        try {
            return (Vector)super.clone();
        } catch (CloneNotSupportedException var2) {
            throw new Error(var2);
        }
    }

    public String toString() {
        return this.x + "," + this.y + "," + this.z;
    }

    public Position toPosition() {
        return new Position(this.x, this.y, this.z);
    }

    public Position toLocation(float yaw, float pitch) {
        return new Position(this.x, this.y, this.z, yaw, pitch);
    }

//    public BlockVector toBlockVector() {
//        return new BlockVector(this.x, this.y, this.z);
//    }

    public static double getEpsilon() {
        return 1.0E-6D;
    }

    public static Vector getMinimum(Vector v1, Vector v2) {
        return new Vector(Math.min(v1.x, v2.x), Math.min(v1.y, v2.y), Math.min(v1.z, v2.z));
    }

    public static Vector getMaximum(Vector v1, Vector v2) {
        return new Vector(Math.max(v1.x, v2.x), Math.max(v1.y, v2.y), Math.max(v1.z, v2.z));
    }

    public static Vector getRandom() {
        return new Vector(random.nextDouble(), random.nextDouble(), random.nextDouble());
    }

    public Map<String, Double> serialize() {
        Map<String, Double> result = new LinkedHashMap<>();
        result.put("x", this.getX());
        result.put("y", this.getY());
        result.put("z", this.getZ());
        return result;
    }

    public static Vector deserialize(Map<String, Double> args) {
        double x = 0.0D;
        double y = 0.0D;
        double z = 0.0D;
        if (args.containsKey("x")) {
            x = args.get("x");
        }

        if (args.containsKey("y")) {
            y = args.get("y");
        }

        if (args.containsKey("z")) {
            z = args.get("z");
        }

        return new Vector(x, y, z);
    }
}
