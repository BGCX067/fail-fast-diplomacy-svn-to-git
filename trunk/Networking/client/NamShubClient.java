package Networking.client;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import GUI.PrimaryGUI;



public class NamShubClient extends JFrame implements ActionListener, ListSelectionListener
{	
	private static final long serialVersionUID = 7744375391200844158L;
	
	private Socket talkSocket;
	private InetAddress serverIP;
	private static ObjectOutputStream out = null;
	private Container contentPane;
	private JFrame connectionWindow, gameWindow, channelWindow;
	private JPanel connectionPanel, statusPanel, sendPanel, gamePanel, channelPanel;
    
	private JTextField localIPField, c_IPField, portField, usernameField;
    
	private JTextField inputField;
	private JTextArea chatLogArea;
    
	private JButton nConnectionButton;
	private JButton disconnectButton;
	
	private JList peerList, gameList, channelList;
	private DefaultListModel peerDLM, gameDLM, channelDLM;
    private String selectedPeer, selectedGame, selectedChannel, username;
    private boolean connected = false, autoScroll = true;
    
    private static HashMap<Integer, PrimaryGUI> runningGameList = new HashMap<Integer, PrimaryGUI>();
    
	public NamShubClient()
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(900, 700);
		setTitle("Nam-Shub Messenger");
		
		contentPane = getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.Y_AXIS));
		contentPane.setBackground(Color.LIGHT_GRAY);
		
        //Connection Window
		connectionWindow = new JFrame("Configure New Connection");
		connectionWindow.setBackground(Color.LIGHT_GRAY);
		connectionWindow.setLayout(new GridLayout(9,1));
		connectionWindow.setDefaultCloseOperation(HIDE_ON_CLOSE);
		connectionWindow.setSize(new Dimension(400,400));
		connectionWindow.setResizable(false);
		
		JLabel localIPFieldLabel = new JLabel("Your IP is:");
		connectionWindow.add(localIPFieldLabel);
		localIPField = new JTextField();
		try
		{
			localIPField.setText(InetAddress.getLocalHost().toString());
		}
		catch (UnknownHostException e)
		{
			JOptionPane.showMessageDialog(this,
				    "Unable to resolve local host.",
				    "Network Error",
				    JOptionPane.ERROR_MESSAGE);
			localIPField.setText("Unable to resolve IP");
		}
		localIPField.setEditable(false);
		connectionWindow.add(localIPField);
		
		JLabel c_IPFieldLabel = new JLabel("Enter the server IP:");
		connectionWindow.add(c_IPFieldLabel);
		c_IPField = new JTextField("localhost");
		connectionWindow.add(c_IPField);
		
		JLabel portFieldLabel = new JLabel("Enter the server Port:");
		connectionWindow.add(portFieldLabel);
		portField = new JTextField("4444");
		connectionWindow.add(portField);
		
		JLabel usernameFieldLabel = new JLabel("Enter desired Username:");
		connectionWindow.add(usernameFieldLabel);
		usernameField = new JTextField("name");
		connectionWindow.add(usernameField);
		
		JButton ConnectButton = new JButton("Connect");
		ConnectButton.addActionListener(this);
		connectionWindow.add(ConnectButton);
		
		//Game Window
		gameWindow = new JFrame();
		gameWindow.setTitle("List of Currently Open Games");
		gameWindow.setBackground(Color.LIGHT_GRAY);
		gameWindow.setSize(new Dimension(300,400));
		gameWindow.setLayout(new BoxLayout(gameWindow.getContentPane(), BoxLayout.Y_AXIS));
		gameWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		gameWindow.setResizable(false);
		
		gameDLM = new DefaultListModel();
		gameList = new JList(gameDLM);
		gameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		gameList.addListSelectionListener(this);
		gameList.setPreferredSize(new Dimension(300,300));
		
		gamePanel = new JPanel();
		gamePanel.setBackground(Color.LIGHT_GRAY);
		gamePanel.setLayout(new FlowLayout());
		
		JButton joinGameButton = new JButton("Join Game");
		joinGameButton.addActionListener(this);
		gamePanel.add(joinGameButton);
		
		JButton hostGameButton = new JButton("Host New Game");
		hostGameButton.addActionListener(this);
		gamePanel.add(hostGameButton);
		
		JButton refreshGamesButton = new JButton("Refresh");
		refreshGamesButton.addActionListener(this);
		gamePanel.add(refreshGamesButton);
		
		gameWindow.add(gameList);
		gameWindow.add(gamePanel);
		
		//Channel Window
		channelWindow = new JFrame();
		channelWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        channelWindow.setTitle("List of Currently Open Channels:");
        channelWindow.setBackground(Color.LIGHT_GRAY);
        channelWindow.setSize(new Dimension(300, 400));
        channelWindow.setLayout(new BoxLayout(channelWindow.getContentPane(),BoxLayout.Y_AXIS));
        channelWindow.setResizable(false);
        
        channelDLM = new DefaultListModel();
        channelList = new JList(channelDLM);
        channelList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        channelList.addListSelectionListener(this);
        channelList.setPreferredSize(new Dimension(300,300));
        
        channelPanel = new JPanel();
        channelPanel.setBackground(Color.LIGHT_GRAY);
        channelPanel.setLayout(new FlowLayout());
        
        JButton joinChannelButton = new JButton("Join Channel");
        joinChannelButton.addActionListener(this);
        channelPanel.add(joinChannelButton);
		
        JButton createChannelButton = new JButton("Create Channel");
        createChannelButton.addActionListener(this);
        channelPanel.add(createChannelButton);
        
        JButton refreshChannelsButton = new JButton("Refresh");
        refreshChannelsButton.addActionListener(this);
        channelPanel.add(refreshChannelsButton);
        
        channelWindow.add(channelList);
        channelWindow.add(channelPanel);
        
        //Main Interface
		statusPanel = new JPanel();
		statusPanel.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()-150));
		statusPanel.setLayout(new FlowLayout());
		statusPanel.setBackground(Color.LIGHT_GRAY);
		
		chatLogArea = new JTextArea();
		chatLogArea.setEditable(false);
		
        JScrollPane chatLogScrollPane = new JScrollPane(chatLogArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        chatLogScrollPane.setPreferredSize(new Dimension(this.getWidth()-240,this.getHeight()-150));
        
        peerDLM = new DefaultListModel();
        peerList = new JList(peerDLM);
        peerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        peerList.addListSelectionListener(this);
        JScrollPane peerListScrollPane = new JScrollPane(peerList,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        peerListScrollPane.setPreferredSize(new Dimension(200, this.getHeight()-150));
        
        statusPanel.add(chatLogScrollPane);
        statusPanel.add(peerListScrollPane);
        
		sendPanel = new JPanel();
		sendPanel.setBackground(Color.LIGHT_GRAY);
		sendPanel.setLayout(new FlowLayout());
        sendPanel.setPreferredSize(new Dimension(this.getWidth(),30));
		
		inputField = new JTextField(70);
        inputField.addKeyListener(new KeyAdapter()
                { //Set up a key listener to react to the enter key and process entry.
                    public void keyPressed(KeyEvent ke)
                    {
                        if (ke.getKeyCode()==KeyEvent.VK_ENTER)
                        {
                            send(inputField.getText());
                        }
                    }
                });
		sendPanel.add(inputField);
		
		JButton SendButton = new JButton("Send");
		SendButton.addActionListener(this);
		sendPanel.add(SendButton);
        
		
        connectionPanel = new JPanel();
        connectionPanel.setLayout(new FlowLayout());
        connectionPanel.setBackground(Color.LIGHT_GRAY);
        connectionPanel.setPreferredSize(new Dimension(this.getWidth(),30));
        
		nConnectionButton = new JButton("New Connection");
		nConnectionButton.addActionListener(this);
		connectionPanel.add(nConnectionButton);
		
		disconnectButton = new JButton("Disconnect");
		disconnectButton.addActionListener(this);
        connectionPanel.add(disconnectButton);
        
        JButton gameButton = new JButton("New Game");
        gameButton.addActionListener(this);
        connectionPanel.add(gameButton);
        
        JButton channelButton = new JButton("Channel");
        channelButton.addActionListener(this);
        connectionPanel.add(channelButton);
        
        JCheckBox autoScrollBox = new JCheckBox("Autoscroll");
		autoScrollBox.setSelected(true);
		autoScrollBox.addActionListener(this);
		connectionPanel.add(autoScrollBox);
        
        contentPane.add(statusPanel);
        contentPane.add(sendPanel);
        contentPane.add(connectionPanel);
	}
	
	public int GetIntFrom(String buffer)
	{
		//Attempts to retrieve doubles from strings
		int value = 0;
		try 
		{
			value = Integer.parseInt(buffer);
		}
		catch(NumberFormatException e) 
		{
			JOptionPane.showMessageDialog(this,
				    "Invalid input: " + buffer,
				    "Input Error",
				    JOptionPane.ERROR_MESSAGE);
			value = 0;
		}
		return value;
	}
	
	public void actionPerformed(ActionEvent event) 
	{
		if (event.getActionCommand().equals("Connect"))
		{
			if (!connected)
			{
                username = usernameField.getText();
				try {
			        talkSocket = new Socket(InetAddress.getByName(c_IPField.getText()), GetIntFrom(portField.getText()));
			        out = new ObjectOutputStream(talkSocket.getOutputStream());
			    } catch (UnknownHostException e) {
			    	JOptionPane.showMessageDialog(this,
						    "Cannot resolve host: " + serverIP,
						    "Network Error",
						    JOptionPane.ERROR_MESSAGE);
			    } catch (IOException e) {
			    	JOptionPane.showMessageDialog(this,
						    "Couldn't get I/O for the connection to: " + talkSocket,
						    "Network Error",
						    JOptionPane.ERROR_MESSAGE);
			    }
			    connected = true;
			    new GetServerOutput(talkSocket, this).start();
				toServer(3,"authuname " + username);
				connectionWindow.setVisible(false);
				gameDLM.clear();
				toServer(0,"gamelist");
				channelDLM.clear();
				toServer(0,"channellist");
			}
			else
				JOptionPane.showMessageDialog(this,
					    "Already connected.",
					    "PEBKAC",
					    JOptionPane.ERROR_MESSAGE);
		}
		else if (event.getActionCommand().equals("New Connection"))
		{
			connectionWindow.setVisible(true);
		}
		else if (event.getActionCommand().equals("Send"))
		{
			send(inputField.getText());
		}
		else if (event.getActionCommand().equals("Disconnect"))
		{
			disconnect("");
		}
		else if (event.getActionCommand().equals("New Game"))
		{
			if (connected)
			{
				gameDLM.clear();
				toServer(0,"gamelist");
				gameWindow.setVisible(true);
			}
			else
				JOptionPane.showMessageDialog(this,
					    "Can't get a game list before connecting to a server.",
					    "PEBKAC",
					    JOptionPane.ERROR_MESSAGE);
		}
		else if (event.getActionCommand().equals("Channel"))
		{
			if (connected)
			{
				channelDLM.clear();
				toServer(0,"channellist");
				channelWindow.setVisible(true);
			}	
			else
				JOptionPane.showMessageDialog(this,
					    "Can't get a channel list before connecting to a server.",
					    "PEBKAC",
					    JOptionPane.ERROR_MESSAGE);
		}
		else if (event.getActionCommand().equals("Create Channel"))
		{
			peerDLM.clear();
				toServer(0,"joinchannel " + JOptionPane.showInputDialog(this, "Enter channel name", "Create New Channel", JOptionPane.QUESTION_MESSAGE));
			channelWindow.setVisible(false);
		}
		else if (event.getActionCommand().equals("Refresh"))
		{
            gameDLM.clear();
			toServer(0,"gamelist");
			channelDLM.clear();
			toServer(0,"channellist"); 
		}
        else if (event.getActionCommand().equals("Join Channel"))
        {
        	peerDLM.clear();
            toServer(0,"joinchannel " + selectedChannel);
            channelWindow.setVisible(false);
        }
        else if (event.getActionCommand().equals("Join Game"))
        {
            toServer(0,"joingame " + selectedGame);
            gameWindow.setVisible(false);
        }
        else if (event.getActionCommand().equals("Host New Game"))
        {
        	String temp = JOptionPane.showInputDialog(this, "Enter game name", "Create New Game", JOptionPane.QUESTION_MESSAGE);
            if (temp!=null)
            {
    			toServer(0,"hostgame " + temp);
                gameWindow.setVisible(false);
            }
        }
        else if (event.getActionCommand().equals("Autoscroll"))
        {
        	autoScroll = !autoScroll;
        }
	}
	/**
	 * Closes any currently open connection
	 * @param message The chat log will display this message
	 */
	public void disconnect(String message) 
	{
		if (connected)
		{
			try 
			{
				connected = false;
				toServer(4,null);
				out.close();
				talkSocket.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this,
					    "Unable to terminate connection.",
					    "Network Error",
					    JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
	        chatLogArea.setText(message);
	        peerDLM.clear();
	        
		}
		else
			JOptionPane.showMessageDialog(this,
				    "No connection to terminate.",
				    "PEBKAC",
				    JOptionPane.ERROR_MESSAGE);
	}
	public void send(String s)
	{
		toServer(2, s);
		inputField.setText("");
	}
	public static void toServer(int type, Object o)
	{
		System.out.println("Type: " + type + " Object: " + o);
		try {
			out.write(type);
			out.flush();
			out.writeObject(o);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Adds a peer to the list
	 * @param newPeer Name of the peer to be added
	 */
	public void addPeer(String newPeer)
	{
		peerDLM.addElement(newPeer);
	}
	/**
	 * Remove a peer from the list
	 * @param peer Name of the peer to be removed
	 */
	public void removePeer(String peer)
	{
		peerDLM.removeElement(peer);
	}
	/**
	 * Clear the list of peers
	 */
	public void clearPeers()
	{
		peerDLM.clear();
	}
    public void addChannel(String s)
    {
        channelDLM.addElement(s);
    }
    public void removeChannel(String s)
    {
        channelDLM.removeElement(s);
    }
    public void clearChannels()
    {
        channelDLM.clear();
    }
    public void addGame(String s) 
    {
        gameDLM.addElement(s);
    }
    public void removeGame(String s)
    {
        gameDLM.removeElement(s);
    }
    public void clearGames()
    {
        gameDLM.clear();
    }
	/**
	 * Append text to the chat log
	 * @param message The text to be appended
	 */
	public void append(String message)
	{
		chatLogArea.append(message);
		if (autoScroll)
			chatLogArea.setCaretPosition(chatLogArea.getDocument().getLength());
	}
	/**
	 * @return The chat log area object
	 */
	public JTextArea getChatLogArea()
	{
		return chatLogArea;
	}
	public void valueChanged(ListSelectionEvent lse) 
	{
		if (lse.getSource().equals(peerList))
			selectedPeer = (String)peerList.getSelectedValue();
		else if (lse.getSource().equals(gameList))
			selectedGame = (String)gameList.getSelectedValue();
		else if (lse.getSource().equals(channelList))
			selectedChannel = (String)channelList.getSelectedValue();
	}

    public static HashMap<Integer, PrimaryGUI> getRunningGameList() {
        return runningGameList;
    }

    public static void setRunningGameList(
            HashMap<Integer, PrimaryGUI> runningGameList) {
        NamShubClient.runningGameList = runningGameList;
    }
    /**
     * @return Returns true if the user has the auto-scroll box ticked. 
     */
	public boolean autoScroll() 
	{
		return autoScroll;
	}

	public boolean isConnected() {
		// TODO Auto-generated method stub
		return connected;
	}
}
class KeyAdapter implements KeyListener
{
    public void keyPressed(KeyEvent ke) {}

    public void keyReleased(KeyEvent ke) {}

    public void keyTyped(KeyEvent ke) {}
}
