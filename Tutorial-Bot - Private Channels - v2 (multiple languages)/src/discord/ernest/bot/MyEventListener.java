package discord.ernest.bot;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MyEventListener extends ListenerAdapter {
	
	private Guild g;
	private Category category;
	private TextChannel helpChannel;
	Map<Long, Ticket> tickets = new HashMap<Long, Ticket>(); // TODO change to HashMap
	List<Long> deleteList = new ArrayList<>();
	
	String[] languageInUse;
	StringBuilder languageSBInUse;
	List<Long> languageList = new ArrayList<>();
	
	private final String no_reaction = "\u274c";
	private final String yes_reaction = "\u2705";

	private final String us_flag = "🇺🇸";
	private final String fr_flag = "🇫🇷";
	
	public void onPrivateMessageReceived(PrivateMessageReceivedEvent e)
	{
		if (e.getAuthor().isBot())
		{
			return;
		}
		
		Message message = e.getMessage();
		String content = message.getContentRaw();
		
		if (content.contains("!solve") || content.contains("!setstate") || content.contains("!close"))
		{
			return;// TODO error message
		}
		else
		{
			TextChannel tc = findOpenedTextChannel(e.getAuthor().getId());
			
			if (tc == null)
			{
				tc = category.createTextChannel(String.format("%s-id%d", message.getAuthor().getName(), message.getAuthor().getIdLong())).complete();
			}
			
			Ticket t = openTicket(tc, e);
			forwardMessage(message, t, tc);
		}
	}
	
	private TextChannel findOpenedTextChannel(String id)
	{
		for (TextChannel c : category.getTextChannels())
			if (c.getName().contains(id))
				return c;
		
		return null;
	}
	
	private Ticket openTicket(TextChannel tc, PrivateMessageReceivedEvent e)
	{
		Ticket ticket = null;
		
		for (Map.Entry<Long, Ticket> t : tickets.entrySet())
		{
			if (t.getKey() == tc.getIdLong())
			{
				return t.getValue();
			}
		}
		
//		for (Ticket t : tickets)
//		{
//			if (t.getID() == tc.getIdLong())
//				return t;
//		}
			
		if (ticket == null)
		{
			ticket = new Ticket(tc.getIdLong(), e.getAuthor());
			tc.sendMessage("@here").queue();
//			tickets.add(ticket);
			tickets.put(tc.getIdLong(), ticket);
		}
		
		return ticket;
	}
	
	private void forwardMessage(Message message, Ticket t, TextChannel newChannel) 
	{
		UserProblem u = new UserProblem(t.getLastID(), message.getContentRaw());
		t.addIssue(u);
		EmbedBuilder eb;
		
		eb = new EmbedBuilder();
		eb.setTitle(languageInUse[0] + message.getAuthor().getName());
		eb.setColor(Color.decode("#8A2BE2"));

		eb.addBlankField(true);
		eb.addBlankField(true);
		eb.addBlankField(true);
		
		eb.addField("#", "", true);
		eb.addField(languageInUse[1], "", true);
		eb.addField(languageInUse[2], "", true);
		
		for (int i = 0; i < t.getList().size(); i++)
		{
			UserProblem up = t.getList().get(i);
			
			eb.addField("", String.valueOf(up.getID()), true);
			eb.addField("", String.valueOf(up.getProblemDescription()), true);
			eb.addField("", (up.getState() ? languageInUse[3] : languageInUse[4]), true);
		}
		
		eb.setFooter(languageInUse[5]);
		
		if (t.getEmbedID() != 0)
			newChannel.deleteMessageById(t.getEmbedID()).complete();
		
//		System.out.println(t.getEmbedID());
		t.setEmbedID(newChannel.sendMessage(eb.build()).complete().getIdLong());
//		System.out.println(t.getEmbedID());
	}

	public void onGuildMessageReceived(GuildMessageReceivedEvent e)
	{
		if (e.getAuthor().isBot() || e.getAuthor().isFake())
			return;
		
		Message msg = e.getMessage();
		String msgContent = msg.getContentRaw();
		
		if (msgContent.startsWith("!"))
		{
			if (e.getMessage().getCategory().equals(category))
			{
				if (!tickets.containsKey(e.getChannel().getIdLong()))
				{
					if (msgContent.contains("!close") || msgContent.contains("!solve") || msgContent.contains("!setstate"))
						e.getChannel().sendMessage(languageInUse[6]).queue();
					else
						processHelpGuildCommand(e);
				}
				else
				{
					processTicketCommand(e);
				}
			}
			else
			{
				e.getChannel().sendMessage(languageInUse[7]).queue();
			}
		}
	}
	
	private void processHelpGuildCommand(GuildMessageReceivedEvent e)
	{
		String messageContent = e.getMessage().getContentRaw();
		
		if (messageContent.startsWith("!command"))
		{
			
			e.getChannel().sendMessage(languageSBInUse.toString()).queue();
		}
		
		if (messageContent.startsWith("!language"))
		{
			Message languageMSG = e.getChannel().sendMessage(languageInUse[16]).complete();
			languageMSG.addReaction(us_flag).complete();
			languageMSG.addReaction(fr_flag).complete();
			languageList.add(languageMSG.getIdLong());
		}
	}
	
	private void processTicketCommand(GuildMessageReceivedEvent e)
	{
		String messageContent = e.getMessage().getContentRaw();
		
		if (messageContent.startsWith("!solve") || messageContent.startsWith("!setstate"))
		{
			if (messageContent.contains("!solve #") || messageContent.startsWith("!setstate #"))
			{
				TextChannel tc = e.getChannel();
				Ticket t = tickets.get(tc.getIdLong());
				int id;
				
				try 
				{
					id = Integer.parseInt(messageContent.substring(messageContent.indexOf("#") + 1, messageContent.indexOf(" ", messageContent.indexOf("#"))));
				}
				catch (Exception ex)
				{
					e.getChannel().sendMessage(languageInUse[8]).queue();
					return;
				}
				
				UserProblem u = null;
				
				for (UserProblem ups : t.getList())
				{
					if (ups.getID() == id)
					{
						u = ups;
						break;
					}
				}
				
				if (messageContent.startsWith("!solve #"))
					sendEmbeddedReply(e, messageContent, t, u);
				else
					changeState(u, e); // TODO
			}
		}
		
		if (messageContent.startsWith("!close"))
		{
			Message confirmationMSG = e.getChannel().sendMessage(languageInUse[9]).complete();
			confirmationMSG.addReaction(yes_reaction).complete();
			confirmationMSG.addReaction(no_reaction).complete();
			deleteList.add(confirmationMSG.getIdLong());
		}
		
		processHelpGuildCommand(e);
	}
	
	public void onMessageReactionAdd(MessageReactionAddEvent e)
	{
		if (!e.getUser().isBot())
		{
			if (deleteList.contains(e.getMessageIdLong()))
			{
				if (e.getReactionEmote().getName().equals(yes_reaction))
				{
					e.getTextChannel().delete().queue();
					deleteList.remove(e.getMessageIdLong());
				}
				else if (e.getReactionEmote().getName().equals(no_reaction))
					g.getTextChannelById(e.getTextChannel().getId()).deleteMessageById(e.getMessageId()).complete();
				else
					return;
			}
			
			if (languageList.contains(e.getMessageIdLong()))
			{
				if (e.getReactionEmote().getName().equals(us_flag))
				{
					setLanguage(0);
				}
				else if (e.getReactionEmote().getName().equals(fr_flag))
				{
					setLanguage(1);
				}
				
				languageList.remove(e.getMessageIdLong());
				e.getTextChannel().deleteMessageById(e.getMessageIdLong()).complete();
				e.getTextChannel().sendMessage(languageInUse[17]).queue();
			}
		}
		
	}

	private void sendEmbeddedReply(GuildMessageReceivedEvent e, String messageContent,
			Ticket t, UserProblem u)
	{
		if (u == null)
		{
			e.getChannel().sendMessage(languageInUse[10]).queue();
		}
		else
		{
			if (u.getState() == false)
			{
				EmbedBuilder eb;
				
				eb = new EmbedBuilder();
				eb.setTitle(languageInUse[11]);
				eb.addField(languageInUse[12], u.getProblemDescription(), true);
				eb.addField(languageInUse[13], messageContent.substring(messageContent.indexOf(" ", messageContent.indexOf("#"))), true);
				eb.setFooter(languageInUse[14]);
				eb.setTimestamp(new Date().toInstant());
				
				t.getAuthor().openPrivateChannel().complete().sendMessage(eb.build()).queue();
				u.setState(true);
			}
			else
				e.getChannel().sendMessage(languageInUse[15]).queue();// TODO change state
		}
	}
	
	private void changeState(UserProblem u, GuildMessageReceivedEvent e)
	{
		if (u == null)
			e.getChannel().sendMessage("Oops! I could not find an issue associated with the provided ID.\nCheck the list and try again.").queue();
		else
		{
			u.setState(!u.getState());
		}
	}
	
	public void setLanguage(int language)
	{
		languageSBInUse = Languages.getLanguageStringBuilder(language);
		languageInUse = Languages.getLanguageArray(language);
	}
	
	public void setGuild(Guild guild)
	{
		g = guild;
	}
	
	public void setInfos(long channelID, long catID)
	{
		helpChannel = g.getTextChannelById(channelID);
		category = g.getCategoryById(catID);
	}
}
