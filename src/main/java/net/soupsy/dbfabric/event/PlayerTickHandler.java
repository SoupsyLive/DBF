package net.soupsy.dbfabric.event;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.soupsy.dbfabric.networking.ModPackets;
import net.soupsy.dbfabric.util.EnergyData;
import net.soupsy.dbfabric.util.IEntityDataSaver;
import net.soupsy.dbfabric.util.PlayerStorage;
import net.soupsy.dbfabric.util.PowerData;

public class PlayerTickHandler implements ServerTickEvents.StartTick {
    @Override
    public void onStartTick(MinecraftServer server) {
        int tickNumb = server.getTicks();
        if(tickNumb % 10 == 0){
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                if(!PlayerStorage.isPowerupActive("Powered-Players", player.getUuid())){
                    IEntityDataSaver dataPlayer = ((IEntityDataSaver) player);
                    EnergyData.addEnergy(dataPlayer, 7);
                }else{
                    IEntityDataSaver dataPlayer = ((IEntityDataSaver) player);
                    if(EnergyData.getEnergy(dataPlayer) > 7){
                        EnergyData.removeEnergy(dataPlayer, 7);
                    }else{
                        ClientPlayNetworking.send(ModPackets.POWER_UP_ID, PacketByteBufs.create());
                        player.damage(player.getDamageSources().playerAttack(player), 15.0f);
                    }
                }
            }
        }



    }
}
