package com.github.cubixcraft.auth.session;

public class PlayerAlreadyRegisteredException extends Exception {
	private String playername;

	public PlayerAlreadyRegisteredException(String playername) {
		super("Player `" + playername + "` is already logged in");
		this.playername = playername;
	}
	
	public String getPlayername() {
		return this.playername;
	}
}
