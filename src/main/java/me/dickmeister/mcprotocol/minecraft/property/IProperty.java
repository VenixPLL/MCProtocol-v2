package me.dickmeister.mcprotocol.minecraft.property;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Unix
 * @since 10.01.2021
 */

public interface IProperty<T extends Comparable<T>>
{
    String getName();

    Collection<T> getAllowedValues();

    Class<T> getValueClass();

    Optional<T> parseValue(String value);

    /**
     * Get the name for the given value.
     */
    String getName(T value);
}