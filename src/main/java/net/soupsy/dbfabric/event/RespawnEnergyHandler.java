package net.soupsy.dbfabric.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.soupsy.dbfabric.DragonBallFabric;
import net.soupsy.dbfabric.util.EnergyData;
import net.soupsy.dbfabric.util.IEntityDataSaver;

public class RespawnEnergyHandler implements ServerPlayerEvents.AfterRespawn {
    @Override
    public void afterRespawn(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        EnergyData.addEnergy((IEntityDataSaver) newPlayer, 100);
        DragonBallFabric.LOGGER.debug("Tried to give new Energy");
    }
}
