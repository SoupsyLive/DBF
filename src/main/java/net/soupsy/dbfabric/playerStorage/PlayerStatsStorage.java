package net.soupsy.dbfabric.playerStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerStatsStorage {
    private static final HashMap<UUID, ArrayList<PowerUp>> unlockedPowerups = new HashMap<>();
    public static final HashMap<UUID, ArrayList<Object>> playerStats = new HashMap<>();

    // Max Power || Max Energy || Race || Personal Potential Rate ||

    public static void addPlayer(UUID uuid){
        ArrayList<Object> list = new ArrayList<>();
        list.add(100L);
        list.add(100);
        list.add(Race.HUMAN);
        list.add(1f);

        playerStats.put(uuid, list);
    }

// GET
    public static long getMaxPower(UUID uuid){
        if(!playerStats.containsKey(uuid)){addPlayer(uuid);}
        ArrayList list = playerStats.get(uuid);
        long stat = (long) list.get(0);
        return stat;
    }
    public static Integer getMaxEnergy(UUID uuid){
        if(!playerStats.containsKey(uuid)){addPlayer(uuid);}
        ArrayList list = playerStats.get(uuid);
        int stat = (int) list.get(1);
        return stat;
    }
    public static Race getRace(UUID uuid){
        if(!playerStats.containsKey(uuid)){addPlayer(uuid);}
        ArrayList list = playerStats.get(uuid);
        Race stat = (Race) list.get(2);
        return stat;
    }
    public static List getUnlockedPowerups(UUID uuid){
        return unlockedPowerups.get(uuid);
    }
    public static Float getPersonalPotential(UUID uuid){
        if(!playerStats.containsKey(uuid)){addPlayer(uuid);}
        ArrayList list = playerStats.get(uuid);
        float stat = (float) list.get(3);
        return stat;
    }

// SET
    public static long setMaxPower(UUID uuid, long power){
        if(!playerStats.containsKey(uuid)){addPlayer(uuid);}
        ArrayList list = playerStats.get(uuid);
        list.set(0, power);

        return power;
    }
    public static int setMaxEnergy(UUID uuid, int energy){
        if(!playerStats.containsKey(uuid)){addPlayer(uuid);}
        ArrayList list = playerStats.get(uuid);
        list.set(1, energy);

        return energy;
    }
    public static Race setRace(UUID uuid, Race newRace){
        if(!playerStats.containsKey(uuid)){addPlayer(uuid);}
        ArrayList list = playerStats.get(uuid);
        list.set(2, newRace);

        return newRace;
    }
    public static ArrayList setUnlockedPowerups(UUID uuid, ArrayList list){
        unlockedPowerups.put(uuid, list);
        return list;
    }
    public static float setPersonalPotential(UUID uuid, float power){
        if(!playerStats.containsKey(uuid)){addPlayer(uuid);}
        ArrayList list = playerStats.get(uuid);
        list.set(3, power);

        return power;
    }
// ADD
    public static long addMaxPower(UUID uuid, long power){
        if(!playerStats.containsKey(uuid)){addPlayer(uuid);}
        ArrayList list = playerStats.get(uuid);
        long oldPower = (long) list.get(0);
        list.set(0, oldPower+power);

        return oldPower+power;
    }
    public static int addMaxEnergy(UUID uuid, int energy){
        if(!playerStats.containsKey(uuid)){addPlayer(uuid);}
        ArrayList list = playerStats.get(uuid);
        int oldEnergy = (int) list.get(1);
        list.set(1, oldEnergy+energy);

        return oldEnergy+energy;
    }
    public static ArrayList addUnlockedPowerup(UUID uuid, PowerUp powerup){
        ArrayList powerupList = unlockedPowerups.get(uuid);
        powerupList.add(powerup);
        unlockedPowerups.put(uuid, powerupList);
        return powerupList;
    }

    public static int addPersonalPotential(UUID uuid, int potential){
        if(!playerStats.containsKey(uuid)){addPlayer(uuid);}
        ArrayList list = playerStats.get(uuid);
        int oldPotential = (int) list.get(3);
        list.set(1, oldPotential+potential);

        return oldPotential+potential;
    }


// SUBTRACT
    public static long removeMaxPower(UUID uuid, long power){
        if(!playerStats.containsKey(uuid)){addPlayer(uuid);}
        ArrayList list = playerStats.get(uuid);
        long oldPower = (long) list.get(0);
        if(oldPower-power >0){
            list.set(0, oldPower-power);
            return oldPower-power;
        }
        list.set(0, 1);
        return 1;
    }
    public static int removeMaxEnergy(UUID uuid, int energy){
        if(!playerStats.containsKey(uuid)){addPlayer(uuid);}
        ArrayList list = playerStats.get(uuid);
        int oldEnergy = (int) list.get(0);
        if(oldEnergy-energy >0){
            list.set(0, oldEnergy-energy);
            return oldEnergy-energy;
        }
        list.set(0, 1);
        return 1;
    }
    public static ArrayList removeUnlockedPowerup(UUID uuid, PowerUp powerup){
        ArrayList powerupList = unlockedPowerups.get(uuid);
        if(powerupList.contains(powerup)){
            powerupList.remove(powerup);
            unlockedPowerups.put(uuid, powerupList);
        }
        return powerupList;
    }
    public static float removePersonalPotential(UUID uuid, float potential){
        if(!playerStats.containsKey(uuid)){addPlayer(uuid);}
        ArrayList list = playerStats.get(uuid);
        float oldPotential = (float) list.get(0);
        if(oldPotential-potential >0){
            list.set(0, oldPotential-potential);
            return oldPotential-potential;
        }
        list.set(0, 1);
        return 1;
    }

}
