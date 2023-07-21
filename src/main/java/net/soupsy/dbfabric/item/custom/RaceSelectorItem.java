package net.soupsy.dbfabric.item.custom;

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
import net.soupsy.dbfabric.playerStorage.PlayerStatsStorage;
import net.soupsy.dbfabric.playerStorage.Race;

public class RaceSelectorItem extends Item {
    public RaceSelectorItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if(!world.isClient() && (hand == Hand.MAIN_HAND || hand == Hand.OFF_HAND)){
            selectRandomRace(user);
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
            selectRandomRace(user);
            user.getItemCooldownManager().set(this, 20);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }
        return super.useOnEntity(stack, user, entity, hand);
    }

    private void selectRandomRace(PlayerEntity player){
        int rand = getRandomNumber(6);
        switch (rand){
            case 0:
                PlayerStatsStorage.setRace(player.getUuid(), Race.HUMAN);
                player.sendMessage(Text.literal("You obtained the §a"+Race.HUMAN.getName()+"§r race."));
                break;
            case 1:
                PlayerStatsStorage.setRace(player.getUuid(), Race.SAIYAN);
                player.sendMessage(Text.literal("You obtained the §a"+Race.SAIYAN.getName()+"§r race."));
                break;
            case 2:
                PlayerStatsStorage.setRace(player.getUuid(), Race.HALFSAIYAN);
                player.sendMessage(Text.literal("You obtained the §a"+Race.HALFSAIYAN.getName()+"§r race."));
                break;
            case 3:
                PlayerStatsStorage.setRace(player.getUuid(), Race.NAMEKIAN);
                player.sendMessage(Text.literal("You obtained the §a"+Race.NAMEKIAN.getName()+"§r race."));
                break;
            case 4:
                PlayerStatsStorage.setRace(player.getUuid(), Race.FREIZA);
                player.sendMessage(Text.literal("You obtained the §a"+Race.FREIZA.getName()+"§r race."));
                break;
            case 5:
                PlayerStatsStorage.setRace(player.getUuid(), Race.ANDROID);
                player.sendMessage(Text.literal("You obtained the §a"+Race.ANDROID.getName()+"§r race."));
                break;
            case 6:
                PlayerStatsStorage.setRace(player.getUuid(), Race.MAJIN);
                player.sendMessage(Text.literal("You obtained the §a"+Race.MAJIN.getName()+"§r race."));
                break;

        }

    }
    private int getRandomNumber(Integer max) {
        return Random.createLocal().nextInt(max+1);
    }
}
