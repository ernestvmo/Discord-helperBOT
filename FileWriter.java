package discord.ernest.bot;

import java.io.BufferedWriter;
import java.io.File;



public class FileWriter
{
	private static String pathname = "savedFile.txt";
	
	public static void generateReceipt(UserProblem problem)
	{
		File file = new File(pathname);
		BufferedWriter bw = null;
		
		try 
		{
			if (!file.exists())
			{
				file.createNewFile();
			}

			java.io.FileWriter fw = new java.io.FileWriter(file, true);
			bw = new BufferedWriter(fw);
			
			bw.write("Issue \t#" + problem.getID() + System.lineSeparator());
			bw.write("Author\t" + problem.getAuthor().getName() + "(" + problem.getAuthor().getId() + ")" + System.lineSeparator());
			bw.write("Message\t" + problem.getProblemDescription() + System.lineSeparator());
			bw.write("Status\t" + problem.getStatus() + System.lineSeparator());
			bw.write(System.lineSeparator());
			
			bw.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
