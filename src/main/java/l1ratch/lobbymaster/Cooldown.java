package l1ratch.lobbymaster;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Cooldown extends BukkitRunnable {
    Player p;

    public int count = 0;

    public Cooldown(Player p) {
        this.p = p;
    }

    @Override
    public void run() {
        switch(this.count) {
            case 1: {
                break;
            }
            case 2: {
                break;
            }
            case 3: {
                break;
            }
            case 4: {
                break;
            }
            case 5: {
                this.count = 0;
                cancel();
                break;
            }
        }
        count++;
    }

}
