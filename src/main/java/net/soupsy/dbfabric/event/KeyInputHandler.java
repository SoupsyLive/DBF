package net.soupsy.dbfabric.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.soupsy.dbfabric.networking.ModPackets;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_DRAGONBALL_FABRIC = "key.catagory.dbfabric.dbfabric";
    public static final String KEY_BOOST_ENERGY = "key.dbfabric.boost_energy";
    public static final String KEY_USE_ENERGY = "key.dbfabric.use_energy";
    public static final String KEY_POWER_UP = "key.dbfabric.power_up";

    public static KeyBinding boostingEnergyKey;
    public static KeyBinding useEnergyKey;
    public static KeyBinding powerUpKey;
    static boolean keyDebounce = false;
    static boolean ignoreInputs = false;


    public static void registerKeyInputs(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(boostingEnergyKey.wasPressed()){
                if(!keyDebounce && !ignoreInputs){
                    //client.player.sendMessage(Text.literal("BOOST"));
                    ClientPlayNetworking.send(ModPackets.BOOSTING_ENERGY_ID, PacketByteBufs.create());
                    keyDebounce = true;
                    for(int i=0; i<100;i++){
                        if(i==100-1){
                            keyDebounce = false;
                        }
                    }
                }
            }
            if(useEnergyKey.wasPressed()){
                if(!keyDebounce && !ignoreInputs){
                    keyDebounce = true;
                    ClientPlayNetworking.send(ModPackets.USE_ENERGY_ID, PacketByteBufs.create());
                    for(int i=0; i<100;i++){
                        if(i==100-1){
                            keyDebounce = false;
                        }
                    }
                }
            }
            if(powerUpKey.wasPressed()){
                if(!keyDebounce && !ignoreInputs){
                    keyDebounce = true;
                    ClientPlayNetworking.send(ModPackets.POWER_UP_ID, PacketByteBufs.create());
                    for(int i=0; i<100;i++){
                        if(i==100-1){
                            keyDebounce = false;
                        }
                    }
                }
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
        useEnergyKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_USE_ENERGY,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_K,
                KEY_CATEGORY_DRAGONBALL_FABRIC
        ));
        powerUpKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_POWER_UP,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                KEY_CATEGORY_DRAGONBALL_FABRIC
        ));

        registerKeyInputs();
    }
}
