package net.soupsy.dbfabric.util;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.soupsy.dbfabric.networking.ModPackets;

public class EnergyData {
    public static int addEnergy(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        if(!nbt.getBoolean("drainpower")){
            int energy = nbt.getInt("energy");
            if(energy+amount <= 100){
                energy += amount;
            }else{
                energy = 100;
            }
            nbt.putInt("energy", energy);

            // sync data
            syncEnergy(energy, (ServerPlayerEntity) player);
            return energy;
        }

        return nbt.getInt("energy");
    }

    public static int removeEnergy(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int energy = nbt.getInt("energy");
        if(energy-amount > 0){
            energy -= amount;
        }else{
            energy = 0;
        }

        nbt.putInt("energy", energy);
        // sync data
        syncEnergy(energy, (ServerPlayerEntity) player);
        return energy;
    }
    public static int getEnergy(IEntityDataSaver player){
        return player.getPersistentData().getInt("energy");
    }
    public static void syncEnergy(int energy, ServerPlayerEntity player){
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(energy);
        ServerPlayNetworking.send(player, ModPackets.ENERGY_SYNC_ID, buffer);
    }


}


