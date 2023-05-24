package net.soupsy.dbfabric.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.soupsy.dbfabric.util.IEntityDataSaver;
import net.soupsy.dbfabric.util.PleaseJustWork;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.modify.LocalVariableDiscriminator;
import org.spongepowered.asm.mixin.injection.modify.ModifyVariableInjector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;

@Mixin(LivingEntity.class)
public abstract class DamageCalculatorMixin implements PleaseJustWork {
    @Inject(method = "modifyAppliedDamage", at = @At("HEAD"))
    protected void injectPowerDamageMethod(DamageSource source, float amount, CallbackInfoReturnable<Float> cir){
        if(source.getAttacker() instanceof PlayerEntity){

            PlayerEntity attackingPlayer = (PlayerEntity) source.getAttacker();
            IEntityDataSaver dataPlayer = (IEntityDataSaver) attackingPlayer;
            int power = dataPlayer.getPersistentData().getInt("power");

            attackingPlayer.sendMessage(Text.literal(""+amount));
            amount = amount+(power*20);
            attackingPlayer.sendMessage(Text.literal(""+amount));
        }
    }
}
