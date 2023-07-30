package net.soupsy.dbfabric.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.UuidArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.soupsy.dbfabric.client.TestScreen;
import net.soupsy.dbfabric.playerStorage.PlayerStatsStorage;
import net.soupsy.dbfabric.util.ArgNumbCheckUtil;
import net.soupsy.dbfabric.util.IEntityDataSaver;
import net.soupsy.dbfabric.util.PowerData;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;

public class PowerCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("power").requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.argument("target", EntityArgumentType.players())
                        .then(CommandManager.argument("new power",  IntegerArgumentType.integer(1))
                                        .executes(PowerCommand::run))
        ));
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Integer newPower = IntegerArgumentType.getInteger(context, "new power");
        Collection<ServerPlayerEntity> targetPlayers = EntityArgumentType.getPlayers(context, "target");
        ArrayList<ServerPlayerEntity> targetPlayersArray = new ArrayList<>(targetPlayers);


        for(int i=0; i < targetPlayers.size(); i++){

            ServerPlayerEntity targetPlayer = targetPlayersArray.get(i);
            IEntityDataSaver player = (IEntityDataSaver) targetPlayer;
            int oldPower = PowerData.getPower(player);
            PowerData.setPower(player, newPower);
            PlayerStatsStorage.setMaxPower(context.getSource().getPlayer().getUuid(), (long) newPower);

            DecimalFormat df = new DecimalFormat("#,###");
            String oldPowerCommas = df.format(oldPower);
            String newPowerCommas = df.format(newPower);
            if(context.getSource().getPlayer() != targetPlayer){
                context.getSource().sendMessage(Text.literal("§e"+targetPlayer.getDisplayName().getString()+"Power: §c§l"+oldPowerCommas+"§r§f -> §a§l"+newPowerCommas+"§r§e."));
            }
            context.getSource().sendMessage(Text.literal("§ePower: §c§l"+oldPowerCommas+"§r§f -> §a§l"+newPowerCommas+"§r§e."));
            //context.getSource().sendFeedback((Supplier<Text>) Text.literal("§ePower: §c§l"+oldPowerCommas+"§r§f -> §a§l"+newPowerCommas+"§r§e."), true);
        }
        return 1;


    }

}
