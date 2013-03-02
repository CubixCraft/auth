package com.github.cubixcraft.auth.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.cubixcraft.auth.Main;
import com.github.cubixcraft.auth.session.PlayerAlreadyLoggedinException;
import com.github.cubixcraft.auth.session.PlayerNotRegisteredException;
import com.github.cubixcraft.auth.session.WrongPasswordException;

public class LoginCommand implements CommandExecutor {
	private Main plugin;
	
	public LoginCommand(Main plugin) {
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
			this.plugin.session.login(player, password);
			this.plugin.unfreeze(player);
			sender.sendMessage("Herzlich Willkommen");
		} catch (PlayerNotRegisteredException e) {
			player.sendMessage("Du bist nicht registriert");
			player.sendMessage("Nutze /register um dich zu registrieren");
		} catch (WrongPasswordException e) {
			player.sendMessage("Falsches Passwort");
		} catch (PlayerAlreadyLoggedinException e) {
			player.sendMessage("Du bist breits eingeloggt");
			player.sendMessage("Nutze /logout um dich auszuloggen");
		}
		
		return true;
	}
}