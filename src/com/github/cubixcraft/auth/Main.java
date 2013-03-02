package com.github.cubixcraft.auth;

import java.util.Map.Entry;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.cubixcraft.auth.Commands.ChangePWCommand;
import com.github.cubixcraft.auth.Commands.LoginCommand;
import com.github.cubixcraft.auth.Commands.RegisterCommand;
import com.github.cubixcraft.auth.Commands.UnregisterCommand;
import com.github.cubixcraft.auth.Events.ConnectionListener;
import com.github.cubixcraft.auth.Events.PlayerListener;
import com.github.cubixcraft.auth.session.SessionHandler;

public class Main extends JavaPlugin {
	private PluginManager pm;
	
	public SessionHandler session = new SessionHandler();
	private PlayerListener playerListener;

	public void onEnable() {
		pm = this.getServer().getPluginManager();
		
		this.playerListener = new PlayerListener(this);
		pm.registerEvents(this.playerListener, this);
		pm.registerEvents(new ConnectionListener(this), this);

		this.getCommand("login").setExecutor(new LoginCommand(this));
		this.getCommand("register").setExecutor(new RegisterCommand(this));
		this.getCommand("unregister").setExecutor(new UnregisterCommand(this));
		this.getCommand("changepw").setExecutor(new ChangePWCommand(this));
		
		load();
	}
	
	public void onDisable() {
		save();
	}

	public void load() {
		this.reloadConfig();
		FileConfiguration config = this.getConfig();
		
		this.session.clear();
		for (String player : config.getConfigurationSection("passwords").getKeys(false))
			this.session.set(player, config.getString("passwords." + player));

	}
	
	public void save() {
		FileConfiguration config = this.getConfig();
		
		for (Entry<String, String> player : this.session.get().entrySet())
			config.set("passwords." + player.getKey(), player.getValue());
		
		this.saveConfig();
	}
	
	public void freeze(Player player) {
		this.playerListener.freeze(player);
	}
	
	public void unfreeze(Player player) {
		this.playerListener.unfreeze(player);
	}
}
