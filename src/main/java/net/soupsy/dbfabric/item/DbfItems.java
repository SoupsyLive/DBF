package net.soupsy.dbfabric.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.soupsy.dbfabric.DragonBallFabric;
import net.soupsy.dbfabric.item.custom.RaceSelectorItem;

public class DbfItems {
    public static final Item ORIGIN_ALIGNER = registerItem("origin_aligner",
            new RaceSelectorItem(new Item.Settings().maxCount(16)));
    public static final Item SENSU_BEAN = registerItem("sensu_bean",
            new Item(new Item.Settings().maxCount(16).food(DbfFoodComponents.SENSU_BEAN)));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(DragonBallFabric.MOD_ID, name), item);
    }
    public static void registerModItems() {
        DragonBallFabric.LOGGER.debug("Registering "+DragonBallFabric.MOD_ID+" items.");
        //ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(DbfItems::addItemsToDbTabItemGroup);
    }
}
