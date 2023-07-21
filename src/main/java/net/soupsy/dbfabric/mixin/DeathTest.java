package net.soupsy.dbfabric.mixin;

import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.soupsy.dbfabric.util.IEntityDataSaver;
import net.soupsy.dbfabric.util.PowerCalculator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class DeathTest extends Entity{


    public DeathTest(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "onDeath", at = @At("HEAD"))
    protected void preventDeath(DamageSource damageSource, CallbackInfo ci){
        if(damageSource.getSource() instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity) damageSource.getSource();
            if(!player.isDead() && !player.isRemoved()){
                IEntityDataSaver dataPlayer = ((IEntityDataSaver) player);
                boolean downed = dataPlayer.getPersistentData().getBoolean("downed");
                if(!downed){

                }
            }
        }


    }
}
