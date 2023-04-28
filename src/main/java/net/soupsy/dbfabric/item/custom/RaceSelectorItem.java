package net.soupsy.dbfabric.item.custom;

import it.unimi.dsi.fastutil.ints.IntImmutableList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class RaceSelectorItem extends Item {
    public RaceSelectorItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if(!world.isClient() && (hand == Hand.MAIN_HAND || hand == Hand.OFF_HAND)){
            outputRandomNumber(user);
            user.getItemCooldownManager().set(this, 20);
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }
        return super.use(world, user, hand);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if(hand == Hand.MAIN_HAND || hand == Hand.OFF_HAND){
            //if(entity.is)
            outputRandomNumber(user);
            user.getItemCooldownManager().set(this, 20);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }
        return super.useOnEntity(stack, user, entity, hand);
    }

    private void outputRandomNumber(PlayerEntity player){
        player.sendMessage(Text.literal("Chosen Race #"+getRandomNumber(9)));
    }
    private int getRandomNumber(Integer max) {
        return Random.createLocal().nextInt(max+1);
    }
}
