package l1ratch.lobbymaster;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public abstract class TabLogic implements TabCompleter {

    // Метод, который наследники должны реализовать для предоставления возможных аргументов
    protected abstract List<String> getArguments(CommandSender sender, Command command, String alias, String[] args);

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 0) {
            return new ArrayList<>();
        }

        List<String> arguments = getArguments(sender, command, alias, args);
        List<String> result = new ArrayList<>();

        for (String a : arguments) {
            if (a.toLowerCase().startsWith(args[0].toLowerCase())) {
                result.add(a);
            }
        }

        return result;
    }
}
