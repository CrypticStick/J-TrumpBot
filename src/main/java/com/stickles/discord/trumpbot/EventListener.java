package com.stickles.discord.trumpbot;

import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class EventListener extends ListenerAdapter {

	@Override
	public void onReady(ReadyEvent e) {
		System.out.println(String.format("[%s#%s] I'm online!", e.getJDA().getSelfUser().getName(),
				e.getJDA().getSelfUser().getDiscriminator()));
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent e) {

		if (e.getAuthor().isBot())
			return;
		// Do whatever I want now!
	}

}
