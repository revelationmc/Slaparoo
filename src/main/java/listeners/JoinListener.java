package listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import utils.ColourUtils;

public class JoinListener implements Listener{

    @EventHandler
    public void onJoin(PlayerJoinEvent joinEvent){
        Player player = joinEvent.getPlayer();
        String nameCut = player.getDisplayName().substring(player.getDisplayName().indexOf("|")+1);
        joinEvent.setJoinMessage(ColourUtils.colour("&6&lSLAP &8| &r" + nameCut.trim() + "&e is ready to slap! &6(&a" +
                Bukkit.getServer().getOnlinePlayers().size() + "&e/&a10&6)"));
    }

}
