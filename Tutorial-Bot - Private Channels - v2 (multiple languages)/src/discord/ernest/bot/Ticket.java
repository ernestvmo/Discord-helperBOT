package discord.ernest.bot;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.entities.User;

public class Ticket
{
	private long id;
	private int count;
	private List issueList;
	private long embedID;
	private User author;
	
	public Ticket(long id, User author)
	{
		this.id = id;
		count = 0;
		this.issueList = new ArrayList<UserProblem>();
		embedID = 0;
		this.author = author;
	}
	
	public void addIssue(UserProblem u)
	{
		issueList.add(u);
		count++;
	}

	public long getEmbedID()
	{
		return embedID;
	}

	public void setEmbedID(long id)
	{
		this.embedID = id;
	}

	public long getID()
	{
		return this.id;
	}
	
	public int getLastID()
	{
		return this.count;
	}

	public List<UserProblem> getList()
	{
		return this.issueList;
	}
	
	public User getAuthor()
	{
		return this.author;
	}
}
