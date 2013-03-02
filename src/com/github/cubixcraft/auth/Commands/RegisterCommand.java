package com.github.cubixcraft.auth.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.cubixcraft.auth.Main;
import com.github.cubixcraft.auth.session.PasswordTooShortException;
import com.github.cubixcraft.auth.session.PlayerAlreadyRegisteredException;

public class RegisterCommand implements CommandExecutor {
	private Main plugin;
	
	public RegisterCommand(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) return false;
		
		if (args.length != 1) {
			sender.sendMessage("Kein Passwort angegeben");
			return false;
		}
		
		Player player = (Player) sender;
		String password = args[0];

		try {
			this.plugin.session.register(player, password);
			player.sendMessage("Herzliche Willkommen");
		} catch (PlayerAlreadyRegisteredException e) {
			player.sendMessage("Du bist bereits registriert");
		} catch (PasswordTooShortException e) {
			player.sendMessage("Dein Passwort ist zu kurz");
		}
		
		return true;
	}
}