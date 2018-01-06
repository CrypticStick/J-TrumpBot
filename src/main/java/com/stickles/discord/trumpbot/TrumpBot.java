package com.stickles.discord.trumpbot;

import java.io.Console;

import javax.security.auth.login.LoginException;

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
	// static String token = "MzIxMDk3NjA0MTMxNzE3MTIw.DBt2Mg.C2XjfTCAjXAZB7tGGfFqDmgm-rc"; //TrumpBot's special token
	static String token = "MzkzMTUyNTM4MzA5ODIwNDE2.DRxnZw.i0KWff0hXeAH3khJN1XsfqFnGew"; // TrumpBeta's special token
	static String prefix;
	static String mention;
	static boolean running = true;
	static int timeoutSeconds = 10;

	public static void main(String[] args) throws LoginException, RateLimitedException, InterruptedException {

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
