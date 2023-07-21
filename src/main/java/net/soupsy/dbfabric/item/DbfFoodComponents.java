package net.soupsy.dbfabric.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class DbfFoodComponents {
    public static final FoodComponent SENSU_BEAN = new FoodComponent.Builder().hunger(20).saturationModifier(0.0f)
            .statusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 10, 200), 1.0f)
            .alwaysEdible()
            .snack()
            .build();
}
