package net.soupsy.dbfabric.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.soupsy.dbfabric.util.EnergyData;
import net.soupsy.dbfabric.util.IEntityDataSaver;
import net.soupsy.dbfabric.util.PlayerStorage;
import net.soupsy.dbfabric.util.PowerData;

public class PowerUpC2SPacket {

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        PlayerStorage.togglePowerup(player.getUuid());
        if(PlayerStorage.isPowerupActive("Powered-Players", player.getUuid())){
            IEntityDataSaver playerData = (IEntityDataSaver) player;
            EnergyData.removeEnergy(playerData, 7);
            if(EnergyData.getEnergy(playerData) < 2){
                player.kill();
                PlayerStorage.togglePowerup(player.getUuid());
            }
            PowerData.addPower(playerData, PowerData.getPower(playerData));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, -1, 1, true, false));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, -1, 2, true, false));
            ServerWorld world = player.getWorld();
            world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_PARROT_IMITATE_ENDER_DRAGON, SoundCategory.PLAYERS,
                    0.4F, world.random.nextFloat() * 0.1F + 0.9F);
        }else{
            IEntityDataSaver playerData = (IEntityDataSaver) player;
            PowerData.removePower(playerData, (PowerData.getPower(playerData)/2));
            player.removeStatusEffect(StatusEffects.GLOWING);
            player.removeStatusEffect(StatusEffects.SPEED);

            ServerWorld world = player.getWorld();
            world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_ENDER_DRAGON_FLAP, SoundCategory.PLAYERS,
                    0.3F, world.random.nextFloat() * 0.1F + 0.9F);
        }

    }
}
