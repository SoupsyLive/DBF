package net.soupsy.dbfabric.util;

import net.minecraft.nbt.NbtCompound;

public interface IEntityDataSaver {
    NbtCompound getPresistentData();

    NbtCompound getPersistentData();
}
