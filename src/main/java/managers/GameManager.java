package managers;

import main.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.revelationmc.gamesigns.api.GameState;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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

    public GameManager(Main main){
        this.main = main;
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
        if(main.getConfig().getKeys(false).size() == 0){
            debug("nospawns");
        }
        for(Player online: Bukkit.getOnlinePlayers()) {
            online.setFoodLevel(20);
            online.getInventory().clear();
            online.getInventory().setItem(0, new ItemStack(Material.COOKIE));
            online.getInventory().setChestplate(new ItemStack(Material.ELYTRA));
            online.sendTitle(ColourUtils.colour("&6&lSLAP&e&lAROO"), ColourUtils.colour("&eInspired by HiveMC: Java Edition."), 40, 60, 40);
            online.playSound(online.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 7, 1);
            if(!main.existsInTable(online)){
                main.insertData(online.getPlayer());
            }
        }
        this.setState(States.IN_GAME);
        main.getGameSignsApi().setGameState(GameState.IN_GAME);
        this.runStateChange();
       fin = Bukkit.getScheduler().runTaskLaterAsynchronously(main, () -> {
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.sendTitle(ColourUtils.colour("&4&lGAME OVER"), ColourUtils.colour("&a&oThanks for playing!"), 40, 60, 40);
                player.playSound(player.getLocation(), Sound.ENTITY_WITHER_DEATH, 7, 1);
                player.getInventory().clear();
            });
            this.setState(States.WAITING);
            main.getGameSignsApi().setGameState(GameState.WAITING);
            this.runStateChange();
        }, 2400L);
    }

    public void setState(States state){
        this.state = state;
    }

    public States getState(){
        return this.state;
    }

    public void runStateChange(){ // sends administrators a message letting them know the state was changed.
        Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission("slaparoo.start")).forEach(player ->
                player.sendMessage(ColourUtils.colour("&e&lGAME &8| &fGame state set to&8: &e" + getState().toString())));
    }

    public void debug(String issue) {
        if (issue == "start") {
            Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission("slaparoo.start")).forEach(player -> {
                    player.sendMessage(ColourUtils.colour("&c&lDEBUG &8| &fDebug at start:&c game did not start properly."));
                    player.sendMessage(ColourUtils.colour("&c&lDEBUG &8| &6Cause&8: &fGame is already running."));
            });
        }
        if (issue == "same") {
            Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission("slaparoo.start")).forEach(player -> {
                    player.sendMessage(ColourUtils.colour("&c&lDEBUG &8| &fDebug at state change:&c state cannot change to the same state."));
                    player.sendMessage(ColourUtils.colour("&c&lDEBUG &8| &6Cause&8: &fGame state cannot be set to the same game state."));
            });
        }
        if (issue == "nospawns"){
            Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission("slaparoo.start")).forEach(player -> {
                player.sendMessage(ColourUtils.colour("&c&lDEBUG &8| &4CRITICAL&8: &cGame spawns not set."));;
            });
        }
    }

}
