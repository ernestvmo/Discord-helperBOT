package discord.ernest.bot;

import net.dv8tion.jda.api.entities.User;

public class UserProblem
{
	private int ID;
	private User author;
	private String problemDescription;
	private boolean status;
	
	public UserProblem(int ID, User a, String p)
	{
		author = a;
		this.ID = ID;
		problemDescription = p;
		this.status = false; 
	}
	
	public User getAuthor()
	{
		return author;
	}

	public String getProblemDescription()
	{
		return problemDescription;
	}
	
	public boolean getStatus()
	{
		return status;
	}

	public void setSolved()
	{
		status = true;
	}

	public int getID()
	{
		return ID;
	}
}
