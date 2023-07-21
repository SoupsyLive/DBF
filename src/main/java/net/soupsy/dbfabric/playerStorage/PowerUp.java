package net.soupsy.dbfabric.playerStorage;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public enum PowerUp {

    // Saiyan & Half Saiyan
    KAIOKEN("kaio-ken", "Kaio-Ken", 50, 2),
    SUPERSAIYAN("super-saiyan", "Super Saiyan", 80, 50),
    SUPERSAIYANTWO("super-saiyan-two", "Super Saiyan Two", 160, 100),
    SUPERSAIYANTHREE("super-saiyan-three", "Super Saiyan Three", 640, 400),
    OOZARU("oozaru", "Oozaru", 0, 10),

    // Namekians

    YELLOWNAMEK("yellow-namek", "Yellow Namek", 100, 100),
    ORANGENAMEK("orange-namek", "Orange Namek", 400, 400);

    private final String id;
    private final String displayName;
    private final int energyCost;
    private final int powerMultiplier;

    private PowerUp(String id, String displayName, int energyCost, int powerMultiplier){
        this.id = id;
        this.displayName = displayName;
        this.energyCost = energyCost;
        this.powerMultiplier = powerMultiplier;
    }

    public String getId() {
        return this.id;
    }
    public String getName() {
        return this.displayName;
    }
    public Integer getEnergyCost() {
        return this.energyCost;
    }
    public Integer getPowerMultiplier(){return this.powerMultiplier;}
    public static PowerUp getPowerupFromId(String id){
        List<PowerUp> powerups = Arrays.asList(PowerUp.values());
        for(int i=0; i < powerups.size(); i++){
            if(Objects.equals(id, powerups.get(i).getId())){
                return powerups.get(i);
            }
        }
        return null;
    }

    public boolean checkRacePowerup(PowerUp powerup , Race race){
        return switch (powerup) {
            case KAIOKEN -> true;
            case SUPERSAIYAN, SUPERSAIYANTWO, SUPERSAIYANTHREE, OOZARU ->
                    race == Race.SAIYAN || race == Race.HALFSAIYAN;
            case YELLOWNAMEK, ORANGENAMEK -> race == Race.NAMEKIAN;
        };
    }

}
