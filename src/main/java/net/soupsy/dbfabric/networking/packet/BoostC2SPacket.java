package net.soupsy.dbfabric.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
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
import net.soupsy.dbfabric.util.IEntityDataSaver;
import net.soupsy.dbfabric.util.PowerData;

import java.text.Normalizer;

public class BoostC2SPacket {
    //private static final String MESSAGE_BOOST_POWER = "message.dbfabric.boost_power";
    //private static final String MESSAGE_REMOVE_POWER = "message.dbfabric.remove_power";

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        int powerChange = 2;
        ServerWorld world = player.getWorld();
        //When the server gets this packet do this :
        BlockPos playerPos = player.getBlockPos().add(0, 1, 0);
        //EntityType.FIREBALL.spawn((ServerWorld) player.world, playerPos, SpawnReason.TRIGGERED);
        world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_ENDER_DRAGON_GROWL, SoundCategory.PLAYERS,
                0.1F, world.random.nextFloat() * 0.1F + 0.9F);
        //add power level
        PowerData.addPower(((IEntityDataSaver) player), powerChange);
        // output new power level
        player.sendMessage(Text.literal(""+((IEntityDataSaver) player).getPersistentData().getInt("power"))
                .fillStyle(Style.EMPTY.withColor(Formatting.GOLD).withBold(true)), true);

        // sync power
        PowerData.syncPower(((IEntityDataSaver) player).getPersistentData().getInt("power"), player);
    }
}
