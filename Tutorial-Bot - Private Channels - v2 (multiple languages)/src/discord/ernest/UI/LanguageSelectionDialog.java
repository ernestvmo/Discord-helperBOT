package discord.ernest.UI;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;

import discord.ernest.bot.Languages;

import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;

public class LanguageSelectionDialog extends JDialog implements ActionListener
{
	private final JPanel contentPanel = new JPanel();
	private JButton btnStart;
	private JComboBox<Object> languageCmbBox;
	private enum Language
	{
		English,
		Français
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			LanguageSelectionDialog dialog = new LanguageSelectionDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public LanguageSelectionDialog()
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage("img/zennit_gaming.png"));
		setResizable(false);
		setBounds(100, 100, 330, 250);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setBackground(new Color(183,123,238));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 150, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 40, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblLanguage = new JLabel("Language:");
			GridBagConstraints gbc_lblLanguage = new GridBagConstraints();
			gbc_lblLanguage.insets = new Insets(0, 0, 5, 5);
			gbc_lblLanguage.anchor = GridBagConstraints.EAST;
			gbc_lblLanguage.gridx = 1;
			gbc_lblLanguage.gridy = 1;
			contentPanel.add(lblLanguage, gbc_lblLanguage);
		}
		{
			languageCmbBox = new JComboBox<Object>(Language.values());
			languageCmbBox.setBorder(new LineBorder(new Color(0, 0, 0)));
			GridBagConstraints gbc_languageCmbBox = new GridBagConstraints();
			gbc_languageCmbBox.insets = new Insets(0, 0, 5, 5);
			gbc_languageCmbBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_languageCmbBox.gridx = 2;
			gbc_languageCmbBox.gridy = 1;
			contentPanel.add(languageCmbBox, gbc_languageCmbBox);
		}
		{
			btnStart = new JButton("Start");
			btnStart.setBackground(new Color(183,123,238));
			btnStart.addActionListener(this);
			btnStart.setPreferredSize(new Dimension(100, 23));
			GridBagConstraints gbc_btnStart = new GridBagConstraints();
			gbc_btnStart.fill = GridBagConstraints.VERTICAL;
			gbc_btnStart.gridwidth = 2;
			gbc_btnStart.insets = new Insets(0, 0, 5, 5);
			gbc_btnStart.gridx = 1;
			gbc_btnStart.gridy = 3;
			contentPanel.add(btnStart, gbc_btnStart);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		if (e.getSource().equals(btnStart))
		{
			int language = 0;
			
			switch ((Language) languageCmbBox.getSelectedItem())
			{
				case English:
					language = 0;
					break;
				case Français:
					language = 1;
					break;
			}
			
			Languages.init();
			Window w = new Window(language);
			this.dispose();
		}
	}

}
