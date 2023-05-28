package net.soupsy.dbfabric.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.soupsy.dbfabric.util.ArgNumbCheckUtil;
import net.soupsy.dbfabric.util.IEntityDataSaver;
import net.soupsy.dbfabric.util.PowerData;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;
import static com.mojang.brigadier.builder.RequiredArgumentBuilder.argument;

public class PowerCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("power")
                        .requires(source -> source.hasPermissionLevel(2))
                        .then(argument("power", greedyString()))
                        .executes(ctx -> run(ctx.getSource(), getString(ctx, "power"))));
                //.then(CommandManager.literal("set").executes(PowerCommand::run)));
    }

    public static int run(ServerCommandSource context, String power){
        //final ServerCommandSource source = context.
        IEntityDataSaver player = (IEntityDataSaver)context;
        int oldPower = PowerData.getPower(player);

        if(ArgNumbCheckUtil.isInt(power)){
            int intPower = Integer.parseInt(power);

            PowerData.setPower(player, intPower);
            context.sendFeedback(Text.literal((context.getDisplayName()+": "+ oldPower+" -> "+intPower+" power.")).fillStyle(Style.EMPTY.withColor(Formatting.GOLD).withBold(true)), true);
            return 1;
        }

        return 0;
    }
}
