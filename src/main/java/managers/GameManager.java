package managers;

import main.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import utils.ColourUtils;

public class GameManager {
    private States state = States.WAITING;
    Main main;
    int startCount = 15;
    BukkitTask fin;
    private DebugClass debugClass;

    public GameManager(Main main, DebugClass debugClass){
        this.main = main;
        this.debugClass = debugClass;
    }

    public void runCountdown(){
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(main, () -> {
            for(Player online: Bukkit.getOnlinePlayers()){
                online.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ColourUtils.colour("&6&lSLAP &eis starting in &a&l" + startCount-- )));
                online.playSound(online.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 1, 10);
            }
        }, 0L, 300L);
        start();
    }

    public void start(){
        for(Player online: Bukkit.getOnlinePlayers()) {
            online.setFoodLevel(20);
            online.getInventory().clear();
            online.getInventory().setItem(0, new ItemStack(Material.COOKIE));
            online.getInventory().setChestplate(new ItemStack(Material.ELYTRA));
            online.sendTitle(ColourUtils.colour("&6&lSLAP&e&lAROO"), ColourUtils.colour("&eInspired by HiveMC: Java Edition."), 40, 60, 40);
            online.playSound(online.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 7, 1);
        }
       fin = Bukkit.getScheduler().runTaskLaterAsynchronously(main, () -> {
            for(Player online: Bukkit.getOnlinePlayers()){
                online.sendTitle(ColourUtils.colour("&4&lGAME OVER"), ColourUtils.colour("&a&oThanks for playing!"), 40, 60, 40);
                online.playSound(online.getLocation(), Sound.ENTITY_WITHER_DEATH, 7, 1);
                this.setState(States.IN_GAME);
                this.runStateChange();
            }
        }, 2400L);
    }

    public void setState(States state){
        this.state = state;
        if(getState() == States.IN_GAME){
            if(state == States.IN_GAME){
                debug("same");
            }
        }
    }

    public States getState(){
        return this.state;
    }

    public void runStateChange(){ // sends administrators a message letting them know the state was changed.
        Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission("slaparoo.start")).forEach(player ->
                player.sendMessage(ColourUtils.colour("&e&lGAME &8| &fGame state set to&8: &e" + getState().toString())));
    }

    public void debug(Object issue){
        if(issue == "start"){
            debugClass.cannotStart();
        }
        if(issue == "same"){
            debugClass.sameState();
        }
    }

}
