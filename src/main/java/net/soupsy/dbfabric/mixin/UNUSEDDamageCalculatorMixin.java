package net.soupsy.dbfabric.mixin;

import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.soupsy.dbfabric.util.IEntityDataSaver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class UNUSEDDamageCalculatorMixin extends Entity implements Attackable {
    public UNUSEDDamageCalculatorMixin(EntityType<?> type, World world) {
        super(type, world);
    }
    //@Shadow public abstract double getAttributeValue(EntityAttribute attribute);

    @Inject(method = "modifyAppliedDamage", at = @At("HEAD"))
    protected void injectPowerDamageMethod(DamageSource source, float amount, CallbackInfoReturnable<Float> cir){
        if(source.getAttacker() instanceof PlayerEntity){
            PlayerEntity attackingPlayer = (PlayerEntity) source.getAttacker();

            attackingPlayer.getStatusEffects();
            if(attackingPlayer.hasStatusEffect(StatusEffects.RESISTANCE)){

            }

            IEntityDataSaver dataPlayer = (IEntityDataSaver) attackingPlayer;
            int power = dataPlayer.getPersistentData().getInt("power");


            attackingPlayer.sendMessage(Text.literal(""+amount));
            amount = amount+(power*20);
            attackingPlayer.sendMessage(Text.literal(""+amount));
        }
    }
}
