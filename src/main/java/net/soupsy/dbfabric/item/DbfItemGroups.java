package net.soupsy.dbfabric.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.soupsy.dbfabric.DragonBallFabric;

public class DbfItemGroups {
    public static final ItemGroup DRAGON_BALL_FABRIC = FabricItemGroup.builder(new Identifier(DragonBallFabric.MOD_ID))
            .displayName(Text.literal("Dragon Ball Fabric"))
            .icon(() -> new ItemStack(DbfItems.ORIGIN_ALIGNER))
            .build();
}
