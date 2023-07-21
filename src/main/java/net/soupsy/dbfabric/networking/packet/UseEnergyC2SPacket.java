package net.soupsy.dbfabric.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
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
import net.soupsy.dbfabric.playerStorage.PlayerStatsStorage;
import net.soupsy.dbfabric.util.EnergyData;
import net.soupsy.dbfabric.util.IEntityDataSaver;
import net.soupsy.dbfabric.util.PowerData;

public class UseEnergyC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        // energy cost
        int energyChange = 7;

        if(((IEntityDataSaver) player).getPersistentData().getInt("energy") > energyChange){
            // player pos
            ServerWorld world = (ServerWorld) player.getWorld();
            BlockPos playerPos = player.getBlockPos().add(0, 1, 0);

            // summon fireball, no NBT
            EntityType.FIREBALL.spawn((ServerWorld) player.getWorld(), playerPos, SpawnReason.TRIGGERED);
            world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_ENDER_DRAGON_HURT, SoundCategory.PLAYERS,
                    0.4F, world.random.nextFloat() * 0.1F + 0.9F);

            // spend energy
            EnergyData.removeEnergy(((IEntityDataSaver) player), energyChange);

            // sync power
            EnergyData.syncEnergy(((IEntityDataSaver) player).getPersistentData().getInt("energy"), player);
        }else{
            // fail sfx
            player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.PLAYERS,
                    0.3F, player.getWorld().random.nextFloat() * 0.1F + 0.9F);
        }
    }
}
