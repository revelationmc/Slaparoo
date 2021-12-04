package listeners;

import main.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import utils.ColourUtils;

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
                e.setFormat(ColourUtils.colour("&8Baby Faced " + name + "&8\u00BB &r" + e.getMessage()));
            }
            if(main.getPoints(p) >= 10 && main.getPoints(p) < 50){
                e.setFormat(ColourUtils.colour("&7Red Faced " + name + "&8\u00BB &r" + e.getMessage()));
            }
            if(main.getPoints(p) >= 50 && main.getPoints(p) < 100){
                e.setFormat(ColourUtils.colour("&fShazam " + name + "&8\u00BB &r" + e.getMessage()));
            }
            if(main.getPoints(p) >= 100 && main.getPoints(p) < 250){
                e.setFormat(ColourUtils.colour("&ePow " + name + "&8\u00BB &r" + e.getMessage()));
            }
            if(main.getPoints(p) >= 250 && main.getPoints(p) < 500){
                e.setFormat(ColourUtils.colour("&aWallop " + name + "&8\u00BB &r" + e.getMessage()));
            }
            if(main.getPoints(p) >= 500 && main.getPoints(p) < 750){
                e.setFormat(ColourUtils.colour("&2Splat " + name + "&8\u00BB &r" + e.getMessage()));
            }
            if(main.getPoints(p) >= 750 && main.getPoints(p) < 1000){
                e.setFormat(ColourUtils.colour("&dClank " + name + "&8\u00BB &r" + e.getMessage()));
            }
            if(main.getPoints(p) >= 1000 && main.getPoints(p) < 1500){
                e.setFormat(ColourUtils.colour("&bKapow " + name + "&8\u00BB &r" + e.getMessage()));
            }
        }

    }


}
