package listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Set;

public class VoidListener implements Listener {

    Location purpleTerracotta;
    Set<Block> purpleTerraBlock;

    @EventHandler
    public void onVoidDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
                event.setCancelled(true);

                Bukkit.getWorlds().forEach(world -> {
                    if(world.getName().equalsIgnoreCase("Slaparoo")){
                        for (int x = -10000000; x < 1000000; ) {
                            for (int y = -10000000; y < 1000000; ) {
                                for (int z = -10000000; z < 1000000; ) {
                                    world.getBlockAt(x, y, z);
                                    if(world.getBlockAt(x, y, z).getType() == Material.PURPLE_TERRACOTTA){
                                        Block b = world.getBlockAt(x, y, z);
                                        purpleTerraBlock.add(b);
                                        this.purpleTerracotta = new Location(b.getWorld(), b.getX(), b.getY()+2, b.getZ());
                                        player.teleport(this.purpleTerracotta);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                });


                }
            }
        }

}
