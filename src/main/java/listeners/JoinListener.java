package listeners;

import main.Main;
import managers.GameManager;
import managers.States;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import utils.ColourUtils;

import java.util.HashMap;


public class JoinListener implements Listener {

    Main main;
    GameManager gm;
    public static HashMap<Player, Integer> playerNum = new HashMap<>();
    public static int num = 0;
    public JoinListener(Main main){
        this.main = main;
        gm = new GameManager(main);
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent joinEvent){
        Player player = joinEvent.getPlayer();
        if(gm.getState() == States.IN_GAME){
            joinEvent.setJoinMessage(ColourUtils.colour("&6&lSLAP &8| &r" + player.getDisplayName() + "&7joined as a spectator."));
            player.setGameMode(GameMode.SPECTATOR);
            player.teleport(Bukkit.getWorld("world").getSpawnLocation());
            player.sendTitle("", ColourUtils.colour("&5You are a spectator."), 30, 60, 30);
            Bukkit.getOnlinePlayers().forEach(inGame -> {
                inGame.hidePlayer(main, player);
            });
        } else {
                if(Bukkit.getOnlinePlayers().size() >= 2) {
                    gm.runCountdown();
                    switch(num){
                        case 0:
                            playerNum.put(player, 0);
                            num+=1;
                            break;
                        case 1:
                            playerNum.put(player, 1);
                            num+=1;
                            break;
                        case 2:
                            playerNum.put(player, 2);
                            num+=1;
                            break;
                        case 3:
                            playerNum.put(player, 3);
                            num+=1;
                            break;
                        case 4:
                            playerNum.put(player, 4);
                            num+=1;
                            break;
                }
            }
            joinEvent.setJoinMessage(ColourUtils.colour("&6&lSLAP &8| &r" + player.getDisplayName() + "&e is ready to slap! &6(&a" +
                    Bukkit.getServer().getOnlinePlayers().size() + "&e/&a65&6)"));
        }
    }

}
