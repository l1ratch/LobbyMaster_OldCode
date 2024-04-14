package l1ratch.lobbymaster;

import org.bukkit.*;
//import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LobbyMaster extends JavaPlugin implements Listener {
    private static final String prefix = "§7[§aLobbyMaster§7]§8 ";
    private static LobbyMaster instance;
    public static ArrayList<String> allow_cmd;
    public static ArrayList<String> player_cmd;
    public static ArrayList<String> console_cmd;
    public static String disable_cmd;

    // #Создание другого конфига
    // public File file = new File(getDataFolder() + File.separator + "DonConfig.yml");
    // public FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(file);
    // #Получение строк конфига
    // fileConfig.getString("String")

    @Override
    public void onEnable() {
        instance = this;

        // Зона работы с кфг
        final File f = new File(this.getDataFolder() + File.separator + "config.yml");
        if (!f.exists()) {
            saveDefaultConfig();
            log("Файл конфигурации не был найден! Создаю новый...");
        }
        loadConfig(getConfig());

        // Зона запуска элементов плагина
        if (getConfig().getBoolean("Time.Allow")) {
            for (World w : Bukkit.getServer().getWorlds()) {
                w.setTime(getConfig().getLong("Time.SetTime"));
                w.setGameRuleValue("doDaylightCycle", "false");
                log("Имя модифицируемого мира: " + w.getName());
            }
        }

        //registerCommands();
        this.getCommand("lm").setExecutor(new Commands());
        this.getCommand("gmc").setExecutor(new Commands());
        this.getCommand("gms").setExecutor(new Commands());
        this.getCommand("gma").setExecutor(new Commands());
        this.getCommand("gmsp").setExecutor(new Commands());

        Bukkit.getPluginManager().registerEvents(new DonFunctions(), this);
        Bukkit.getPluginManager().registerEvents(new Messager(), this);
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new BlockCMD(), this);
        log("----------------------------------");
        log(" Плагин §aLobbyMaster §8включен!  ");
        log(" Написал: l1ratch                 ");
        log("----------------------------------");
    }

    public static LobbyMaster getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        log("----------------------------------");
        log(" Плагин §aLobbyMaster §8выключен! ");
        log(" Написал: l1ratch                 ");
        log("----------------------------------");
    }

    @EventHandler
    public void FoodLevel(FoodLevelChangeEvent e) {
        if (getConfig().getBoolean("Hunger")){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        // Поменял чтобы кулдауны работали асинхронно
        new Cooldown(p).runTaskTimerAsynchronously(this, 0L, 20L);
        p.getActivePotionEffects().clear();
        if(getConfig().getBoolean("Spawn.Allow")){
            p.teleport(new Location (Bukkit.getWorlds().get(0), getConfig().getInt("Spawn.location.x"), getConfig().getInt("Spawn.location.y"), getConfig().getInt("Spawn.location.z"), getConfig().getInt("Spawn.location.yaw"), getConfig().getInt("Spawn.location.pitch")));
        }
        if (getConfig().getBoolean("StartMessage.Allow")) {
            if (getConfig().getString("StartMessage.join") == null)
                e.setJoinMessage("");
            else {
                e.setJoinMessage(getConfig().getString("StartMessage.join").replaceAll("&", "§").replaceAll("%player%", p.getName()));
            }
        }
        if (getConfig().getBoolean("giveInvisibility"))
            p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999999, 1));
        if (getConfig().getBoolean("giveJump"))
            p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999999, 1));
        if (getConfig().getBoolean("giveBlindNess"))
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 999999999, 1));
        if (getConfig().getBoolean("giveSpeed"))
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 1));
        String gm = getConfig().getString("GameMode");
        if (gm != null)
            p.setGameMode(GameMode.valueOf(gm));
    }


    @EventHandler
    public void JoinCommand(PlayerLoginEvent e){
        Player p = e.getPlayer();
        Bukkit.getScheduler().runTaskLaterAsynchronously(this, () -> {
            if (getInstance().getConfig().getBoolean("JoinCommands.Player.Allow")){
                List<String> list = getInstance().getConfig().getStringList("JoinCommands.Player.cmd");
                //for (int i = 0; i < list.size(); i++) {
                //  p.chat("/" + list.get(i).replaceAll("&", "§").replaceAll("%player%", p.getName()));
                // }
                // Научись работать со stream()
                list.forEach(msg -> p.chat("/" + msg.replaceAll("&", "§").replaceAll("%player%", p.getName())));
            }
            if (getInstance().getConfig().getBoolean("JoinCommands.Console.Allow")){
                List<String> list = getInstance().getConfig().getStringList("JoinCommands.Console.cmd");
                //for(int i = 0; i < list.size(); i++) {
                //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), list.get(i).replaceAll("&", "§").replaceAll("%player%", p.getName()));
                //}
                list.forEach(msg -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), msg.replaceAll("&", "§").replaceAll("%player%", p.getName())));
            }
        }, getInstance().getConfig().getInt("JoinCommands.Ticks"));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (getConfig().getBoolean("StartMessage.Allow")) {
            if (getConfig().getString("StartMessage.quit") == null) {
                e.setQuitMessage("");
            } else {
                e.setQuitMessage(getConfig().getString("StartMessage.quit").replaceAll("&", "§").replaceAll("%player%", p.getName()));
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        if (getConfig().getBoolean("StartMessage.Allow")) {
            if (getConfig().getString("StartMessage.death") == null) {
                e.setDeathMessage("");
            }
            else {
                e.setDeathMessage(getConfig().getString("StartMessage.death").replaceAll("&", "§").replaceAll("%player%", p.getName()));
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (getConfig().getBoolean("Damage"))
            e.setCancelled(true);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (getConfig().getBoolean("Break") && !e.getPlayer().hasPermission("lobbymaster.admin"))
            e.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (getConfig().getBoolean("Place") && !e.getPlayer().hasPermission("lobbymaster.admin"))
            e.setCancelled(true);
    }

    @EventHandler
    public void onFire(BlockBurnEvent e) {
        if (getConfig().getBoolean("Fire"))
            e.setCancelled(true);
    }

    @EventHandler
    public void onFire(BlockSpreadEvent e) {
        if (getConfig().getBoolean("Fire"))
            e.setCancelled(true);
    }

    @EventHandler
    public void onIceMent(BlockFadeEvent e) {
        if (getConfig().getBoolean("IceMelt"))
            e.setCancelled(true);
    }

    @EventHandler
    public void onWeather(WeatherChangeEvent e) {
        if (getConfig().getBoolean("Weather"))
            e.setCancelled(true);
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent e) {
        if (getConfig().getBoolean("Explosions"))
            e.setCancelled(true);
    }

    @EventHandler
    public void noChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (getConfig().getBoolean("Chat") && !e.getPlayer().hasPermission("lobbymaster.admin")) {
            e.setCancelled(true);
            p.sendMessage(getConfig().getString("NoChatText").replaceAll("&", "§"));
        }
    }

    public static String replacer(String what) {
        return what.replaceAll("&", "§");
    }

    public static void loadConfig(FileConfiguration cfg) {
        disable_cmd = replacer(cfg.getString("NoCommandsText"));
        allow_cmd = (ArrayList<String>)cfg.getStringList("allow_cmd");
        player_cmd = (ArrayList<String>)cfg.getStringList("JoinCommands.CommandsPlayer");
        console_cmd = (ArrayList<String>)cfg.getStringList("JoinCommands.CommandsConsole");
    }

    public static void log(String message) {
        Bukkit.getConsoleSender().sendMessage(getPrefix() + message);
    }

    public static String getPrefix() {
        return prefix;
    }

}
