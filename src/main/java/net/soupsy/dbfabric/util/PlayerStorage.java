package net.soupsy.dbfabric.util;

import java.util.HashMap;
import java.util.UUID;

public class PlayerStorage {
    //key = uuid player
    //string = power up type
    //boolean = enabled
    private static final HashMap<UUID, String> playerPowerups = new HashMap<>();
    private static final HashMap<UUID, Boolean> poweredPlayers = new HashMap<>();

    public static void togglePowerup(UUID uuid){
        if(!poweredPlayers.containsKey(uuid)){
            poweredPlayers.put(uuid, true);
        }
        if(poweredPlayers.get(uuid)){
            poweredPlayers.put(uuid, false);
        }else if(!poweredPlayers.get(uuid)){
            poweredPlayers.put(uuid, true);
        }
    }
    public static boolean isOnPlayerList(String list, UUID uuid){
        if(list.equals("Powered-Players")){
            if (PlayerStorage.playerPowerups.containsKey(uuid)){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    public static boolean isPowerupActive(String list, UUID uuid){
        if(list.equals("Powered-Players")){
            if (poweredPlayers.containsKey(uuid)){
                return poweredPlayers.get(uuid);
            }else{
                poweredPlayers.put(uuid, false);
            }
        }
        return false;
    }
}
