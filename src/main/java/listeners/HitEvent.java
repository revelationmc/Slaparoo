package listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import utils.ColourUtils;


public class HitEvent implements Listener {

    Location below;

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event){
       if(event.getDamager() instanceof Player){
           if(event.getEntity() instanceof Player){
               Player p = (Player) event.getDamager();
               Player t = (Player) event.getEntity();
               below = new Location(t.getWorld(), t.getLocation().getX(), t.getLocation().getY() - 1, t.getLocation().getZ());
               if(below.getBlock().getType() == Material.PURPLE_TERRACOTTA){
                   event.setCancelled(true);
                   p.sendMessage(ColourUtils.colour("&cYou need to be on the map to hit players."));
               } else {
                   t.setHealth(20);
                   t.setVelocity(t.getVelocity().multiply(7F));
               }
           }
       }
    }


}