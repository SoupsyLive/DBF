package net.soupsy.dbfabric.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.soupsy.dbfabric.DragonBallFabric;
import net.soupsy.dbfabric.networking.packet.BoostC2SPacket;
import net.soupsy.dbfabric.networking.packet.ExampleC2SPacket;

public class ModPackets {
    public static final Identifier BOOSTING_ENERGY_ID = new Identifier(DragonBallFabric.MOD_ID, "boosting");
    public static final Identifier BOOSTING_SYNC_ID = new Identifier(DragonBallFabric.MOD_ID, "boost-sync");
    public static final Identifier EXAMPLE_ID = new Identifier(DragonBallFabric.MOD_ID, "example");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(EXAMPLE_ID, ExampleC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(BOOSTING_ENERGY_ID, BoostC2SPacket::receive);
    }
    public static void registerS2CPackets() {

    }


}
