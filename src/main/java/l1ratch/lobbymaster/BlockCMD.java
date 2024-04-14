package l1ratch.lobbymaster;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class BlockCMD implements Listener {
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (LobbyMaster.getInstance().getConfig().getBoolean("Commands") && !e.getPlayer().hasPermission("lobbymaster.admin")){
            if (!LobbyMaster.allow_cmd.contains(e.getMessage().split(" ")[0].replace("/", ""))) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(LobbyMaster.disable_cmd);
            }
        }
    }
}
