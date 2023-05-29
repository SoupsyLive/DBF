package net.soupsy.dbfabric.util;

public class PowerCalculator {
    public static float calcPowerAttack(float power){
        return Math.max(power/20, 0.1f);
    }
    public static float calcPowerDefense(float damage, float power){
        damage /= (1+(power/20));
        return damage;
    }


}
