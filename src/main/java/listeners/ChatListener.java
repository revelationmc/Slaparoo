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
            if(main.getPoints(p) < 2){
                e.setFormat(ColourUtils.colour("&8Baby Faced " + name + " &8\u00BB &r" + e.getMessage()));
            }
            if(main.getPoints(p) >= 2 && main.getPoints(p) < 5){
                e.setFormat(ColourUtils.colour("&7Red Faced " + name + " &8\u00BB &r" + e.getMessage()));
            }
            if(main.getPoints(p) >= 5 && main.getPoints(p) < 15){
                e.setFormat(ColourUtils.colour("&fShazam " + name + " &8\u00BB &r" + e.getMessage()));
            }
            if(main.getPoints(p) >= 15 && main.getPoints(p) < 35){
                e.setFormat(ColourUtils.colour("&ePow " + name + " &8\u00BB &r" + e.getMessage()));
            }
            if(main.getPoints(p) >= 35 && main.getPoints(p) < 60){
                e.setFormat(ColourUtils.colour("&aWallop " + name + " &8\u00BB &r" + e.getMessage()));
            }
            if(main.getPoints(p) >= 60 && main.getPoints(p) < 100){
                e.setFormat(ColourUtils.colour("&2Splat " + name + " &8\u00BB &r" + e.getMessage()));
            }
            if(main.getPoints(p) >= 100 && main.getPoints(p) < 200){
                e.setFormat(ColourUtils.colour("&dClank " + name + " &8\u00BB &r" + e.getMessage()));
            }
            if(main.getPoints(p) >= 200 && main.getPoints(p) < 350){
                e.setFormat(ColourUtils.colour("&bKapow " + name + " &8\u00BB &r" + e.getMessage()));
            }
        }

    }


}
