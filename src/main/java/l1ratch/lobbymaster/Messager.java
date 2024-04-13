package l1ratch.lobbymaster;

import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Messager implements Listener {
    @EventHandler
    public void MessageJoin(PlayerJoinEvent e) {
        if (LobbyMaster.getInstance().getConfig().getBoolean("JoinMessage.Allow")){
            Player p = e.getPlayer();
            List<String> list;
            list = LobbyMaster.getInstance().getConfig().getStringList("JoinMessage.Message");
            for (String s : list)
                p.sendMessage(s.replaceAll("&", "§").replaceAll("%player%", p.getName()));
        }
    }
}
