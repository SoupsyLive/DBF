package net.soupsy.dbfabric.util;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.soupsy.dbfabric.networking.ModPackets;

public class PowerData {
    public static int addPower(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        if(!nbt.getBoolean("drainpower")){
            int power = nbt.getInt("power");
            if(power+amount <= 20){
                power += amount;
            }else{
                power = 20;
            }
            nbt.putInt("power", power);

            // sync data
            syncPower(power, (ServerPlayerEntity) player);
            return power;
        }

        return nbt.getInt("power");
    }

    public static int removePower(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int power = nbt.getInt("power");
        if(power-amount > 1){
            power -= amount;
        }else{
            power = 1;
        }

        nbt.putInt("power", power);
        // sync data
        syncPower(power, (ServerPlayerEntity) player);
        return power;
    }
    public static int getPower(IEntityDataSaver player){
        return player.getPersistentData().getInt("power");
    }
    public static void syncPower(int power, ServerPlayerEntity player){
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(power);
        ServerPlayNetworking.send(player, ModPackets.ENERGY_SYNC_ID, buffer);
    }


}


