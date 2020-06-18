package discord.ernest.bot;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MyEventListener extends ListenerAdapter {
	
	private TextChannel helpChannel;
	List<UserProblem> problems = new ArrayList<>(); // TODO change to HashMap
	private int count=0;
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e)
	{
		if (e.getAuthor().isBot())
			return;
		
		Message message = e.getMessage();
		String messageContent = message.getContentRaw();
		MessageChannel channel = e.getChannel();
		
		if (messageContent.startsWith(".test"))
		{
//			System.out.println("HERE");
			channel.sendMessage("Hello World").queue();
		}

		if (messageContent.startsWith("!command"))
		{
			StringBuilder sb = new StringBuilder();
			
			sb.append("Here are the bot's commands:\n\n");
			sb.append("`!solve`");
			sb.append("\nThis command allows you to reply to an issue from the problems list.\nTo reply, you have to type `!solve #` followed by the issue's ID, then followed by your reply.\nPlease note that once you reply to the issue, it will be marked as solved and removed from the list.\n\n");
			sb.append("`!del`");
			sb.append("\nThis command allows you to remove an issue from the list without solving it.\n To delete an issue, you have to type `!del #` followed by the issue ID.\n It will not send a reply to the person who sent the issue.\n\n");
			sb.append("`!plist`");
			sb.append("\nThis command allows you to see all issues that have not yet been solved.\nType `!plist` to see the list of issues.");
			
			helpChannel.sendMessage(sb.toString()).queue();
		}
		
		if (messageContent.startsWith("!plist"))
		{
			if (problems.size() == 0)
			{
				channel.sendMessage("There are no pending issues.").queue();
				return;
			}
			else
			{
				StringBuilder sb = new StringBuilder();
				
				sb.append("`");
				for (UserProblem up : problems)
				{
					
					sb.append(String.format("Issue #%d: ", up.getID())  + up.getAuthor().getName() + " asked: " + up.getProblemDescription() + "\n");
				}

				sb.append("`");
				helpChannel.sendMessage(sb.toString()).queue();
			}
			
		}

		if (messageContent.startsWith("!del"))
		{

			if (problems.size() == 0)
			{
				channel.sendMessage("Oops! There are no problems that require help. The problem can already have been solved by another user.").queue();
				return;
			}
			
			if (messageContent.contains("!del #"))
			{
				int id = Integer.parseInt((String) messageContent.substring(messageContent.indexOf("#") + 1));
				
				for (UserProblem up : problems)
				{
					if (up.getID() == id)
					{
						problems.remove(up);
						helpChannel.sendMessage(String.format("`Issue #%d` was removed from the list of issues.", id)).queue();
						break;
					}
					else
					{
						// TODO message no user found
						return;
					}
				}
			}
			else
				channel.sendMessage("incorrect format, please try again.").queue();
		}
		
		if (messageContent.startsWith("!solve"))
		{
			if (problems.size() == 0)
			{
				channel.sendMessage("Oops! There are no problems that require help. The problem can already have been solved by another user.").queue();
				return;
			}
			
			if (messageContent.contains("!solve #"))
			{
				User u = null;
				UserProblem p = null;
				
				int id = Integer.parseInt((String) messageContent.subSequence(messageContent.indexOf("#") + 1, messageContent.indexOf(" ", messageContent.indexOf("#"))));
				
				for (UserProblem up : problems)
				{
					if (up.getID() == id)
					{
						p = up;
						u = up.getAuthor();
						break;
					}
					else
					{
						// TODO message no user found
						return;
					}
				}		
				
				solveIssueMessage(message, u, p);
			}
			else
			{
				channel.sendMessage("Oops! It seems you did not format the message correctly! Make sure you tag the correct user.").queue();
			}
		}
	}
	
	private void solveIssueMessage(Message message, User u, UserProblem problem)
	{
		String messageContent = message.getContentRaw();
		u.openPrivateChannel().complete().sendMessage(
					message.getAuthor().getName() + " replied: " 
		+ messageContent.substring(messageContent.indexOf(" ", messageContent.indexOf("#")))
		+ "\n" + "Please do not reply to this message unless you have another question!"
				).queue();//getContentDisplay()
		problems.remove(problem);
	}

	public void onPrivateMessageReceived(PrivateMessageReceivedEvent event)
	{
		if (event.getAuthor().isBot())
		{
			return;
		}
		
		Message message = event.getMessage();
		String messageContent = message.getContentDisplay();
		MessageChannel mc = event.getChannel();
		
		if (!messageContent.contains("!plist") && !messageContent.contains("!solve") && !messageContent.contains("!del") && !messageContent.contains("!command"))
		{
			mc.sendMessage("Votre message a été pris en compte, vous recevrez une réponse bientot.").queue();
			forwardMessage(message);
		}
		else
		{
			mc.sendMessage("Your message was blocked because it contains a potential command.").queue();
			return;
		}
		
	}
	
	private void forwardMessage(Message message) 
	{
		UserProblem up = new UserProblem(count, message.getAuthor(), message.getContentRaw());
		helpChannel.sendMessage(String.format("Issue #%d: ", count)  + message.getAuthor().getName() + " asked: " + message.getContentRaw() 
		+ "\n" + "To solve this issue, type `!solve #` followed by the issue ID, then followed by your reply.").queue();
		problems.add(up);
		FileWriter.generateReceipt(up);
		count++;
	}
	
	public void setTextChannel(TextChannel textChannel)
	{
		helpChannel = textChannel;
	}
}
