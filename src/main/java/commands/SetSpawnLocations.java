package commands;

import main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import utils.ColourUtils;

public class SetSpawnLocations implements CommandExecutor {

    Main m;
    public SetSpawnLocations(Main m){
        this.m = m;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;

        switch(args.length){
            case 0:
                p.sendMessage(ColourUtils.colour("&6&lSLAP &8\u00BB &cInvalid arguments."));
                break;
            case 1:
                switch(args[0]){
                    case "1":
                        p.sendMessage(ColourUtils.colour("&6&lSLAP &8\u00BB &aSet spawn 1 to your location."));
                        m.getConfig().set("spawn1", p.getLocation());
                        m.saveConfig();
                        break;
                    case "2":
                        p.sendMessage(ColourUtils.colour("&6&lSLAP &8\u00BB &aSet spawn 2 to your location."));
                        m.getConfig().set("spawn2", p.getLocation());
                        m.saveConfig();
                        break;
                    case "3":
                        p.sendMessage(ColourUtils.colour("&6&lSLAP &8\u00BB &aSet spawn 3 to your location."));
                        m.getConfig().set("spawn3", p.getLocation());
                        m.saveConfig();
                        break;
                    case "4":
                        p.sendMessage(ColourUtils.colour("&6&lSLAP &8\u00BB &aSet spawn 4 to your location."));
                        m.getConfig().set("spawn4", p.getLocation());
                        m.saveConfig();
                        break;
                    case "5":
                        p.sendMessage(ColourUtils.colour("&6&lSLAP &8\u00BB &aSet spawn 5 to your location."));
                        m.getConfig().set("spawn5", p.getLocation());
                        m.saveConfig();
                        break;
                }
                break;
        }

        return true;
    }
}
