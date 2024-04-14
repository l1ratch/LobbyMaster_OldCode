package l1ratch.lobbymaster;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class DonFunctions implements Listener {
    @EventHandler
    public void DonateCMD(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(LobbyMaster.getInstance().getConfig().getBoolean("DonateFunctions.Allow")){
            if(p.hasPermission("lobbymaster.donate")){
                p.setAllowFlight(true);
                if(LobbyMaster.getInstance().getConfig().getString("DonateFunctions.PremNotice") != null){
                    p.sendMessage(LobbyMaster.getInstance().getConfig().getString("DonateFunctions.PremNotice").replaceAll("&", "§"));
                }
            } else {
                p.setAllowFlight(false);
                if(LobbyMaster.getInstance().getConfig().getString("DonateFunctions.NoPermMessage") != null){
                    p.sendMessage(LobbyMaster.getInstance().getConfig().getString("DonateFunctions.NoPermMessage").replaceAll("&", "§"));
                }
            }
        }
        if(p.hasPermission("lobbymaster.admin")) {
            p.setAllowFlight(true);
        }
    }
}
