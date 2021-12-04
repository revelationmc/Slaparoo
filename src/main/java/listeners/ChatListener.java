package listeners;

import main.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    Main main;
    public ChatListener(Main main){
        this.main = main;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        String name = main.getLuckPerms().getUserManager().getUser(p.getUniqueId()).getCachedData().getMetaData().getPrefix() + p.getName();
        if(!main.existsInTable(p)){
            main.insertData(p);
        } else {
            if(main.getPoints(p) < 10){
                e.setFormat("&8Baby Faced " + name + "&8\u00BB &r" + e.getMessage());
            }
            if(main.getPoints(p) >= 10 && main.getPoints(p) < 50){
                e.setFormat("&7Red Faced " + name + "&8\u00BB &r" + e.getMessage());
            }
            if(main.getPoints(p) >= 50 && main.getPoints(p) < 100){
                e.setFormat("&fShazam " + name + "&8\u00BB &r" + e.getMessage());
            }
            if(main.getPoints(p) >= 100 && main.getPoints(p) < 250){
                e.setFormat("&ePow " + name + "&8\u00BB &r" + e.getMessage());
            }
            if(main.getPoints(p) >= 250 && main.getPoints(p) < 500){
                e.setFormat("&aWallop " + name + "&8\u00BB &r" + e.getMessage());
            }
            if(main.getPoints(p) >= 500 && main.getPoints(p) < 750){
                e.setFormat("&2Splat " + name + "&8\u00BB &r" + e.getMessage());
            }
            if(main.getPoints(p) >= 750 && main.getPoints(p) < 1000){
                e.setFormat("&dClank " + name + "&8\u00BB &r" + e.getMessage());
            }
            if(main.getPoints(p) >= 1000 && main.getPoints(p) < 1500){
                e.setFormat("&bKapow " + name + "&8\u00BB &r" + e.getMessage());
            }
        }

    }


}
