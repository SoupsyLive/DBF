package net.soupsy.dbfabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.soupsy.dbfabric.client.EnergyMeterOverlay;
import net.soupsy.dbfabric.event.KeyInputHandler;
import net.soupsy.dbfabric.networking.ModPackets;

public class DragonBallFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        //DbfItems.registerModItems();
        //DbfBlocks.registerDbfBlocks();

        KeyInputHandler.register();
        ModPackets.registerS2CPackets();

        HudRenderCallback.EVENT.register(new EnergyMeterOverlay());
    }
}
