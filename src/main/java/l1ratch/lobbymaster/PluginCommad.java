package l1ratch.lobbymaster;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PluginCommad implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;
        if (player.isOp() || player.hasPermission("lobbymaster.admin")){
            if (args.length == 0) {
                sender.sendMessage("§a§lДоступные аргументы: §6§l/LobbyMaster <reload | help | setspawn>");
                return true;
            }
            if (args[0].equalsIgnoreCase("reload")) {
                LobbyMaster.getInstance().reloadConfig();
                sender.sendMessage("§c§lКонфиг плагина LobbyMaster перезагружен!");
                return true;
            }
            if (args[0].equalsIgnoreCase("help")) {
                player.sendMessage("§7§l[§c§lLinksLobby§7§l]§a§l > §6§lПлагин для настройки лобби!");
                player.sendMessage("§a§l> §6Написал: §cl1ratch");
                player.sendMessage("§a§l> §6Право §clobbymaster.admin §6- отменяет действия пунктов(Break, Place, Chat, Commands), дает Fly и доступ к админ-командам");
                player.sendMessage("§a§l> §c/LobbyMaster reload|help|setspawn §6или §c/ll reload|help|setspawn");
            }
            if (args[0].equalsIgnoreCase("setspawn")) {
                LobbyMaster.getInstance().getConfig().set("Spawn.location.x", player.getLocation().getX());
                LobbyMaster.getInstance().getConfig().set("Spawn.location.y", player.getLocation().getY());
                LobbyMaster.getInstance().getConfig().set("Spawn.location.z", player.getLocation().getZ());
                LobbyMaster.getInstance().getConfig().set("Spawn.location.yaw", player.getLocation().getYaw());
                LobbyMaster.getInstance().getConfig().set("Spawn.location.pitch", player.getLocation().getPitch());
                LobbyMaster.getInstance().saveConfig();
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &aТочка спавна установлена"));
            }
            if(args[0].equalsIgnoreCase("l1ratch")){
                sender.sendMessage("§c§lCreated by l1ratch!");
            }
        } else {
            player.sendMessage("§cУ вас нет прав!");
        }
        return true;
    }

}
