package discord.ernest.bot;

import java.util.List;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Activity.ActivityType;
import net.dv8tion.jda.api.entities.TextChannel;

public class Driver
{
	private static String textChannelName = "help";
	private static MyEventListener me = new MyEventListener();
	
	public static void main(String[] args)
	{
		try
		{
			JDA api = new JDABuilder(AccountType.BOT).setToken("NzE5OTU3MTczMTg2MDY4NTIy.Xt_Bvg.XMOESaY0GpKKySy7g3oM_JGhHug")
					.setActivity(Activity.listening("you...")).build();
			//.setActivity(Activity.listening("")

			
			
			api.addEventListener(me);
			api.awaitReady();
			
			List<TextChannel> channels = api.getTextChannelsByName(textChannelName, true);
			
			me.setTextChannel(channels.get(0));
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
