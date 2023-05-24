package net.soupsy.dbfabric.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.player.PlayerEntity;

public class DeathEvents implements  ServerLivingEntityEvents.AllowDeath{

    @Override
    public boolean allowDeath(LivingEntity entity, DamageSource damageSource, float damageAmount) {

        if(entity instanceof PlayerEntity){
            return true;
        }
        return true;
    }
}
