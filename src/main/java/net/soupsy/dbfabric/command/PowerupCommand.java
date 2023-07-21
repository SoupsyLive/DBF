package net.soupsy.dbfabric.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.soupsy.dbfabric.playerStorage.PlayerStatsStorage;
import net.soupsy.dbfabric.playerStorage.PowerUp;
import net.soupsy.dbfabric.util.ArgNumbCheckUtil;
import net.soupsy.dbfabric.util.IEntityDataSaver;
import net.soupsy.dbfabric.util.PlayerStorage;
import net.soupsy.dbfabric.util.PowerData;

import java.text.DecimalFormat;
import java.util.*;

public class PowerupCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("technique").requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.argument("action", StringArgumentType.word())
                                .suggests((context, builder) -> {
                                    builder.suggest("select");
                                    builder.suggest("unlock");
                                    builder.suggest("remove");
                                    return builder.buildFuture();
                                })
                                    .then(CommandManager.argument("item", StringArgumentType.word())
                                        .suggests((context, builder) -> {
                                            List<PowerUp> powerups = Arrays.asList(PowerUp.values());
                                            for(int i=0; i < powerups.size(); i++){
                                                builder.suggest(powerups.get(i).getId());
                                            }
                                            builder.suggest("none");
                                            return builder.buildFuture();
                                        })
                                                    .then(CommandManager.argument("target",  EntityArgumentType.players())
                                                            .executes(PowerupCommand::run)
                                    )
                                .executes(PowerupCommand::run)
                                )
                ));
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        // Get values
        String action = StringArgumentType.getString(context, "action");
        PowerUp powerup = PowerUp.getPowerupFromId(StringArgumentType.getString(context, "item"));
        Collection<ServerPlayerEntity> targetPlayers = EntityArgumentType.getPlayers(context, "target");
        ArrayList<ServerPlayerEntity> targetPlayersArray = new ArrayList<>(targetPlayers);


        for(int i=0; i < targetPlayers.size(); i++){
            ServerPlayerEntity targetPlayer = targetPlayersArray.get(i);
            UUID playerUuid = targetPlayer.getUuid();

            // Make sure powerup exist
            if(powerup == null && !Objects.equals(StringArgumentType.getString(context, "item"), "none")){
                return 0;
            }

            if(Objects.equals(action, "select")){
            /*
            if(!powerupList.contains(powerup)){
                PlayerStatsStorage.addUnlockedPowerup(playerUuid ,powerup);
            }
            */

                if(Objects.equals(StringArgumentType.getString(context, "item"), "none")){
                    PlayerStorage.setSelectedPowerup(playerUuid, null);
                    if(context.getSource().getPlayer() != targetPlayer){
                        context.getSource().sendMessage(Text.literal("Deselected §e"+targetPlayer.getDisplayName().getString()+"'s §rtechnique."));
                    }
                    targetPlayer.sendMessage(Text.literal("Deselected technique."));

                }

                if(context.getSource().getPlayer() != targetPlayer){
                    context.getSource().sendMessage(Text.literal("Selected §e"+targetPlayer.getDisplayName().getString()+"'s §rtechnique to §a§l"+powerup.getName()+"§r§f."));
                }
                PlayerStorage.setSelectedPowerup(playerUuid, powerup);
                targetPlayer.sendMessage(Text.literal("Selected: §a§l"+powerup.getName()+"§r§f technique."));


            }
            else if(Objects.equals(action, "unlock")){
                PlayerStatsStorage.addUnlockedPowerup(playerUuid ,powerup);
                if(context.getSource().getPlayer() != targetPlayer){
                    context.getSource().sendMessage(Text.literal("Unlocked §e"+targetPlayer.getDisplayName().getString()+"'s §a§l"+powerup.getName()+"§r§f technique."));
                }
                targetPlayer.sendMessage(Text.literal("Unlocked: §a§l"+powerup.getName()+"§r§f technique."));

            }
            else if(Objects.equals(action, "remove")){
                PlayerStatsStorage.removeUnlockedPowerup(playerUuid, powerup);
                if(context.getSource().getPlayer() != targetPlayer){
                    context.getSource().sendMessage(Text.literal("Removed §e"+targetPlayer.getDisplayName().getString()+"'s §a§l"+powerup.getName()+"§r§f as an available technique."));
                }
                context.getSource().sendMessage(Text.literal("Removed: §c§l"+powerup.getName()+"§r§f from available techniques."));

            }
            else{
                context.getSource().sendMessage(Text.literal("§cSomething went wrong with choosing your action, try again."));

            }
        }
        return 1;
    }
}
