package discord.ernest.bot;

public class Languages
{
	private static String [][] languages = new String[2][18];
	private static StringBuilder [] languagesSB = new StringBuilder[2];

	public static void init()
	{
		loadLanguages();
		loadLanguagesSB();
	}

	private static void loadLanguagesSB()
	{
		languagesSB[0] = new StringBuilder();
		languagesSB[0].append("Here is the list of commands available:\n\n");
		languagesSB[0].append("**`!solve #`**\n");
		languagesSB[0].append("This command allows you to reply to an issue from the problems list.\nTo reply, you have to type `!solve #` followed by the issue's ID, then followed by your reply.\nPlease note that once you reply to the issue, it will be marked as solved and removed from the list.\n\n");
		languagesSB[0].append("`!language`");
		languagesSB[0].append("\nThis command enables the change of language used by the bot.\n Type `!language` then react with the approriate emoji.\n\n");
		languagesSB[0].append("`!setstate`");
		languagesSB[0].append("\nThis command changes the state of a problem to solved.\n\n");
		languagesSB[0].append("`!close`");
		languagesSB[0].append("\nThis command allows you to close an open ticket and close the textchannel currently opened.\n\n");
		
		languagesSB[1] = new StringBuilder();
		languagesSB[1].append("Voici la liste des commandes:\n\n");
		languagesSB[1].append("**`!solve #`**\n");
		languagesSB[1].append("Cette commande permnet de répondre à un problème de la liste.\nPour répondre, vous devez tapez `!solve #` suivi de l'ID, suivi par la solution donnée."
				+ "\n`Attention!` Une fois répondu, le problème sera marqué comme résolu et devra être modifié pour répondre à nouveau. (voir `!setstate`)\n\n");
		languagesSB[1].append("`!language`");
		languagesSB[1].append("\nCette commande permet de changer la langue utilisée par le bot. \nTapez `!language` puis réagissez avec l'emoji approprié.\n\n");
		languagesSB[1].append("`!setstate`");
		languagesSB[1].append("\nCette commande change l'état d'un problème dans son état opposé.\nPour répondre, vous devez tapez `!setstate #` suivi de l'ID.\n\n");
		languagesSB[1].append("`!close`");
		languagesSB[1].append("\nCette commande vous permet de fermer un ticket ouvert et fermer la TextChannel associée.\n\n");
	}
	
	private static void loadLanguages()
	{
		{
			languages[0][0] = "Messages from ";
			languages[0][1] = "Description";
			languages[0][2] = "Solved?";
			languages[0][3] = "**YES**";
			languages[0][4] = "**NO**";
			languages[0][5] = "To reply, type !solve # followed by the issue ID, and then your answer.";
			languages[0][6] = "You cannot close this channel.\nIf you really need to close it, you will have to do it **manually**.";
			languages[0][7] = "You are in the **wrong** category. Please check if you are in the right channel.";
			languages[0][8] = "Oops! It seems you did not format the message correctly! Make sure you tag the correct user.";
			languages[0][9] = "Are you certain you wish to close this channel?\n This will delete **ALL** problems associated with this user.";
			languages[0][10] = "Oops! I could not find an issue associated with the provided ID.\nCheck the list and try again.";
			languages[0][11] = "Reply";
			languages[0][12] = "You sent:";
			languages[0][13] = "The reply";
			languages[0][14] = "If you do not find the reply useful or do not understand it, do not hesitate to send another message.";
			languages[0][15] = "The issue you are trying to select has already been marked as solved.\nYou can change its state **ONLY** if must be.";
			languages[0][16] = "Select the new language you would like to choose";
			languages[0][17] = "Language changed.";
		}
		{
			languages[1][0] = "Messages venant de ";
			languages[1][1] = "Description";
			languages[1][2] = "Résolu?";
			languages[1][3] = "**OUI**";
			languages[1][4] = "**NON**";
			languages[1][5] = "Pour répondre, tapez !solve # suivit de l'ID, et enfin la réponse.";
			languages[1][6] = "Vous ne pouvez pas fermer cette channel.\nSi vous devez vraiment le faire, vous devrez le faire **manuellement**.";
			languages[1][7] = "Vous êtes dans la **mauvaise catégorie**. Re-vérifiez que vous vous trouvez dans la bonne channel.";
			languages[1][8] = "Oups! Il semble que vous n'avez pas tapez le message **correctement**! Soyez certain d'avoir taggé le bon user.";
			languages[1][9] = "Êtes-vous certain de vouloir fermer ce channel? \n Cela supprimera aussi **TOUT** les problèmes associé à ce ticket.";
			languages[1][10] = "Oups! Je n'ai pas réussis à trouver un problème associé à cet ID.\nVérifier la liste et essayez à nouveau.";
			languages[1][11] = "Réponse";
			languages[1][12] = "Votre message:";
			languages[1][13] = "La réponse";
			languages[1][14] = "Si vous ne trouvez pas la réponse suffisante ou que vous ne la comprenez pas, n'hésitez pas à envoyer un autre message.";
			languages[1][15] = "Le problème que vous tentez de résoudre a déjà été marqué comme résolu.\nVous pouvez changer l'état du problème si vous en avez **VRAIMENT** besoin.";
			languages[1][16] = "Selectionnez la nouvelle langue";
			languages[1][17] = "Langue modifiée.";
		}
	}
	
	public static String[] getLanguageArray(int l)
	{
		return languages[l];
	}
	
	
	public static StringBuilder getLanguageStringBuilder(int l)
	{
		return languagesSB[l];
	}
}
