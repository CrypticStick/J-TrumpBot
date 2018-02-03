package com.stickles.discord.trumpbot;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;

import com.stickles.discord.trumpbot.Command;

import net.dv8tion.jda.client.exceptions.VerificationLevelException;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.InsufficientPermissionException;

public class Commands {
	
	public static void sendMessage(MessageReceivedEvent ev, String msg) {
		try {
			ev.getChannel().sendMessage(msg).queue();
		} catch (InsufficientPermissionException | VerificationLevelException e) {
			ev.getAuthor().openPrivateChannel().queue((channel) -> channel
					.sendMessage(String.format(
							"%s currently does not have permission to speak in %s, %s.\n"
									+ "If you feel this is a mistake, please contact the server administrator.",
							TrumpBot.jda.getSelfUser().getName(), ev.getGuild().getName(), ev.getChannel().getName()))
					.queue());
		} catch (IllegalArgumentException e) {
			sendMessage(ev, String.format("%s, I can't send an empty message!", ev.getAuthor().getAsMention()));
		}
	}

	@Command(Name = "help",
			Summary = "Lists information about available commands",
			Syntax = "help [optional:command]")
	public static void IcanHELPyoU(MessageReceivedEvent e, ArrayList<String> args) {
		
		String helpMsg = "";
		
		Method[] commands = Commands.class.getMethods();
		for (Method m : commands) {
		        if (m.isAnnotationPresent(Command.class)) {
		        	
		        	String Name = "";
		    		String formattedAliases = "";
		    		String Summary = "";
		    		String SpecialPerms = "";
		    		String Syntax = "";
		    		boolean matchingAlias = false;
		    		
		        	Name = m.getAnnotation(Command.class).Name();
		        	String[] Aliases = m.getAnnotation(Command.class).Aliases();
		        	for (String a : Aliases) {
		        		if (args.contains(a)) matchingAlias = true;
		        	}
		        	if (args.isEmpty() || args.contains(Name) || matchingAlias) {
		        	if (m.getAnnotation(Command.class).Aliases().length > 0)
		        		formattedAliases = " (also " + String.join(", ", Aliases) + ")";
		        	Summary = m.getAnnotation(Command.class).Summary();
		        	if (!m.getAnnotation(Command.class).SpecialPerms().isEmpty())
		        		SpecialPerms = " **<" + m.getAnnotation(Command.class).SpecialPerms() + ">**";
		        	if (!m.getAnnotation(Command.class).Syntax().isEmpty())
		        		Syntax = " ~ `" + m.getAnnotation(Command.class).Syntax() + "`";
		        	helpMsg += String.format("`%s%s%s` - %s%s%s\n",TrumpBot.prefix,Name,formattedAliases,Summary,SpecialPerms,Syntax);
		        	}
		        }
		}
		if (helpMsg == "") sendMessage(e,String.format("Sorry %s, the requested command(s) do not exist.", e.getAuthor().getAsMention()));
		else sendMessage(e, helpMsg);
	}
	
	
	@Command(Name = "echo",
			Aliases = {"repeat","copy"},
			Summary = "I, Donald J. Trump, will copy what you say",
			Syntax = "echo [command]")
	public static void wowAnECHOCommand(MessageReceivedEvent e, ArrayList<String> args) {
		
		sendMessage(e,String.join(" ", args));
		e.getMessage().delete().queue();
	}
	
	
	@Command(Name = "test", 
			Summary = "My personal testing command while implementing features. TESTING TESTING 123",
			SpecialPerms = "Only I can run this :P")
	public static void thisIsATEST123(MessageReceivedEvent e, ArrayList<String> args) {
		
		if (!e.getAuthor().getId().equals("215507031375740928")) return;
		
		sendMessage(e,"If you see this, something great has happened.");
		e.getMessage().delete().queue();
	}
	
	
	@Command(Name = "random", 
			Summary = "Provides a random number between 0 and 100")
	public static void superRanDOmLOL(MessageReceivedEvent e, ArrayList<String> args) {
		
		Random rnd = new Random();
        Integer randomInt = rnd.nextInt(100);
        sendMessage(e,randomInt.toString());
	}
	
	@Command(Name = "b",
			Summary = ":b:oi :b:e :b:lessed with the auto :b: converter",
			Syntax = "b [enable | disable]")
	public static void BisFOrBUPBoiXDXDBONALDBUMPHERE(MessageReceivedEvent e, ArrayList<String> args) {
		
		sendMessage(e,"Coming soon to theaters near YOU.");
		//e.getMessage().delete().queue();
		
	}

}
