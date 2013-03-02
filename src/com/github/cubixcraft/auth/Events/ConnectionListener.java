package com.github.cubixcraft.auth.Events;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.cubixcraft.auth.Main;

public class ConnectionListener implements Listener {
	private Main plugin;
	
	private Set<Player> frozen = new HashSet<Player>();
	
	public ConnectionListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerLogin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		
		if (this.plugin.session.isLoggedin(player)) {
			this.plugin.getServer().broadcast("Jemand hat versucht, sich als ein eingeloggter Spieler anzumelden.",
					"auth.admin");
			event.disallow(Result.KICK_FULL, "Jemand ist bereits unter diesem Namen eingeloggt.");
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		if (this.plugin.session.isRegistered(player)) {
			this.plugin.freeze(player);
			player.sendMessage("Bitte logge dich mit /login [password] ein");
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerQuit(PlayerQuitEvent event) {
		this.plugin.unfreeze(event.getPlayer());
		this.plugin.session.logout(event.getPlayer());
	}

}
