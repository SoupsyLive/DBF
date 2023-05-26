package net.soupsy.dbfabric.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.soupsy.dbfabric.util.IEntityDataSaver;
import net.soupsy.dbfabric.util.PowerCalculator;
import net.soupsy.dbfabric.util.PowerData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerEntity.class)
public abstract class PowerAttackMixin extends Entity {
    public PowerAttackMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyVariable(method = "attack", at = @At("STORE"), ordinal = 0)
    protected float multiplyDamage(float f) {

        // get NBT data of person
        IEntityDataSaver dataPlayer = (IEntityDataSaver) this;
        float power = PowerData.getPower(dataPlayer);

        // return the damage with a calculation
        return PowerCalculator.calcPowerAttack(power);
    }
}
