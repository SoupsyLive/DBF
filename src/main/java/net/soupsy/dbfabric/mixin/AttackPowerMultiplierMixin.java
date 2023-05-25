package net.soupsy.dbfabric.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.soupsy.dbfabric.util.IEntityDataSaver;
import net.soupsy.dbfabric.util.PowerData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerEntity.class)
public abstract class AttackPowerMultiplierMixin extends Entity {

    public AttackPowerMultiplierMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyVariable(method = "attack", at = @At("HEAD"), argsOnly = false)
    public PlayerEntity multiplyDamage(PlayerEntity player) {

        IEntityDataSaver dataPlayer = (IEntityDataSaver) player;
        int power = PowerData.getPower(dataPlayer);


        return player;
    }


}
