package net.soupsy.dbfabric.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.soupsy.dbfabric.DragonBallFabric;

public class DbfBlocks {
    //public static final Block DRAGON_BALL_BLOCK = registerBlock("dragon_ball_block",
    //        new Block(FabricBlockSettings.copyOf(Blocks.STONE).strength(1f)));

    private static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(DragonBallFabric.MOD_ID, name), block);
    }
    private static Item registerBlockItem(String name, Block block) {

        Item item = Registry.register(Registries.ITEM, new Identifier(DragonBallFabric.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
        //ItemGroupEvents.modifyEntriesEvent(tab).register(entries -> entries.add(block));

        return Registry.register(Registries.ITEM, new Identifier(DragonBallFabric.MOD_ID, name), new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerDbfBlocks(){
        DragonBallFabric.LOGGER.debug("Registering DbfBlocks class for"+DragonBallFabric.MOD_ID);
    }
}
