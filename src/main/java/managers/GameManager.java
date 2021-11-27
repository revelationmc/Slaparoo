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
    States state;
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
        for(Player online: Bukkit.getOnlinePlayers()) {
            online.setFoodLevel(20);
            online.getInventory().clear();
            online.getInventory().setItem(0, new ItemStack(Material.COOKIE));
            online.getInventory().setChestplate(new ItemStack(Material.ELYTRA));
            online.sendTitle(ColourUtils.colour("&6&lSLAP&e&lAROO"), ColourUtils.colour("&eInspired by HiveMC: Java Edition."), 40, 40, 40);
        }
       fin = Bukkit.getScheduler().runTaskLaterAsynchronously(main, () -> {
            for(Player online: Bukkit.getOnlinePlayers()){
                online.sendTitle(ColourUtils.colour("&4&lGAME OVER"), "", 40, 40, 40);
                online.playSound(online.getLocation(), Sound.ENTITY_WITHER_DEATH, 1, 10);
                state = States.WAITING;
            }
        }, 2400L);
    }


}
