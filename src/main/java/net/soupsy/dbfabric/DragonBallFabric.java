package net.soupsy.dbfabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.text.Text;
import net.soupsy.dbfabric.command.PowerCommand;
import net.soupsy.dbfabric.event.DeathEvents;
import net.soupsy.dbfabric.event.ModPlayerEventCopyFrom;
import net.soupsy.dbfabric.event.PlayerTickHandler;
import net.soupsy.dbfabric.event.RespawnEnergyHandler;
import net.soupsy.dbfabric.networking.ModPackets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;
import static net.minecraft.server.command.CommandManager.*;

public class DragonBallFabric implements ModInitializer {
	public static final String MOD_ID = "dbfabric";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModPackets.registerC2SPackets();
		//LOGGER.info("Hello Fabric world!");
		ServerTickEvents.START_SERVER_TICK.register(new PlayerTickHandler());
		ServerLivingEntityEvents.ALLOW_DEATH.register(new DeathEvents());
		ServerPlayerEvents.COPY_FROM.register(new ModPlayerEventCopyFrom());

		//CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("foo")
				//.executes(context -> {
					// For versions below 1.19, replace "Text.literal" with "new LiteralText".
					//context.getSource().sendMessage(Text.literal("Called /foo with no arguments"));
					//return 1;
				//})));
	}
}
