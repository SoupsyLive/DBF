package net.soupsy.dbfabric.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.soupsy.dbfabric.DragonBallFabric;
import net.soupsy.dbfabric.networking.packet.*;

public class ModPackets {
    public static final Identifier BOOSTING_POWER_ID = new Identifier(DragonBallFabric.MOD_ID, "boosting");
    public static final Identifier ENERGY_SYNC_ID = new Identifier(DragonBallFabric.MOD_ID, "energy-sync");


    public static final Identifier USE_ENERGY_ID = new Identifier(DragonBallFabric.MOD_ID, "spend-energy");
    public static final Identifier POWER_UP_ID = new Identifier(DragonBallFabric.MOD_ID, "power-up");

    public static final Identifier POWER_UP_SYNC_ID = new Identifier(DragonBallFabric.MOD_ID, "power-up-sync");

    public static final Identifier EXAMPLE_ID = new Identifier(DragonBallFabric.MOD_ID, "example");


    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(EXAMPLE_ID, ExampleC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(BOOSTING_POWER_ID, BoostC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(USE_ENERGY_ID, UseEnergyC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(POWER_UP_ID, PowerUpC2SPacket::receive);

    }
    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(ENERGY_SYNC_ID, EnergySyncDataS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(POWER_UP_SYNC_ID, PowerupSyncDataS2CPacket::receive);

    }


}
