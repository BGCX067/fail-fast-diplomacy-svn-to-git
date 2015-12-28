package GUI;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
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

import Networking.server.NamShubServer;
import Networking.server.User;


public class ServerGUI extends JFrame implements ActionListener//, ListSelectionListener
{
	private static final long serialVersionUID = -6348669063635955376L;
    
	private Container contentPane = null;
    private JTextArea chatLogArea = null;
    
    private int WIDTH = 900, HEIGHT = 700;
    
    private JFrame configFrame;
    private JPanel optionsPanel;
    private JPanel statusPanel;
    private JTextField localIPField;
    private JTextField portField;
    
    private JButton configureSessionButton;
    private JButton terminateSessionButton;
    private JList userList;
    private DefaultListModel listModel = new DefaultListModel();
    
    private String selectedUser = null;
    
    NamShubServer server = null;
    
    //private String selectedUser;
    
    public ServerGUI(NamShubServer server)
    {
        this.server = server;
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH,HEIGHT);
        setTitle("Nam-Shub Server");
        
        contentPane = getContentPane();
        contentPane.setBackground(Color.LIGHT_GRAY);
        contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.Y_AXIS));
        
        statusPanel = new JPanel();
        statusPanel.setBackground(Color.LIGHT_GRAY);
        statusPanel.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()-100));
        statusPanel.setLayout(new FlowLayout());
        
        chatLogArea = new JTextArea();
        chatLogArea.setEditable(false);
        
        JScrollPane chatLogScrollPane = new JScrollPane(chatLogArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        chatLogScrollPane.setPreferredSize(new Dimension(WIDTH-300,HEIGHT-100));
        
        userList = new JList(listModel);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userList.setVisibleRowCount(10);
        
        JScrollPane userListScrollPane = new JScrollPane(userList,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        userListScrollPane.setPreferredSize(new Dimension(200,HEIGHT-100));
        
        statusPanel.add(userListScrollPane);
        statusPanel.add(chatLogScrollPane);
        
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new FlowLayout());
        optionsPanel.setBackground(Color.LIGHT_GRAY);
        
        configureSessionButton = new JButton("Configure New Session");
        configureSessionButton.addActionListener(this);
        optionsPanel.add(configureSessionButton);
        
        terminateSessionButton = new JButton("Terminate Session");
        terminateSessionButton.addActionListener(this);
        optionsPanel.add(terminateSessionButton);
        
        contentPane.add(statusPanel);
        contentPane.add(optionsPanel);
        
        configFrame = new JFrame("Configure Session");
        configFrame.setBackground(Color.LIGHT_GRAY);
        configFrame.setLayout(new GridLayout(5,1));
        configFrame.setDefaultCloseOperation(HIDE_ON_CLOSE);
        
        JLabel localIPFieldLabel = new JLabel("Server IP is:");
        configFrame.add(localIPFieldLabel);
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
        configFrame.add(localIPField);
        
        JLabel PortLabel = new JLabel("Enter the port to listen on:");
        configFrame.add(PortLabel);
        portField = new JTextField("4444",8);
        configFrame.add(portField);
        
        JButton BeginSessionButton = new JButton("Begin Session");
        BeginSessionButton.addActionListener(this);
        configFrame.add(BeginSessionButton);
        
        configFrame.pack();
    }
    public void addNewUser(User newUser)
    {
    	listModel.addElement(newUser);
    }
    public int getIntFrom(String buffer)
    {
        //Attempts to retrieve integers from strings
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

    public void actionPerformed(ActionEvent e) 
    {
        if (e.getActionCommand().equals("Configure New Session"))
        {
            configFrame.setVisible(true);
        }
        else if (e.getActionCommand().equals("Begin Session"))
        {
            server.beginSession(getIntFrom(portField.getText()), localIPField.getText());
            configFrame.setVisible(false);
        }
        else if (e.getActionCommand().equals("Terminate Session"))
        {
            server.terminateSession();
        }
    }
    public void append(String s)
    {
        chatLogArea.append(s);
        chatLogArea.setCaretPosition(chatLogArea.getDocument().getLength());
    }
    public void addUser(User nu)
    {
    	listModel.addElement(nu.getUserName());
    }
    public void removeUser(User nu)
    {
    	listModel.removeElement(nu.getUserName());
    }
	public void valueChanged(ListSelectionEvent lse) 
	{
		selectedUser = (String)userList.getSelectedValue();
	}
	public String getSelectedUser()
	{
		return selectedUser;
	}
	public void clearUsers()
	{
		listModel.clear();
	}
}
