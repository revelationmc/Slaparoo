package listeners;

import managers.GameManager;
import managers.States;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InvListener implements Listener {

    GameManager gm;
    public InvListener(GameManager gm){
        this.gm = gm;
    }

    @EventHandler
    public void onInvListen(InventoryClickEvent event){
        if(gm.getState().equals(States.IN_GAME)){
            event.setCancelled(true);
        }
    }
}
