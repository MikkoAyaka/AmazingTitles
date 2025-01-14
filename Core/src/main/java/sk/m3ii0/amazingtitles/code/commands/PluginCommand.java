package sk.m3ii0.amazingtitles.code.commands;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import sk.m3ii0.amazingtitles.api.objects.AmazingCreator;
import sk.m3ii0.amazingtitles.api.objects.types.ActionType;
import sk.m3ii0.amazingtitles.code.AmazingTitles;
import sk.m3ii0.amazingtitles.code.colors.ColorTranslator;
import sk.m3ii0.amazingtitles.code.commands.dispatcher.TitleDispatcher;
import sk.m3ii0.amazingtitles.code.notifications.BarNotification;
import sk.m3ii0.amazingtitles.code.stats.Metrics;
import sk.m3ii0.amazingtitles.code.utils.StringUtils;

import java.util.Collections;
import java.util.List;

public class PluginCommand implements CommandExecutor, TabExecutor {
	
	@Override
	public List<String> onTabComplete(CommandSender s, Command cmd, String label, String[] args) {
		
		int current = args.length;
		if (current == 0) return Collections.emptyList();
		String using = args[current-1];
		
		/*
		 * Action Selection
		 * */
		if (current == 1) {
			return CommandUtils.copyPartialMatches(using, ActionType.toIterable());
		}
		
		/*
		* Player Selection
		* */
		if (current == 2) {
			return CommandUtils.copyPartialMatches(using, CommandUtils.buildPlayerParams(using));
		}

		/*
		* Message
		* */
		if (args[0].equalsIgnoreCase("MESSAGE"))  {
			return StringUtils.of("<Visit wiki how to build message>");
		}

		/*
		* Notification
		* */
		if (args[0].equalsIgnoreCase("NOTIFICATION")) {
			if (current == 3) {
				return StringUtils.of("<NotificationSymbol>");
			} else if (current == 4) {
				return StringUtils.of("<Number(Duration-InSeconds)>");
			} else return StringUtils.of("<NotificationMessage>");
		}

		/*
		* Animation Selection
		* */
		if (current == 3) {
			return CommandUtils.copyPartialMatches(using, AmazingTitles.getCustomComponents().keySet());
		}

		if (current == 4) {
			return StringUtils.of("<Number-Delay>");
		}

		if (current == 5) {
			return StringUtils.of("<Number-Duration(InSeconds)>");
		}

		/*
		* Animation arguments
		* */
		String animation_name = args[2];
		if (!AmazingTitles.getCustomComponents().containsKey(animation_name)) {
			return StringUtils.of("<Invalid Animation>");
		}
		AmazingCreator creator = AmazingTitles.getCustomComponents().get(animation_name);
		return CommandUtils.copyPartialMatches(using, creator.getComplete(current-6));
	}
	
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {

		if (args.length < 2) {
			sendHelpMessage(s);
			return true;
		}

		/*
		* 0-receivers 1-type 2-animation 3+ params
		* */

		List<Player> receivers = CommandUtils.playersFromOperator(args[1]);
		String animationTypes;
		ActionType actionType;
		try {
			actionType = ActionType.valueOf(args[0].toUpperCase());
			if (actionType == ActionType.MESSAGE) {
				StringBuilder text = new StringBuilder();
				for (int i = 2; i < args.length; i++) {
					text.append(args[i]).append(" ");
				}
				text = new StringBuilder(text.toString().replaceAll(" $", ""));
				BaseComponent[] message = TitleDispatcher.getMessageFromRaw(text.toString());
				for (Player p : receivers) {
					p.spigot().sendMessage(message);
				}
				return true;
			}
			if (actionType == ActionType.NOTIFICATION) {
				String symbol = ColorTranslator.colorize(args[2]);
				int duration = Integer.parseInt(args[3]);
				StringBuilder text = new StringBuilder();
				for (int i = 4; i < args.length; i++) {
					text.append(args[i]).append(" ");
				}
				text = new StringBuilder(text.toString().replaceAll(" $", ""));
				BarNotification notification = BarNotification.create(symbol, text.toString(), duration);
				AmazingTitles.setNotificationFor(notification, receivers);
				return true;
			}
			animationTypes = args[2];
			if (!AmazingTitles.getCustomComponents().containsKey(animationTypes)) {
				sendHelpMessage(s);
				return true;
			}
		} catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
			sendHelpMessage(s);
			return true;
		}
		
		AmazingCreator creator = AmazingTitles.getCustomComponents().get(animationTypes);
		int animationArgs = args.length - 3;
		String[] animation = new String[animationArgs];
		int counter = 0;
		for (int i = 3; i < args.length; i++) {
			animation[counter] = args[i];
			++counter;
		}
		
		if (animationArgs-2 < creator.getMinimum()) {
			sendHelpMessage(s);
			return true;
		}

		/*
		* Builder
		* */
		TitleDispatcher.asyncDispatch(s, actionType, animationTypes, receivers, animation);
		AmazingTitles.getMetrics().addCustomChart(new Metrics.SingleLineChart("used_commands", () -> 1));
		return false;
	}

	private void sendHelpMessage(CommandSender s) {
		s.sendMessage("§cAT §7-> §4Invalid command format... (Follow instructions in tab completer)");
	}
	
}
