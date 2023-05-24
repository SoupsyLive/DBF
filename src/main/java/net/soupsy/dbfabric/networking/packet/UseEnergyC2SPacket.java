package net.soupsy.dbfabric.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.soupsy.dbfabric.util.EnergyData;
import net.soupsy.dbfabric.util.IEntityDataSaver;
import net.soupsy.dbfabric.util.PowerData;

public class UseEnergyC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        //if(((IEntityDataSaver) MinecraftClient.getInstance().player).getPersistentData().getInt("power") > 2){
        if(((IEntityDataSaver) player).getPersistentData().getInt("energy") > 2){


            int energyChange = 2;
            ServerWorld world = player.getWorld();
            //When the server gets this packet do this :
            BlockPos playerPos = player.getBlockPos().add(0, 1, 0);
            EntityType.FIREBALL.spawn((ServerWorld) player.world, playerPos, SpawnReason.TRIGGERED);
            world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_ENDER_DRAGON_HURT, SoundCategory.PLAYERS,
                    0.4F, world.random.nextFloat() * 0.1F + 0.9F);
            //add power level
            EnergyData.removeEnergy(((IEntityDataSaver) player), energyChange);
            // output new power level
            player.sendMessage(Text.literal(""+((IEntityDataSaver) player).getPersistentData().getInt("energy"))
                    .fillStyle(Style.EMPTY.withColor(Formatting.DARK_RED).withBold(true)), true);
        }else{
            player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.PLAYERS,
                    0.3F, player.getWorld().random.nextFloat() * 0.1F + 0.9F);

        }


        // sync power
        EnergyData.syncEnergy(((IEntityDataSaver) player).getPersistentData().getInt("energy"), player);
    }
}
