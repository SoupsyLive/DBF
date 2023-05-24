package net.soupsy.dbfabric.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.soupsy.dbfabric.util.IEntityDataSaver;

public class EnergySyncDataS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        //When the server gets this packet do this :
        ((IEntityDataSaver) client.player).getPersistentData().putInt("energy", buf.readInt());
    }
}
