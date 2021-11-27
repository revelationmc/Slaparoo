package listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import utils.ColourUtils;

public class QuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent quitEvent){
        Player player = quitEvent.getPlayer();
        String nameCut = player.getDisplayName().substring(player.getDisplayName().indexOf("|")+1);
        quitEvent.setQuitMessage(ColourUtils.colour("&e&lSLAP &8| &a" + nameCut.trim() + " &edoesn't want to slap anymore. &6(&a" +
                Bukkit.getServer().getOnlinePlayers().size() + "&e/&a10&6)"));
    }

}
