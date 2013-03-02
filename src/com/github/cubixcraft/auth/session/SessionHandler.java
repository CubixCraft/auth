package com.github.cubixcraft.auth.session;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;

public class SessionHandler {
	private Map<String, String> players = new HashMap<String, String>();
	private Set<String> loggedin = new HashSet<String>();
	
	public int minimalPasswordLength = 3;

	public boolean isLoggedin(String playername) {
		return this.loggedin.contains(playername);
	}
	
	public boolean isLoggedin(Player player) {
		return this.isLoggedin(player.getName());
	}
	
	public boolean isRegistered(String playername) {
		return this.players.containsKey(playername);
	}
	
	public boolean isRegistered(Player player) {
		return this.isRegistered(player.getName());
	}

	public void logout() {
		this.loggedin.clear();
	}
	
	public void logout(String playername) {
		this.loggedin.remove(playername);
	}
	
	public void logout(Player player) {
		this.logout(player.getName());
	}
	
	public void login(String playername) {
		this.loggedin.add(playername);
	}
	
	public void login(Player player) {
		this.login(player.getName());
	}

	public void login(String playername, String password) throws PlayerNotRegisteredException,
			WrongPasswordException, PlayerAlreadyLoggedinException {
		if (!this.checkPassword(playername, password)) return;
		if (this.isLoggedin(playername)) throw new PlayerAlreadyLoggedinException(playername);
		this.login(playername);
	}
	
	public void login(Player player, String password) throws PlayerNotRegisteredException, WrongPasswordException,
			PlayerAlreadyLoggedinException {
		this.login(player.getName(), password);
	}
	
	public boolean checkPassword(String playername, String password) throws PlayerNotRegisteredException,
			WrongPasswordException {
		if (!this.isRegistered(playername)) throw new PlayerNotRegisteredException(playername);
		if (!this.players.get(playername).equals(password)) throw new WrongPasswordException(playername, password);
		return true;
	}
	
	public boolean checkPassword(Player player, String password) throws PlayerNotRegisteredException,
			WrongPasswordException {
		return this.checkPassword(player.getName(), password);
	}
	
	public void register(String playername, String password) throws PlayerAlreadyRegisteredException,
			PasswordTooShortException {
		if (this.isRegistered(playername)) throw new PlayerAlreadyRegisteredException(playername);
		if (password.length() < this.minimalPasswordLength)
			throw new PasswordTooShortException(playername, password, this.minimalPasswordLength);
		this.set(playername, password);
	}
	
	public void register(Player player, String password) throws PlayerAlreadyRegisteredException,
			PasswordTooShortException {
		this.register(player.getName(), password);
	}

	public void changePassword(String playername, String password) throws PlayerNotRegisteredException,
			PasswordTooShortException {
		if (!this.isRegistered(playername)) throw new PlayerNotRegisteredException(playername);
		if (password.length() < this.minimalPasswordLength)
			throw new PasswordTooShortException(playername, password, this.minimalPasswordLength);
		this.set(playername, password);
	}
	
	public void changePassword(Player player, String password) throws PlayerNotRegisteredException,
			PasswordTooShortException {
		this.changePassword(player.getName(), password);
	}
	
	public void unregister(String playername) throws PlayerNotRegisteredException {
		if (!this.isRegistered(playername)) throw new PlayerNotRegisteredException(playername);
		this.players.remove(playername);
		this.logout(playername);
	}
	
	public void unregister(Player player) throws PlayerNotRegisteredException {
		this.unregister(player.getName());
	}
	
	public void set(String playername, String password) {
		this.players.put(playername, password);
	}
	
	public void set(Map<String, String> players) {
		this.players.putAll(players);
	}
	
	public Map<String, String> get() {
		return this.players;
	}

	public void clear() {
		this.players.clear();
	}
}
