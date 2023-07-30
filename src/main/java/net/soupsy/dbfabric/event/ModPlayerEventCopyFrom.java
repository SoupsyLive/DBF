package net.soupsy.dbfabric.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.soupsy.dbfabric.util.IEntityDataSaver;
import net.soupsy.dbfabric.util.PowerData;

public class ModPlayerEventCopyFrom implements ServerPlayerEvents.CopyFrom{
    @Override
    public void copyFromPlayer(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        IEntityDataSaver original = ((IEntityDataSaver) oldPlayer);
        IEntityDataSaver player = ((IEntityDataSaver) newPlayer);

        player.getPersistentData().putInt("power", PowerData.getPower(original)/2);
        player.getPersistentData().putInt("energy", 100);

    }
}
