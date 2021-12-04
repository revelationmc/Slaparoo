package listeners;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;


public class HitEvent implements Listener {

    private Location below;

    public static HashMap<Player, Player> playerHitCheck = new HashMap<>();

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event){
       if(event.getDamager() instanceof Player){
           if(event.getEntity() instanceof Player){
               Player p = (Player) event.getDamager();
               Player t = (Player) event.getEntity();
               this.below = new Location(t.getWorld(), t.getLocation().getX(), t.getLocation().getY() - 1, t.getLocation().getZ());
                   t.setHealth(20);
                   t.setVelocity(t.getVelocity().multiply(5).setY(3));
                   p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_HURT, 7, 1);
                   p.spawnParticle(Particle.SMALL_FLAME, t.getLocation(), 3);
                   playerHitCheck.put(p, t);
           }
       }
    }


}