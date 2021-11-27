package main;

import me.vagdedes.mysql.database.MySQL;
import me.vagdedes.mysql.database.SQL;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {


    @Override
    public void onEnable(){
        runStartupConnnection();
    }

    @Override
    public void onDisable(){
    }

    private void runStartupConnnection(){
            MySQL.setConnection("23.158.176.10", "u9881_XDwyt9wYE5", "L+3+90U^6y8oDMZmg2M69Xgp", "s9881_game", "3306");
            getLogger().info("Database Connected.");
            createTable();
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
        //                   playerName,   UUID, 0, 0, 0
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

}
