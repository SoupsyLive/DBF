package net.soupsy.dbfabric.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;
import net.soupsy.dbfabric.util.IEntityDataSaver;
import net.soupsy.dbfabric.util.PowerCalculator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class PowerDefenseMixin extends Entity {
    @Shadow public abstract boolean damage(DamageSource source, float amount);
    public PowerDefenseMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyVariable(method = "damage", at = @At("HEAD"), argsOnly = true)
    public float reduceDamage(float amount) {

        // get power NBT
        IEntityDataSaver dataPlayer = ((IEntityDataSaver) this);
        float power = dataPlayer.getPersistentData().getInt("power");

        // calc the damage reduction and apply it to arg
        amount = PowerCalculator.calcPowerDefense(amount, power);

        // return damage
        return amount;
    }
}
