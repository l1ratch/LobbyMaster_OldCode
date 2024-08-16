package l1ratch.lobbymaster;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static l1ratch.lobbymaster.LobbyMaster.getInstance;

    public class Commands implements CommandExecutor {

        private final LobbyMaster plugin = null;
        

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("lm")) {
            
            if (args.length == 0) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (player.hasPermission("lobbymaster.admin")) {
                        sender.sendMessage(getInstance().txtPrefix + "§a§lДоступные аргументы: §6§l/LobbyMaster <reload | help | setspawn>");
                        return true;
                    } else {
                        player.sendMessage(getInstance().getConfig().getString("noPerm").replaceAll("&", "§"));
                    }
                } else {
                    sender.sendMessage("§a§lДоступные аргументы: §6§l/LobbyMaster <reload>");
                    return true;
                }
                return true;
            }
            
            if (args[0].equalsIgnoreCase("reload")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (player.hasPermission("lobbymaster.admin")) {
                        plugin.reloadConfig();
                        player.sendMessage(getInstance().txtPrefix + "§c§lКонфиг плагина LobbyMaster перезагружен!");
                    } else {
                        player.sendMessage(getInstance().getConfig().getString("noPerm").replaceAll("&", "§"));
                    }
                } else {
                    plugin.reloadConfig();
                    sender.sendMessage(getInstance().txtPrefix + "§c§lКонфиг плагина LobbyMaster перезагружен!");
                }
                return true;
            }

            if (args[0].equalsIgnoreCase("help")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (player.hasPermission("lobbymaster.admin")) {
                        player.sendMessage("§7§l[§c§lLinksLobby§7§l]§a§l > §6§lПлагин для настройки лобби!");
                        player.sendMessage("§a§l> §6Написал: §cl1ratch");
                        player.sendMessage("§a§l> §6Право §clobbymaster.admin §6- отменяет действия пунктов(Break, Place, Chat, Commands), дает Fly и доступ к админ-командам");
                        player.sendMessage("§a§l> §c/LobbyMaster reload|help|setspawn §6или §c/ll reload|help|setspawn");
                    } else {
                        player.sendMessage(getInstance().getConfig().getString("noPerm").replaceAll("&", "§"));
                    }
                }
            }
            if (args[0].equalsIgnoreCase("setspawn")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (player.hasPermission("lobbymaster.admin")) {
                        getInstance().getConfig().set("Spawn.location.x", player.getLocation().getX());
                        getInstance().getConfig().set("Spawn.location.y", player.getLocation().getY());
                        getInstance().getConfig().set("Spawn.location.z", player.getLocation().getZ());
                        getInstance().getConfig().set("Spawn.location.yaw", player.getLocation().getYaw());
                        getInstance().getConfig().set("Spawn.location.pitch", player.getLocation().getPitch());
                        getInstance().saveConfig();
                        player.sendMessage(getInstance().txtPrefix + ChatColor.translateAlternateColorCodes('&', "&8» &aТочка спавна установлена"));
                    } else {
                        player.sendMessage(getInstance().getConfig().getString("noPerm").replaceAll("&", "§"));
                    }
                }
            }
        }

        
        if (!(sender instanceof Player)) {
            sender.sendMessage(getInstance().txtPrefix + "§c§lOnly players can use this command.");
            return true;
        }

        Player player = (Player) sender;
        if (player.hasPermission("lobbymaster.admin")){
            if (command.getName().equalsIgnoreCase("gmc")) {
                player.setGameMode(GameMode.CREATIVE);
                player.sendMessage(getInstance().txtPrefix + "§7Режим §cКреатива §7включен!");
                return true;
            }
            if (command.getName().equalsIgnoreCase("gms")) {
                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage(getInstance().txtPrefix + "§7Режим §aВыживания §7включен!");
                return true;
            }
            if (command.getName().equalsIgnoreCase("gma")) {
                player.setGameMode(GameMode.ADVENTURE);
                player.sendMessage(getInstance().txtPrefix + "§7Режим §9Приключения §7включен!");
                return true;
            }
            if (command.getName().equalsIgnoreCase("gmsp")) {
                player.setGameMode(GameMode.SPECTATOR);
                player.sendMessage(getInstance().txtPrefix + "§7Режим §6Наблюдателя §7включен!");
                return true;
            }
        } else{
            player.sendMessage(getInstance().getConfig().getString("noPerm").replaceAll("&", "§"));
        }

        return false;
    }
}
