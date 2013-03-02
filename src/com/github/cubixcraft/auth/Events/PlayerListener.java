package com.github.cubixcraft.auth.Events;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.github.cubixcraft.auth.Main;

public class PlayerListener implements Listener {
	private Main plugin;
	
	private Set<Player> frozen = new HashSet<Player>();
	public Set<String> allowedCommands = new HashSet<String>(Arrays.asList("login", "register", "forgotPassword"));
	
	public PlayerListener(Main plugin) {
		this.plugin = plugin;
	}
	
	public void freeze(Player player) {
		this.frozen.add(player);
	}

	public void unfreeze(Player player) {
		this.frozen.remove(player);
	}

	public boolean isFrozen(Player player) {
		return this.frozen.contains(player);
	}
	
	private void nag(Player player) {
		player.sendMessage("Bitte logge Dich erst ein.");
		player.sendMessage("Der Befehl lautet /login [password]");
	}
	
	private boolean hasChangedCoordinates(final Location from, final Location to) {
		return !from.getWorld().equals(to.getWorld()) || from.getBlockX() != to.getBlockX()
				|| from.getBlockY() != to.getBlockY() || from.getBlockZ() != to.getBlockZ();
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		String[] command = event.getMessage().substring(1).split(" ");
		
		if (this.isFrozen(event.getPlayer()) && !this.allowedCommands.contains(command[0].toLowerCase())) {
			event.setCancelled(true);
			this.nag(event.getPlayer());
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		if (this.isFrozen(event.getPlayer())) {
			event.setCancelled(true);
			this.nag(event.getPlayer());
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		if (this.isFrozen(event.getPlayer())) {
			event.setCancelled(true);
			this.nag(event.getPlayer());
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (this.isFrozen(event.getPlayer())) {
			event.setCancelled(true);
			this.nag(event.getPlayer());
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		if (this.isFrozen(event.getPlayer())) {
			event.setCancelled(true);
			this.nag(event.getPlayer());
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onInventoryClick(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) return;
		Player player = (Player) event.getWhoClicked();
		
		if (this.isFrozen(player)) {
			event.setCancelled(true);
			this.nag(player);
		}

	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		
		if (this.isFrozen(player)) {
			event.setCancelled(true);
			this.nag(player);
		}
		
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		
		if (this.isFrozen(player)) {
			event.setCancelled(true);
			this.nag(player);
		}
		
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerMove(PlayerMoveEvent event) {
		if (this.isFrozen(event.getPlayer()) && hasChangedCoordinates(event.getFrom(), event.getTo())) {
			event.setCancelled(true);
			event.getPlayer().teleport(event.getFrom());
			this.nag(event.getPlayer());
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityDamage(EntityDamageEvent event) {
		if (!(event.getEntity() instanceof Player)) return;
		Player player = (Player) event.getEntity();
		
		if (this.isFrozen(player)) {
			event.setCancelled(true);
			this.nag(player);
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityTarget(EntityTargetEvent event) {
		if (!(event.getTarget() instanceof Player)) return;
		Player player = (Player) event.getTarget();
		
		if (this.isFrozen(player)) {
			event.setCancelled(true);
			this.nag(player);
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onFoodLevelChange(FoodLevelChangeEvent event) {
		if (!(event.getEntity() instanceof Player)) return;
		Player player = (Player) event.getEntity();
		
		if (this.isFrozen(player)) {
			event.setCancelled(true);
			this.nag(player);
		}
	}
}
