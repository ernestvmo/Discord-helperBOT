package discord.ernest.UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import discord.ernest.bot.DiscordBOT;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Window extends JFrame implements ActionListener
{
	private JPanel contentPane;
	private JButton btnActivate;
	
	private DiscordBOT bot;
	private JTextArea txtExceptionArea;
	private JButton btnShutDown;
	
	private int language;

	public Window(int language)
	{
		try
		{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			this.language = language;
			createFrame();
		}
		catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void startBOT(int l)
	{
		bot = new DiscordBOT(l);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(btnActivate))
		{
			txtExceptionArea.append("BOT launched!\n");
			startBOT(language);
			btnActivate.setEnabled(false);
		}
		
		if (e.getSource().equals(btnShutDown))
			if (JOptionPane.showConfirmDialog(this, "This will turn off the BOT, continue?", "Shut Down?", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
				System.exit(0);
	}
	
	/**
	 * Create the frame.
	 */
	public void createFrame()
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage("img/discord_icon.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Discord BOT");
		setBounds(100, 100, 526, 400);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(183,123,238));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(183,123,238));
		contentPane.add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{1, 100, 1, 0};
		gbl_panel.rowHeights = new int[]{40, 30, 0, 40, 0};
		gbl_panel.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		btnActivate = new JButton("Activate");
		btnActivate.setBackground(new Color(183,123,238));
		btnActivate.addActionListener(this);
		GridBagConstraints gbc_btnActivate = new GridBagConstraints();
		gbc_btnActivate.fill = GridBagConstraints.BOTH;
		gbc_btnActivate.insets = new Insets(0, 0, 5, 5);
		gbc_btnActivate.gridx = 1;
		gbc_btnActivate.gridy = 1;
		panel.add(btnActivate, gbc_btnActivate);
		
		btnShutDown = new JButton("Shut Down");
		btnShutDown.setBackground(new Color(183,123,238));
		btnShutDown.addActionListener(this);
		GridBagConstraints gbc_btnShutDown = new GridBagConstraints();
		gbc_btnShutDown.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnShutDown.insets = new Insets(0, 0, 5, 5);
		gbc_btnShutDown.gridx = 1;
		gbc_btnShutDown.gridy = 2;
		panel.add(btnShutDown, gbc_btnShutDown);
		
//		btnShutDown = new JButton("Shut Down");
//		btnShutDown.addActionListener(this);
//		GridBagConstraints gbc_btnShutDown = new GridBagConstraints();
//		gbc_btnShutDown.fill = GridBagConstraints.BOTH;
//		gbc_btnShutDown.insets = new Insets(0, 0, 5, 5);
//		gbc_btnShutDown.gridx = 1;
//		gbc_btnShutDown.gridy = 2;
//		panel.add(btnShutDown, gbc_btnShutDown);
		
		JPanel textAreaPanel = new JPanel();
		textAreaPanel.setBackground(new Color(183,123,238));
		contentPane.add(textAreaPanel, BorderLayout.CENTER);
		GridBagLayout gbl_textAreaPanel = new GridBagLayout();
		gbl_textAreaPanel.columnWidths = new int[]{20, 0, 20, 0};
		gbl_textAreaPanel.rowHeights = new int[]{20, 0, 20, 0};
		gbl_textAreaPanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_textAreaPanel.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		textAreaPanel.setLayout(gbl_textAreaPanel);
		
		txtExceptionArea = new JTextArea();
		txtExceptionArea.setEditable(false);
		txtExceptionArea.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new EmptyBorder(5, 5, 5, 5)));
		txtExceptionArea.setWrapStyleWord(true);
		txtExceptionArea.setLineWrap(true);
		GridBagConstraints gbc_txtrExceptionArea = new GridBagConstraints();
		gbc_txtrExceptionArea.insets = new Insets(0, 0, 5, 5);
		gbc_txtrExceptionArea.fill = GridBagConstraints.BOTH;
		gbc_txtrExceptionArea.gridx = 1;
		gbc_txtrExceptionArea.gridy = 1;
		textAreaPanel.add(txtExceptionArea, gbc_txtrExceptionArea);

		setVisible(true);
	}

}
