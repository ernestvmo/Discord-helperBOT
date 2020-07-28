package discord.ernest.bot;


import java.io.Serializable;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

public class DiscordBOT implements Serializable
{
	// TODO change if needed
	private static long textChannelName = 732933660118089769L;
	// TODO change if needed
	// 
	private static long category = 732933592102993961L;
	// TODO change if needed
	private static long guildID = 723082805235548273L;
	private static MyEventListener me = new MyEventListener();
	
	
	public DiscordBOT(int language)
	{
		try
		{
			JDA api = new JDABuilder(AccountType.BOT).setToken("***")
					.build();

			api.addEventListener(me);
			api.awaitReady();
			
//			List<TextChannel> channels = api.getTextChannelsByName(textChannelName, true);
			
			me.setLanguage(language);
			me.setGuild(api.getGuildById(guildID));
			me.setInfos(textChannelName, category);
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
	}
}
