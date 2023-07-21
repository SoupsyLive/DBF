package net.soupsy.dbfabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.soupsy.dbfabric.block.DbfBlocks;
import net.soupsy.dbfabric.command.PowerCommand;
import net.soupsy.dbfabric.command.PowerupCommand;
import net.soupsy.dbfabric.event.DeathEvents;
import net.soupsy.dbfabric.event.ModPlayerEventCopyFrom;
import net.soupsy.dbfabric.event.PlayerTickHandler;
import net.soupsy.dbfabric.item.DbfItemGroups;
import net.soupsy.dbfabric.item.DbfItems;
import net.soupsy.dbfabric.networking.ModPackets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DragonBallFabric implements ModInitializer {
	public static final String MOD_ID = "dbfabric";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		DbfItemGroups.registerItemGroups();
		ModPackets.registerC2SPackets();
		DbfItems.registerModItems();
		DbfBlocks.registerDbfBlocks();

		//LOGGER.info("Hello Fabric world!");
		// events
		ServerTickEvents.START_SERVER_TICK.register(new PlayerTickHandler());
		ServerLivingEntityEvents.ALLOW_DEATH.register(new DeathEvents());
		ServerPlayerEvents.COPY_FROM.register(new ModPlayerEventCopyFrom());

		// commands
		CommandRegistrationCallback.EVENT.register(PowerCommand::register);
		CommandRegistrationCallback.EVENT.register(PowerupCommand::register);

	}
}
