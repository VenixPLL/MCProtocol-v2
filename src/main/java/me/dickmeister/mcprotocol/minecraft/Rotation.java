package me.dickmeister.mcprotocol.minecraft;

/**
 * @author Unix
 * @since 19.08.2020
 */
public class Rotation {
    /** Rotation on the X axis */
    protected final float x;

    /** Rotation on the Y axis */
    protected final float y;

    /** Rotation on the Z axis */
    protected final float z;

    public Rotation(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public boolean equals(Object p_equals_1_)
    {
        if (!(p_equals_1_ instanceof Rotation)) {
            return false;
        }

        Rotation rotations = (Rotation)p_equals_1_;
        return this.x == rotations.x && this.y == rotations.y && this.z == rotations.z;
    }

    /**
     * Gets the X axis rotation
     */
    public float getX() {
        return this.x;
    }

    /**
     * Gets the Y axis rotation
     */
    public float getY() {
        return this.y;
    }

    /**
     * Gets the Z axis rotation
     */
    public float getZ() {
        return this.z;
    }
}
