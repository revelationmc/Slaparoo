package main;

import commands.SetSpawnLocations;
import commands.StartCommand;
import listeners.*;
import managers.GameManager;
import me.vagdedes.mysql.database.MySQL;
import me.vagdedes.mysql.database.SQL;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.revelationmc.gamesigns.api.GameSignsAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.CompletableFuture;

public class Main extends JavaPlugin {

    private GameSignsAPI gameSignsApi;
    FileConfiguration config = getConfig();

    @Override
    public void onEnable(){
        if(!MySQL.isConnected()){
            runStartupConnnection();
        }
        createTable();
        config.options().copyDefaults(true);
        saveConfig();
        Bukkit.getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new QuitListener(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new HitEvent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new InvListener(new GameManager(this)), this);
        Bukkit.getServer().getPluginManager().registerEvents(new FoodLevelListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        Bukkit.getPluginCommand("start").setExecutor(new StartCommand(new GameManager(this)));
        Bukkit.getPluginCommand("gamespawn").setExecutor(new SetSpawnLocations(this));
        Bukkit.getPluginManager().registerEvents(new VoidCheck(this), this);
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
            MySQL.setConnection("na05-sql.pebblehost.com", "customer_236196_slaparoo", "jOVXZ!Inm-52bu7alDb3", "customer_236196_slaparoo", "3306");
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
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            SQL.insertData("name, UUID, points, kills, deaths", " '" + p.getName() + "', '" + p.getUniqueId().toString() + "', '0', '0', '0'"  , "slaparoo");
        });
        //                   playerName, UUID, 0, 0, 0
    }

    public void addPoints(Player p, int amount)
    {
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            SQL.set("points", getPoints(p).join() + amount, "UUID", "=" , p.getUniqueId().toString(), "slaparoo");
        });
    }

    public CompletableFuture<Integer> getPoints(Player p) {
        return  CompletableFuture.supplyAsync(()->
                (Integer) SQL.get("points", "UUID", "=" , p.getUniqueId().toString(), "slaparoo"));
    }

    public void addKills(Player p)
    {
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            SQL.set("kills", getKills(p).join() + 1, "UUID", "=", p.getUniqueId().toString(), "slaparoo");
        });
    }

    public CompletableFuture<Integer> getKills(Player p)
    {
        return CompletableFuture.supplyAsync(() ->
                (Integer) SQL.get("kills", "UUID", "=" , p.getUniqueId().toString(), "slaparoo"));
    }

    public void addDeaths(Player p)
    {
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            SQL.set("deaths", getDeaths(p).join() + 1, "UUID", "=" , p.getUniqueId().toString(), "slaparoo");
        });
    }

    public CompletableFuture<Integer> getDeaths(Player p)
    {
        return CompletableFuture.supplyAsync(() ->
                (Integer) SQL.get("deaths", "UUID", "=" , p.getUniqueId().toString(), "slaparoo"));
    }

    public GameSignsAPI getGameSignsApi() {
        return this.gameSignsApi;
    }


    private void runSetup(){
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            LuckPerms api = provider.getProvider();
        }
    }

    public LuckPerms getLuckPerms(){
        return LuckPermsProvider.get();
    }
}
