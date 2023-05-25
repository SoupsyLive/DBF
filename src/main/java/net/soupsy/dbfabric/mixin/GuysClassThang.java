package net.soupsy.dbfabric.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.soupsy.dbfabric.util.IEntityDataSaver;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class GuysClassThang extends Entity {

    @Shadow
    public abstract @Nullable StatusEffectInstance getStatusEffect(StatusEffect effect);
    @Shadow public abstract boolean hasStatusEffect(StatusEffect effect);


    @Shadow public abstract boolean damage(DamageSource source, float amount);

    @Shadow private @Nullable DamageSource lastDamageSource;

    public GuysClassThang(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyVariable(method = "damage", at = @At("HEAD"), argsOnly = true)
    public float multiplyDamage(float amount) {
        if (this.hasStatusEffect(StatusEffects.ABSORPTION)) {
            //return amount + (amount * ((this.getStatusEffect(StatusEffects.ABSORPTION).getAmplifier() + 1) * 0.15f));
            return amount+(1*25);


        }

        if(this.lastDamageSource != null){
            if(this.lastDamageSource.getAttacker() instanceof PlayerEntity){
                IEntityDataSaver dataAttacker = (IEntityDataSaver) this.lastDamageSource.getAttacker();
                int attackerPower = dataAttacker.getPersistentData().getInt("power");
                float damage = amount+(attackerPower/20);
                if(damage < 0){
                    damage = 0;
                }
                return damage;
            }
        }

        IEntityDataSaver dataPlayer = ((IEntityDataSaver) this);
        int power = dataPlayer.getPersistentData().getInt("power");
        float damage = amount-(power/20);
        if(damage < 0){
            damage = 0;
        }
        return damage;
    }


}
