package listeners;


import main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import utils.ColourUtils;

public class VoidCheck implements Listener {

    Main main;
    public VoidCheck(Main main){
        this.main = main;
    }

    @EventHandler
    public void onPlayerMove(EntityDamageEvent event){
        Player player = (Player) event.getEntity();
        if(event.getCause() == EntityDamageEvent.DamageCause.VOID) {
            event.setCancelled(true);
            if(!HitEvent.playerHitCheck.get(player).equals(null)){
                HitEvent.playerHitCheck.get(player).sendMessage(ColourUtils.colour("&6&lSLAP &8| &aYou killed &e&l" + player.getName()));
                HitEvent.playerHitCheck.get(player).playSound(HitEvent.playerHitCheck.get(player).getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 7, 1);
            }
            if(JoinListener.playerNum.get(player).equals(null)){
               if(JoinListener.num < 5){
                   JoinListener.playerNum.put(player, JoinListener.num);
                   JoinListener.num+=1;
               }
            } else {
                switch(JoinListener.playerNum.get(player)){
                    case 0:
                        player.teleport(main.getConfig().getLocation("spawn1"));
                        break;
                    case 1:
                        player.teleport(main.getConfig().getLocation("spawn2"));
                        break;
                    case 2:
                        player.teleport(main.getConfig().getLocation("spawn3"));
                        break;
                    case 3:
                        player.teleport(main.getConfig().getLocation("spawn4"));
                        break;
                    case 4:
                        player.teleport(main.getConfig().getLocation("spawn5"));
                        break;
                }
            }
        }
    }


}
