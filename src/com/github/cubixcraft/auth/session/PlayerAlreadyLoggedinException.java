package com.github.cubixcraft.auth.session;

public class PlayerAlreadyLoggedinException extends Exception {
	private String playername;

	public PlayerAlreadyLoggedinException(String playername) {
		super("Player `" + playername + "` is already logged in");
		this.playername = playername;
	}
	
	public String getPlayername() {
		return this.playername;
	}
}
