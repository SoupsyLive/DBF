package net.soupsy.dbfabric.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.soupsy.dbfabric.DragonBallFabric;
import net.soupsy.dbfabric.block.DbfBlocks;

public class DbfItemGroups {
    public static ItemGroup DRAGON_BALL_FABRIC = Registry.register(Registries.ITEM_GROUP, new Identifier(DragonBallFabric.MOD_ID, "dragon-ball-fabric"),
            FabricItemGroup.builder()
                    .displayName(Text.translatable("itemgroup.dbf"))
                    .icon(() -> new ItemStack(DbfItems.ORIGIN_ALIGNER))
                    .entries((displayContext, entries) -> {
                        entries.add(DbfItems.ORIGIN_ALIGNER);
                        entries.add(DbfItems.SENSU_BEAN);

                        //entries.add(DbfBlocks.DRAGON_BALL_BLOCK);
                    })
                    .build());
    public static void registerItemGroups(){
        DragonBallFabric.LOGGER.info("Registering Item Groups for "+DragonBallFabric.MOD_ID);

        /*
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(DbfItems.SENSU_BEAN);
        });
         */
    }
}
