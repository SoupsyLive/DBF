package net.soupsy.dbfabric;

import net.fabricmc.api.ClientModInitializer;
import net.soupsy.dbfabric.event.KeyInputHandler;
import net.soupsy.dbfabric.item.DbfItems;

public class DragonBallFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        DbfItems.registerModItems();

        KeyInputHandler.register();
    }
}
