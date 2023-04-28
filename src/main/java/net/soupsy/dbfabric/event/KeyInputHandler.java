package net.soupsy.dbfabric.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_DRAGONBALL_FABRIC = "key.catagory.dbfabric.dbfabric";
    public static final String KEY_BOOST_ENERGY = "key.dbfabric.boost_energy";

    public static KeyBinding boostingEnergyKey;

    public static void registerKeyInputs(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(boostingEnergyKey.wasPressed()){
                //key is pressed
                client.player.sendMessage(Text.literal("BOOST"));

            }
        });
    }

    public static void register() {
        boostingEnergyKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_BOOST_ENERGY,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                KEY_CATEGORY_DRAGONBALL_FABRIC
        ));

        registerKeyInputs();
    }
}
