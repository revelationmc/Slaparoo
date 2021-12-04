package main;

import commands.SetSpawnLocations;
import commands.StartCommand;
import listeners.*;
import managers.GameManager;
import me.vagdedes.mysql.database.MySQL;
import me.vagdedes.mysql.database.SQL;
import net.revelationmc.gamesigns.api.GameSignsAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private GameSignsAPI gameSignsApi;
    FileConfiguration config = getConfig();

    @Override
    public void onEnable(){
        runStartupConnnection();
        createTable();
        config.options().copyDefaults(true);
        saveConfig();
        Bukkit.getServer().getPluginManager().registerEvents(new JoinListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new QuitListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new HitEvent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new InvListener(new GameManager(this)), this);
        Bukkit.getServer().getPluginManager().registerEvents(new FoodLevelListener(), this);
        Bukkit.getPluginCommand("start").setExecutor(new StartCommand(new GameManager(this)));
        Bukkit.getPluginCommand("gamespawn").setExecutor(new SetSpawnLocations(this));
        final RegisteredServiceProvider<GameSignsAPI> apiProvider = this.getServer().getServicesManager().getRegistration(GameSignsAPI.class);
        if (apiProvider != null) {
            this.gameSignsApi = apiProvider.getProvider();
        }
    }

    @Override
    public void onDisable(){
        saveConfig();
    }

    private void runStartupConnnection(){
            MySQL.setConnection("23.158.176.10", "u9881_XDwyt9wYE5", "L+3+90U^6y8oDMZmg2M69Xgp", "s9881_game", "3306");
            getLogger().info("Database Connected.");
    }


    private void createTable(){
        if(!SQL.tableExists("slaparoo")){
            SQL.createTable("slaparoo", "name VARCHAR(16), UUID VARCHAR(100), points INT(100), kills INT(100), deaths INT(100)");
        }
        else{
            getLogger().info("Slaparoo table exists already.");
        }
    }

    public boolean existsInTable(Player p)
    {
        return SQL.get("points", "UUID", "=", p.getUniqueId().toString(), "slaparoo") != null;
    }

    public void insertData(Player p)
    {
        SQL.insertData("name, UUID, points, kills, deaths", " '" + p.getName() + "', '" + p.getUniqueId().toString() + "', '0', '0', '0'"  , "slaparoo");
        //                   playerName, UUID, 0, 0, 0
    }

    public void addPoints(Player p, int amount)
    {
        SQL.set("points", getPoints(p) + amount, "UUID", "=" , p.getUniqueId().toString(), "slaparoo");
    }

    public int getPoints(Player p)
    {
        return (Integer) SQL.get("points", "UUID", "=" , p.getUniqueId().toString(), "slaparoo");
    }

    public void addKills(Player p)
    {
        SQL.set("kills", getKills(p) + 1, "UUID", "=", p.getUniqueId().toString(), "slaparoo");
    }

    public int getKills(Player p)
    {
        return (int) SQL.get("kills", "UUID", "=" , p.getUniqueId().toString(), "slaparoo");
    }

    public void addDeaths(Player p)
    {
        SQL.set("deaths", getDeaths(p) + 1, "UUID", "=" , p.getUniqueId().toString(), "slaparoo");
    }

    public int getDeaths(Player p)
    {
        return (int) SQL.get("deaths", "UUID", "=" , p.getUniqueId().toString(), "slaparoo");
    }

    public GameSignsAPI getGameSignsApi() {
        return this.gameSignsApi;
    }

}
