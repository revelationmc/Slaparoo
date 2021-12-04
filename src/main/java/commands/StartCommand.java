package commands;

import listeners.JoinListener;
import managers.GameManager;
import managers.States;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import utils.ColourUtils;

public class StartCommand implements CommandExecutor {

    private GameManager gm;
    public StartCommand(GameManager gm){
        this.gm = gm;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(gm.getState() == States.IN_GAME)){
            gm.setState(States.IN_GAME);
            gm.start();
            sender.sendMessage(ColourUtils.colour("&e&lGAME &8| &fGame started."));
            Bukkit.getOnlinePlayers().forEach(player -> {
                if(JoinListener.num < 5){
                    JoinListener.playerNum.put(player, JoinListener.num+=1);
                }
            });
        } else {
            gm.debug("start");
        }
        return true;
    }
}
