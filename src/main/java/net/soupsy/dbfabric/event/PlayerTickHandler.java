package net.soupsy.dbfabric.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.soupsy.dbfabric.util.IEntityDataSaver;
import net.soupsy.dbfabric.util.PlayerStorage;
import net.soupsy.dbfabric.util.PowerData;

public class PlayerTickHandler implements ServerTickEvents.StartTick {
    @Override
    public void onStartTick(MinecraftServer server) {
        int tickNumb = server.getTicks();
        if(tickNumb % 20 == 0){
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                if(!PlayerStorage.isPowerupActive("Powered-Players", player.getUuid())){
                    IEntityDataSaver dataPlayer = ((IEntityDataSaver) player);
                    PowerData.addPower(dataPlayer, 2);
                }else{
                    IEntityDataSaver dataPlayer = ((IEntityDataSaver) player);
                    if(PowerData.getPower(dataPlayer) > 2){
                        PowerData.removePower(dataPlayer, 2);
                    }else{
                        PlayerStorage.togglePowerup(player.getUuid());
                        player.kill();
                        //player.damage(new DamageSource(DamageType), 15);
                    }

                }
                //player.sendMessage(Text.literal(""+tickNumb));
            }
        }



    }
}
