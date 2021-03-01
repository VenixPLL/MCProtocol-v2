package me.dickmeister.mcprotocol.minecraft.block;

import com.google.common.collect.ImmutableMap;
import me.dickmeister.mcprotocol.minecraft.property.IProperty;

import java.util.Collection;

/**
 * @author Unix
 * @since 10.01.2021
 */

public interface IBlockState extends IBlockBehaviors
{
    Collection< IProperty<? >> getPropertyNames();

    <T extends Comparable<T>> T getValue(IProperty<T> property);

    <T extends Comparable<T>, V extends T> IBlockState withProperty(IProperty<T> property, V value);

    <T extends Comparable<T>> IBlockState cycleProperty(IProperty<T> property);

    ImmutableMap< IProperty<?>, Comparable<? >> getProperties();

    Block getBlock();
}