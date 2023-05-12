package net.soupsy.dbfabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.soupsy.dbfabric.event.PlayerTickHandler;
import net.soupsy.dbfabric.networking.ModPackets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DragonBallFabric implements ModInitializer {
	public static final String MOD_ID = "dbfabric";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModPackets.registerC2SPackets();
		//LOGGER.info("Hello Fabric world!");
		ServerTickEvents.START_SERVER_TICK.register(new PlayerTickHandler());
	}
}
