package com.github.cubixcraft.auth.session;

public class PasswordTooShortException extends Exception {
	public PasswordTooShortException(String playername, String password) {
		super("The password `" + password + "` of player `" + playername + "` is too short");
	}
	
	public PasswordTooShortException(String playername, String password, int minimalLength) {
		super("The password `" + password + "` of player `" + playername + "` is too short (at least " + minimalLength
				+ "are needed)");
	}
}
