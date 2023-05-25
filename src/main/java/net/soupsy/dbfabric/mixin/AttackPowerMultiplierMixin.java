package net.soupsy.dbfabric.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.soupsy.dbfabric.util.IEntityDataSaver;
import net.soupsy.dbfabric.util.PowerData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerEntity.class)
public abstract class AttackPowerMultiplierMixin extends Entity {

    @Shadow public abstract float getAttackCooldownProgress(float baseTime);

    public AttackPowerMultiplierMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyVariable(method = "attack", at = @At("STORE"), ordinal = 0)
    protected float multiplyDamage(float f) {

        // get NBT data of person
        IEntityDataSaver dataPlayer = (IEntityDataSaver) this;
        float power = PowerData.getPower(dataPlayer);

        // create a temp F to use in the return
        float tempF = (float) this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);

        //this.getAttackCooldownProgress();

        // return the same F in the original multiplies by my modifier
        return tempF * (power/20);
    }


}
