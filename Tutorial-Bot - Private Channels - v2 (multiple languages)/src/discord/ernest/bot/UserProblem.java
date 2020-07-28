package discord.ernest.bot;

import java.io.Serializable;

import net.dv8tion.jda.api.entities.User;

public class UserProblem
{
	private int ID;
	private String problemDescription;
	private boolean state;
	
	public UserProblem(int ID, String p)
	{
		this.ID = ID;
		problemDescription = p;
		state = false;
	}
	
	public int getID()
	{
		return ID;
	}

	public String getProblemDescription()
	{
		return problemDescription;
	}
	
	public void setState(boolean newVal)
	{
		this.state = newVal;
	}
	
	public boolean getState()
	{
		return state;
	}
	
	public String toString()
	{
		return ID + " " + problemDescription;
	}
}
