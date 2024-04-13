package l1ratch.lobbymaster;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class DonFunctions implements Listener {
    @EventHandler
    public void DonateCMD(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(LobbyMaster.getInstance().getConfig().getBoolean("DonateFly")){
            if(p.hasPermission("LinksLobby.donate")){
                p.setAllowFlight(true);
                if(LobbyMaster.getInstance().getConfig().getString("FlyAllow") != null){
                    p.sendMessage(LobbyMaster.getInstance().getConfig().getString("FlyAllow").replaceAll("&", "§"));
                }
            } else {
                p.setAllowFlight(false);
                if(LobbyMaster.getInstance().getConfig().getString("NoFly") != null){
                    p.sendMessage(LobbyMaster.getInstance().getConfig().getString("NoFly").replaceAll("&", "§"));
                }
            }
        }
        if(p.hasPermission("LinksLobby.admin")) {
            p.setAllowFlight(true);
        }
    }
}
