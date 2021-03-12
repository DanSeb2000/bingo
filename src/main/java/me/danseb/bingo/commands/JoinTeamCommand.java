package me.danseb.bingo.commands;

import me.danseb.bingo.Core;
import me.danseb.bingo.game.GameManager;
import me.danseb.bingo.game.Teams;
import me.danseb.bingo.inventories.TeamInv;
import me.danseb.bingo.utils.Language;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The /team command
 * Uses args if you set them manually to select
 * a team or not to open an inventory that allows
 * you to select also a team.
 */
public class JoinTeamCommand implements CommandExecutor {
    private final GameManager gameManager;

    public JoinTeamCommand (){
        gameManager = Core.getInstance().getGameManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0){
            if (!(sender instanceof Player)){
                sender.sendMessage(Language.PLAYER_COMMAND.getMessage());
                return true;
            }
            TeamInv.TEAM_INV.open((Player) sender);
            return true;
        } else if (args.length == 1) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Teams team = Teams.fromName(args[0]);
                if (gameManager.setPlayerTeam(player, team)){
                    sender.sendMessage(Language.CHANGED_TEAM.getMessage()
                            .replace("%team%", team.getColored()));
                }
                else{
                    sender.sendMessage(Language.JOIN_NOT_A_TEAM.getMessage()
                            .replace("%string%", args[0]));
                }
            }
        }
        return true;
    }

}
