package l1ratch.lobbymaster;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class TabLMcmd extends TabLogic {

    @Override
    protected List<String> getArguments(CommandSender sender, Command command, String alias, String[] args) {
        return Arrays.asList("help", "setspawn", "reload");
    }
}

