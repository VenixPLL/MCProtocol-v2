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

package me.dickmeister.mcprotocol.minecraft.datasync;

import com.google.common.base.Optional;
import me.dickmeister.mcprotocol.minecraft.BlockPos;
import me.dickmeister.mcprotocol.minecraft.EnumFacing;
import me.dickmeister.mcprotocol.minecraft.Rotations;
import me.dickmeister.mcprotocol.minecraft.block.Block;
import me.dickmeister.mcprotocol.minecraft.block.IBlockState;
import me.dickmeister.mcprotocol.minecraft.item.ItemStack;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.util.IntIdentityHashBiMap;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.UUID;

public class DataSerializers
{
    private static final IntIdentityHashBiMap< DataSerializer<? >> REGISTRY = new IntIdentityHashBiMap < DataSerializer<? >> (16);
    public static final DataSerializer<Byte> BYTE = new DataSerializer<Byte>()
    {
        public void write(PacketBuffer buf, Byte value)
        {
            buf.writeByte(value.byteValue());
        }
        public Byte read(PacketBuffer buf) throws IOException
        {
            return buf.readByte();
        }
        public DataParameter<Byte> createKey(int id)
        {
            return new DataParameter<Byte>(id, this);
        }
        public Byte func_192717_a(Byte p_192717_1_)
        {
            return p_192717_1_;
        }
    };
    public static final DataSerializer<Integer> VARINT = new DataSerializer<Integer>()
    {
        public void write(PacketBuffer buf, Integer value)
        {
            buf.writeVarIntToBuffer(value.intValue());
        }
        public Integer read(PacketBuffer buf) throws IOException
        {
            return buf.readVarIntFromBuffer();
        }
        public DataParameter<Integer> createKey(int id)
        {
            return new DataParameter<Integer>(id, this);
        }
        public Integer func_192717_a(Integer p_192717_1_)
        {
            return p_192717_1_;
        }
    };
    public static final DataSerializer<Float> FLOAT = new DataSerializer<Float>()
    {
        public void write(PacketBuffer buf, Float value)
        {
            buf.writeFloat(value.floatValue());
        }
        public Float read(PacketBuffer buf) throws IOException
        {
            return buf.readFloat();
        }
        public DataParameter<Float> createKey(int id)
        {
            return new DataParameter<Float>(id, this);
        }
        public Float func_192717_a(Float p_192717_1_)
        {
            return p_192717_1_;
        }
    };
    public static final DataSerializer<String> STRING = new DataSerializer<String>()
    {
        public void write(PacketBuffer buf, String value)
        {
            buf.writeString(value);
        }
        public String read(PacketBuffer buf) throws IOException
        {
            return buf.readStringFromBuffer(32767);
        }
        public DataParameter<String> createKey(int id)
        {
            return new DataParameter<String>(id, this);
        }
        public String func_192717_a(String p_192717_1_)
        {
            return p_192717_1_;
        }
    };
    public static final DataSerializer<TextComponent> TEXT_COMPONENT = new DataSerializer<TextComponent>()
    {
        public void write(PacketBuffer buf, TextComponent value)
        {
            buf.writeTextComponent(value);
        }
        public TextComponent read(PacketBuffer buf) throws IOException
        {
            return buf.readTextComponent();
        }
        public DataParameter<TextComponent> createKey(int id)
        {
            return new DataParameter<TextComponent>(id, this);
        }
        public TextComponent func_192717_a(TextComponent p_192717_1_)
        {
            return new TextComponent(p_192717_1_.duplicate());
        }
    };
    public static final DataSerializer<ItemStack> OPTIONAL_ITEM_STACK = new DataSerializer<ItemStack>()
    {
        public void write(PacketBuffer buf, ItemStack value)
        {
            buf.writeItemStackToBuffer(value);
        }
        public ItemStack read(PacketBuffer buf) throws IOException
        {
            return buf.readItemStackFromBuffer();
        }
        public DataParameter<ItemStack> createKey(int id)
        {
            return new DataParameter<ItemStack>(id, this);
        }
        public ItemStack func_192717_a(ItemStack p_192717_1_)
        {
            return p_192717_1_.copy();
        }
    };
    public static final DataSerializer<Optional<IBlockState>> OPTIONAL_BLOCK_STATE = new DataSerializer<Optional<IBlockState>>()
    {
        public void write(PacketBuffer buf, Optional<IBlockState> value)
        {
            if (value.isPresent())
            {
                buf.writeVarIntToBuffer(Block.getStateId(value.get()));
            }
            else
            {
                buf.writeVarIntToBuffer(0);
            }
        }
        public Optional<IBlockState> read(PacketBuffer buf) throws IOException
        {
            int i = buf.readVarIntFromBuffer();
            return i == 0 ? Optional.absent() : Optional.of(Block.getStateById(i));
        }
        public DataParameter<Optional<IBlockState>> createKey(int id)
        {
            return new DataParameter<Optional<IBlockState>>(id, this);
        }
        public Optional<IBlockState> func_192717_a(Optional<IBlockState> p_192717_1_)
        {
            return p_192717_1_;
        }
    };
    public static final DataSerializer<Boolean> BOOLEAN = new DataSerializer<Boolean>()
    {
        public void write(PacketBuffer buf, Boolean value)
        {
            buf.writeBoolean(value.booleanValue());
        }
        public Boolean read(PacketBuffer buf) throws IOException
        {
            return buf.readBoolean();
        }
        public DataParameter<Boolean> createKey(int id)
        {
            return new DataParameter<Boolean>(id, this);
        }
        public Boolean func_192717_a(Boolean p_192717_1_)
        {
            return p_192717_1_;
        }
    };
    public static final DataSerializer<Rotations> ROTATIONS = new DataSerializer<Rotations>()
    {
        public void write(PacketBuffer buf, Rotations value)
        {
            buf.writeFloat(value.getX());
            buf.writeFloat(value.getY());
            buf.writeFloat(value.getZ());
        }
        public Rotations read(PacketBuffer buf) throws IOException
        {
            return new Rotations(buf.readFloat(), buf.readFloat(), buf.readFloat());
        }
        public DataParameter<Rotations> createKey(int id)
        {
            return new DataParameter<Rotations>(id, this);
        }
        public Rotations func_192717_a(Rotations p_192717_1_)
        {
            return p_192717_1_;
        }
    };
    public static final DataSerializer<BlockPos> BLOCK_POS = new DataSerializer<BlockPos>()
    {
        public void write(PacketBuffer buf, BlockPos value)
        {
            buf.writeBlockPos(value);
        }
        public BlockPos read(PacketBuffer buf) throws IOException
        {
            return buf.readBlockPos();
        }
        public DataParameter<BlockPos> createKey(int id)
        {
            return new DataParameter<BlockPos>(id, this);
        }
        public BlockPos func_192717_a(BlockPos p_192717_1_)
        {
            return p_192717_1_;
        }
    };
    public static final DataSerializer<Optional<BlockPos>> OPTIONAL_BLOCK_POS = new DataSerializer<Optional<BlockPos>>()
    {
        public void write(PacketBuffer buf, Optional<BlockPos> value)
        {
            buf.writeBoolean(value.isPresent());

            if (value.isPresent())
            {
                buf.writeBlockPos(value.get());
            }
        }
        public Optional<BlockPos> read(PacketBuffer buf) throws IOException
        {
            return !buf.readBoolean() ? Optional.absent() : Optional.of(buf.readBlockPos());
        }
        public DataParameter<Optional<BlockPos>> createKey(int id)
        {
            return new DataParameter<Optional<BlockPos>>(id, this);
        }
        public Optional<BlockPos> func_192717_a(Optional<BlockPos> p_192717_1_)
        {
            return p_192717_1_;
        }
    };
    public static final DataSerializer<EnumFacing> FACING = new DataSerializer<EnumFacing>()
    {
        public void write(PacketBuffer buf, EnumFacing value)
        {
            buf.writeEnumValue(value);
        }
        public EnumFacing read(PacketBuffer buf) throws IOException
        {
            return (EnumFacing)buf.readEnumValue(EnumFacing.class);
        }
        public DataParameter<EnumFacing> createKey(int id)
        {
            return new DataParameter<EnumFacing>(id, this);
        }
        public EnumFacing func_192717_a(EnumFacing p_192717_1_)
        {
            return p_192717_1_;
        }
    };
    public static final DataSerializer<Optional<UUID>> OPTIONAL_UNIQUE_ID = new DataSerializer<Optional<UUID>>()
    {
        public void write(PacketBuffer buf, Optional<UUID> value)
        {
            buf.writeBoolean(value.isPresent());

            if (value.isPresent())
            {
                buf.writeUuid(value.get());
            }
        }
        public Optional<UUID> read(PacketBuffer buf) throws IOException
        {
            return !buf.readBoolean() ? Optional.absent() : Optional.of(buf.readUuid());
        }
        public DataParameter<Optional<UUID>> createKey(int id)
        {
            return new DataParameter<Optional<UUID>>(id, this);
        }
        public Optional<UUID> func_192717_a(Optional<UUID> p_192717_1_)
        {
            return p_192717_1_;
        }
    };
    public static final DataSerializer<NBTTagCompound> field_192734_n = new DataSerializer<NBTTagCompound>()
    {
        public void write(PacketBuffer buf, NBTTagCompound value)
        {
            buf.writeNBTTagCompoundToBuffer(value);
        }
        public NBTTagCompound read(PacketBuffer buf) throws IOException
        {
            return buf.readNBTTagCompoundFromBuffer();
        }
        public DataParameter<NBTTagCompound> createKey(int id)
        {
            return new DataParameter<NBTTagCompound>(id, this);
        }
        public NBTTagCompound func_192717_a(NBTTagCompound p_192717_1_)
        {
            return (NBTTagCompound) p_192717_1_.copy();
        }
    };

    public static void registerSerializer(DataSerializer<?> serializer)
    {
        REGISTRY.add(serializer);
    }

    @Nullable
    public static DataSerializer<?> getSerializer(int id)
    {
        return (DataSerializer)REGISTRY.get(id);
    }

    public static int getSerializerId(DataSerializer<?> serializer)
    {
        return REGISTRY.getId(serializer);
    }

    static
    {
        registerSerializer(BYTE);
        registerSerializer(VARINT);
        registerSerializer(FLOAT);
        registerSerializer(STRING);
        registerSerializer(TEXT_COMPONENT);
        registerSerializer(OPTIONAL_ITEM_STACK);
        registerSerializer(BOOLEAN);
        registerSerializer(ROTATIONS);
        registerSerializer(BLOCK_POS);
        registerSerializer(OPTIONAL_BLOCK_POS);
        registerSerializer(FACING);
        registerSerializer(OPTIONAL_UNIQUE_ID);
        registerSerializer(OPTIONAL_BLOCK_STATE);
        registerSerializer(field_192734_n);
    }
}
