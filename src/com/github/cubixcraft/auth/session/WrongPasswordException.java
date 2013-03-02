package com.github.cubixcraft.auth.session;

public class WrongPasswordException extends Exception {
	public WrongPasswordException(String playername, String password) {
		super("Player `" + playername + "` used wrong password `" + password + "`");
	}
}
