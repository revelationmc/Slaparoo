package listeners;

import main.Main;
import managers.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import utils.ColourUtils;

public class QuitListener implements Listener {

    Main main;
    GameManager gm;
    public QuitListener(Main main){
        this.main = main;
        gm = new GameManager(main);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent quitEvent){
        Player player = quitEvent.getPlayer();
            quitEvent.setQuitMessage(ColourUtils.colour("&6&lSLAP &8| &r" + player.getDisplayName() + " &edoesn't want to slap anymore. &6(&a" +
                    (Bukkit.getOnlinePlayers().size()-1) + "&e/&a5&6)"));
    }

}
