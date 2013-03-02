package com.github.cubixcraft.auth.session;

public class PlayerNotRegisteredException extends Exception {
	private String playername;

	public PlayerNotRegisteredException(String playername) {
		super("There is no such player as `" + playername + "` registered");
		this.playername = playername;
	}
	
	public String getPlayername() {
		return this.playername;
	}
}
