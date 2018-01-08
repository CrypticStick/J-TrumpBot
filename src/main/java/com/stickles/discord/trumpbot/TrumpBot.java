package com.stickles.discord.trumpbot;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.security.auth.login.LoginException;

import java.io.InputStream;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDA.Status;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Game.GameType;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

public class TrumpBot {

	public static JDA jda;
	
	static InputStream tokenIS = TrumpBot.class.getResourceAsStream("/token"); 
	static BufferedReader reader = new BufferedReader(new InputStreamReader(tokenIS));
	static String token;
	static String prefix;
	static String mention;
	static boolean running = true;
	static int timeoutSeconds = 10;

	public static void main(String[] args) throws LoginException, RateLimitedException, InterruptedException {
			try {
				token = reader.readLine();
			} catch (IOException e) {
				System.err.println("The file '/bin/token.txt' does not exist.");
				System.exit(1);
			}
		
		Console c = System.console();
		if (c == null) {
			System.err.println("No console.");
			System.exit(1);
		}

		jda = new JDABuilder(AccountType.BOT) // Configure the bot before starting it
				.setToken(token).setStatus(OnlineStatus.ONLINE)
				.setGame(Game.of(GameType.DEFAULT, "Build That Wall! (Type !$help)"))
				.addEventListener(new EventListener())
				.addEventListener(new CommandHandler())
				.buildBlocking(); // Creates the bot on a new thread

		long endTime = System.currentTimeMillis() + (timeoutSeconds * 1000);
		while (jda.getStatus() != Status.CONNECTED) { // Attempts to connect for 10 seconds - otherwise TIMEOUT
			if (System.currentTimeMillis() < endTime) {
				System.out.print("\r" + jda.getStatus().toString());
				Thread.sleep(500);
			}

			else {
				System.out.println(
						"Error: the bot failed to connect. This could either be because the token is incorrect, or because Stickles completely broke the code :P");
				throw new InterruptedException("Connection timeout.");
			}
		}

		prefix = "!$";
		mention = String.format("<@%s>", jda.getSelfUser().getId());

		//// TRUMPBOT LOCAL TERMINAL ////

		while (running) {
			String command = c.readLine(String.format("%s>", jda.getSelfUser().getName()));

			switch (command) {

			case "shutdown":
			case "quit":
			case "stop":
			case "end":
			case "exit":
			case "close":
				jda.getPresence().setPresence(OnlineStatus.DO_NOT_DISTURB,
						Game.of(GameType.DEFAULT, "Shutting down..."));
				System.out.println("Shutting down...\n");
				System.exit(0);
				break;

			default:
				System.out.println(String.format("command '%s' not recognized.", command));

			}

		}
	}
}
