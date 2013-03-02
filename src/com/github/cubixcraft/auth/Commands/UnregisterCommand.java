package com.github.cubixcraft.auth.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.cubixcraft.auth.Main;
import com.github.cubixcraft.auth.session.PlayerNotRegisteredException;

public class UnregisterCommand implements CommandExecutor {
	private Main plugin;
	
	public UnregisterCommand(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) return false;
		Player player = (Player) sender;

		try {
			this.plugin.session.unregister(player);
			player.sendMessage("Bye Bye");
		} catch (PlayerNotRegisteredException e) {
			player.sendMessage("Du bist nicht registriert");
		}
		
		return true;
	}
}