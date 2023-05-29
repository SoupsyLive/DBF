package net.soupsy.dbfabric.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.soupsy.dbfabric.util.ArgNumbCheckUtil;
import net.soupsy.dbfabric.util.IEntityDataSaver;
import net.soupsy.dbfabric.util.PowerData;

import java.text.DecimalFormat;

public class PowerCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("power").requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.argument("new power", StringArgumentType.word())
                                .suggests((context, builder) -> {
                                    builder.suggest("1");
                                    return builder.buildFuture();
                                })
                                        .executes(PowerCommand::run)
                                )


        );
    }

    private static int run(CommandContext<ServerCommandSource> context) {
        String newPower = StringArgumentType.getString(context, "new power");
        IEntityDataSaver player = (IEntityDataSaver) context.getSource().getPlayer();
        int oldPower = PowerData.getPower(player);
        if(ArgNumbCheckUtil.isInt(newPower)){
            int newPowerInt = Integer.parseInt(newPower);
            PowerData.setPower(player, newPowerInt);

            DecimalFormat df = new DecimalFormat("#,###");
            String oldPowerCommas = df.format(oldPower);
            String newPowerCommas = df.format(newPowerInt);

            context.getSource().sendFeedback(Text.literal("§ePower: §c§l"+oldPowerCommas+"§r§f -> §a§l"+newPowerCommas+"§r§e."), true);
            return 1;
        }else{
            context.getSource().sendFeedback(Text.literal("§c/power <number>"), false);
            return 1;
        }
    }
}
