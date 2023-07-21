package net.soupsy.dbfabric.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.soupsy.dbfabric.networking.ModPackets;
import net.soupsy.dbfabric.util.EnergyData;
import net.soupsy.dbfabric.util.IEntityDataSaver;
import net.soupsy.dbfabric.util.PlayerStorage;
import net.soupsy.dbfabric.util.PowerData;

public class PowerUpC2SPacketOLD {

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        PlayerStorage.togglePowerup(player.getUuid());
        if(PlayerStorage.isPowerupActive("Powered-Players", player.getUuid())){

            PacketByteBuf buffer = PacketByteBufs.create();
            buffer.writeBoolean(true);
            ServerPlayNetworking.send(player, ModPackets.POWER_UP_SYNC_ID, buffer);


            IEntityDataSaver playerData = (IEntityDataSaver) player;
            EnergyData.removeEnergy(playerData, 33);
            if(EnergyData.getEnergy(playerData) < 5){
                player.kill();
                PlayerStorage.togglePowerup(player.getUuid());
                return;
            }
            PowerData.addPower(playerData, PowerData.getPower(playerData));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, -1, 1, true, false));
            //player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, -1, 2, true, false));
            ServerWorld world = (ServerWorld) player.getWorld();
            world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_PARROT_IMITATE_ENDER_DRAGON, SoundCategory.PLAYERS,
                    0.4F, world.random.nextFloat() * 0.1F + 0.9F);
            player.getCameraEntity().offsetZ(500.2);
        }else{
            IEntityDataSaver playerData = (IEntityDataSaver) player;

            PacketByteBuf buffer = PacketByteBufs.create();
            buffer.writeBoolean(false);
            ServerPlayNetworking.send(player, ModPackets.POWER_UP_SYNC_ID, buffer);

            PowerData.removePower(playerData, (PowerData.getPower(playerData)/2));
            player.removeStatusEffect(StatusEffects.GLOWING);
            player.removeStatusEffect(StatusEffects.SPEED);

            ServerWorld world = (ServerWorld) player.getWorld();
            world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_ENDER_DRAGON_FLAP, SoundCategory.PLAYERS,
                    0.3F, world.random.nextFloat() * 0.1F + 0.9F);

        }

    }
}
