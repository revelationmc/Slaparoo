package commands;

import managers.GameManager;
import managers.States;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import utils.ColourUtils;

public class StartCommand implements CommandExecutor {

    private GameManager gm;
    public StartCommand(GameManager gm){
        this.gm = gm;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        gm.setState(States.IN_GAME);
        if(!(gm.getState() == States.IN_GAME)){
            gm.start();
        } else {
            gm.debug("same");
        }
        Player p = (Player) sender;
        p.sendMessage(ColourUtils.colour("&a&lAdmin command executed successfully."));
        return true;
    }
}
