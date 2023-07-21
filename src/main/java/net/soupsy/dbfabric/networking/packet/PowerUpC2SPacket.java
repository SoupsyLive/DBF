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
import net.minecraft.text.Text;
import net.soupsy.dbfabric.networking.ModPackets;
import net.soupsy.dbfabric.playerStorage.PowerUp;
import net.soupsy.dbfabric.util.EnergyData;
import net.soupsy.dbfabric.util.IEntityDataSaver;
import net.soupsy.dbfabric.util.PlayerStorage;
import net.soupsy.dbfabric.util.PowerData;

public class PowerUpC2SPacket {

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        // Check if already powered up
        if(!PlayerStorage.isPowerupActive("Powered-Players", player.getUuid())){

            //Check to make sure they have a selected power-up
            if(PlayerStorage.getSelectedPowerup(player.getUuid()) == null){
                player.sendMessage(Text.literal("You have not selected any techniques"), true);
                return;
            }

            // Get player NBT
            IEntityDataSaver playerData = (IEntityDataSaver) player;

            // Get player's picked powerup
            PowerUp selectedPowerup = PlayerStorage.getSelectedPowerup(player.getUuid());

            //Special Kaio-Ken fail check
            if(PlayerStorage.getSelectedPowerup(player.getUuid()) == PowerUp.KAIOKEN){
                if(EnergyData.getEnergy(playerData) < PowerUp.KAIOKEN.getEnergyCost()){
                    //PlayerStorage.togglePowerup(player.getUuid());
                    player.kill();
                    player.sendMessage(Text.literal("§4Your body gave out from stress."));
                    return;
                }
            }
            else{


                // Check if they have enough energy
                if(EnergyData.getEnergy(playerData) < selectedPowerup.getEnergyCost()){
                    player.sendMessage(Text.literal("§c§lYou cannot muster the energy to transform."), true);
                    return;
                }
            }

            EnergyData.removeEnergy(playerData, selectedPowerup.getEnergyCost());

            // They have enough energy, transform them & send to client
            PlayerStorage.togglePowerup(player.getUuid());


            // Increase Power
            PowerData.setPower(playerData, (PowerData.getPower(playerData))*selectedPowerup.getPowerMultiplier());

            // Visual Representation (Glowing for now until you can figure out a similar system)
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, -1, 1, true, false));

            // Power-up SFX
            ServerWorld world = (ServerWorld) player.getWorld();
            world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_PARROT_IMITATE_ENDER_DRAGON, SoundCategory.PLAYERS,
                    0.4F, world.random.nextFloat() * 0.1F + 0.9F);

            PacketByteBuf buffer = PacketByteBufs.create();
            buffer.writeBoolean(true);
            ServerPlayNetworking.send(player, ModPackets.POWER_UP_SYNC_ID, buffer);

        }else{
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //                       DISABLE   POWERUP            DISABLE   POWERUP            DISABLE   POWERUP                        //
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            // Get player NBT
            IEntityDataSaver playerData = (IEntityDataSaver) player;

            // Send packet to player to disable powerup
            PlayerStorage.togglePowerup(player.getUuid());
            PacketByteBuf buffer = PacketByteBufs.create();
            buffer.writeBoolean(false);
            ServerPlayNetworking.send(player, ModPackets.POWER_UP_SYNC_ID, buffer);

            // Get player's picked powerup
            PowerUp selectedPowerup = PlayerStorage.getSelectedPowerup(player.getUuid());

            // Undo power increase
            PowerData.setPower(playerData, (PowerData.getPower(playerData)) / selectedPowerup.getPowerMultiplier());

            // Undo visual representation
            player.removeStatusEffect(StatusEffects.GLOWING);
            //player.removeStatusEffect(StatusEffects.SPEED);

            // Power-down SFX
            ServerWorld world = (ServerWorld) player.getWorld();
            world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_ENDER_DRAGON_FLAP, SoundCategory.PLAYERS,
                    0.3F, world.random.nextFloat() * 0.1F + 0.9F);
        }
    }
}
