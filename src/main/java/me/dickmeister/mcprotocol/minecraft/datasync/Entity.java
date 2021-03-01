package me.dickmeister.mcprotocol.minecraft.datasync;

import lombok.Data;
import me.dickmeister.mcprotocol.minecraft.item.ItemStack;
import me.dickmeister.mcprotocol.minecraft.world.vec.MathHelper;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author Unix
 * @since 10.01.2021
 */

@Data
public class Entity {

    protected static final DataParameter<Byte> FLAGS = EntityDataManager.createKey(Entity.class, DataSerializers.BYTE);
    private static final DataParameter<Integer> AIR = EntityDataManager.createKey(Entity.class, DataSerializers.VARINT);
    private static final DataParameter<String> CUSTOM_NAME = EntityDataManager.createKey(Entity.class, DataSerializers.STRING);
    private static final DataParameter<Boolean> CUSTOM_NAME_VISIBLE = EntityDataManager.createKey(Entity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SILENT = EntityDataManager.createKey(Entity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> NO_GRAVITY = EntityDataManager.createKey(Entity.class, DataSerializers.BOOLEAN);

    protected static final DataParameter<Byte> HAND_STATES = EntityDataManager.createKey(Entity.class, DataSerializers.BYTE);
    private static final DataParameter<Float> HEALTH = EntityDataManager.createKey(Entity.class, DataSerializers.FLOAT);
    private static final DataParameter<Integer> POTION_EFFECTS = EntityDataManager.createKey(Entity.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> HIDE_PARTICLES = EntityDataManager.createKey(Entity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> ARROW_COUNT_IN_ENTITY = EntityDataManager.createKey(Entity.class, DataSerializers.VARINT);

    private static final DataParameter<Float> ABSORPTION = EntityDataManager.createKey(Entity.class, DataSerializers.FLOAT);
    private static final DataParameter<Integer> PLAYER_SCORE = EntityDataManager.createKey(Entity.class, DataSerializers.VARINT);
    protected static final DataParameter<Byte> PLAYER_MODEL_FLAG = EntityDataManager.createKey(Entity.class, DataSerializers.BYTE);
    protected static final DataParameter<Byte> MAIN_HAND = EntityDataManager.createKey(Entity.class, DataSerializers.BYTE);
    protected static final DataParameter<NBTTagCompound> field_192032_bt = EntityDataManager.createKey(Entity.class, DataSerializers.field_192734_n);
    protected static final DataParameter<NBTTagCompound> field_192033_bu = EntityDataManager.createKey(Entity.class, DataSerializers.field_192734_n);

    public static final DataParameter<ItemStack> ITEM = EntityDataManager.createKey(Entity.class, DataSerializers.OPTIONAL_ITEM_STACK);

    protected final EntityDataManager dataManager = new EntityDataManager(this);

    private boolean glowing, onGround;

    public void init() {
        this.dataManager.register(FLAGS, (byte) 0);
        this.dataManager.register(AIR, 300);
        this.dataManager.register(CUSTOM_NAME_VISIBLE, Boolean.FALSE);
        this.dataManager.register(CUSTOM_NAME, "");
        this.dataManager.register(SILENT, Boolean.FALSE);
        this.dataManager.register(NO_GRAVITY, Boolean.FALSE);
        this.dataManager.register(HAND_STATES, (byte) 0);
        this.dataManager.register(POTION_EFFECTS, 0);
        this.dataManager.register(HIDE_PARTICLES, Boolean.FALSE);
        this.dataManager.register(ARROW_COUNT_IN_ENTITY, 0);
        this.dataManager.register(HEALTH, 1.0F);
        this.dataManager.register(ABSORPTION, 0.0F);
        this.dataManager.register(PLAYER_SCORE, 0);
        this.dataManager.register(PLAYER_MODEL_FLAG, (byte) 0);
        this.dataManager.register(MAIN_HAND, (byte) 1);
        this.dataManager.register(field_192032_bt, new NBTTagCompound());
        this.dataManager.register(field_192033_bu, new NBTTagCompound());
        this.dataManager.register(ITEM, new ItemStack(0));
    }

    public void notifyDataManagerChange(DataParameter<?> key) {
    }

    protected boolean getFlag(int flag) {
        return (dataManager.get(FLAGS) & 1 << flag) != 0;
    }

    /**
     * Enable or disable a entity flag, see getEntityFlag to read the know flags.
     */
    protected void setFlag(int flag, boolean set) {
        var b0 = this.dataManager.get(FLAGS);
        dataManager.set(FLAGS, set ? (byte) (b0 | 1 << flag) : (byte) (b0 & ~(1 << flag)));
    }

    /**
     * Returns if this entity is sneaking.
     */
    public boolean isSneaking() {
        return this.getFlag(1);
    }

    /**
     * Sets the sneaking flag.
     */
    public void setSneaking(boolean sneaking) {
        this.setFlag(1, sneaking);
    }

    public boolean isGlowing() {
        return glowing && this.getFlag(6);
    }

    public void setGlowing(boolean glowingIn) {
        glowing = glowingIn;
        this.setFlag(6, glowing);
    }

    public final float getHealth() {
        return this.dataManager.get(HEALTH);
    }

    public void setHealth(float health) {
        this.dataManager.set(HEALTH, MathHelper.clamp_float(health, 0.0F, 20.0F));
    }

    /**
     * Get if the Entity is sprinting.
     */
    public boolean isSprinting() {
        return this.getFlag(3);
    }

    /**
     * Set sprinting switch for Entity.
     */
    public void setSprinting(boolean sprinting) {
        this.setFlag(3, sprinting);
    }

    public void setElytraFlying() {
        this.setFlag(7, true);
    }

    public void clearElytraFlying() {
        this.setFlag(7, true);
        this.setFlag(7, false);
    }

    public boolean isElytraFlying() {
        return this.getFlag(7);
    }

    public void setItemInHand(ItemStack itemStack) {
        dataManager.set(ITEM, itemStack);
    }
}