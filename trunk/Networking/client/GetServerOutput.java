package Networking.client;
import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;

import javax.swing.JOptionPane;

import WorldStateTracker.Player;

import GUI.PrimaryGUI;

public class GetServerOutput extends Thread
{
	private String fromServer = "";
	private int leadingByte;
	private NamShubClient GUI = null;
	private Socket TalkSocket = null;
	private ObjectInputStream in = null;
    private HashMap<Integer, PrimaryGUI> runningGameList = NamShubClient.getRunningGameList();
	
	public GetServerOutput (Socket TalkSocket, NamShubClient GUI)
	{
		this.GUI = GUI;
		this.TalkSocket = TalkSocket;
	}
	
	public void run() 
	{
		try 
		{
			if (TalkSocket.getInputStream()!=null)
			{
				in = new ObjectInputStream(TalkSocket.getInputStream());
				System.out.println("Started");
	            try{
	                while (GUI.isConnected()) 
	                {
	                	leadingByte = in.read();
	                	System.out.println("Leadingbyte: " + leadingByte);
	                	if (leadingByte == 0)
	                	{
	                		try {
								fromServer = (String)in.readObject();
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	                		System.out.println("FromServer: " + fromServer);
	                		if (fromServer.startsWith("adduser"))
	                			GUI.addPeer(fromServer.split(" ")[1]);
	                        else if (fromServer.startsWith("removeuser"))
	                			GUI.removePeer(fromServer.split(" ")[1]);
	                        else if (fromServer.startsWith("addchannel"))
	                            GUI.addChannel(fromServer.split(" ")[1]);
	                        else if (fromServer.startsWith("removechannel"))
	                            GUI.removeChannel(fromServer.split(" ")[1]);
	                        else if (fromServer.startsWith("addgame"))
	                            GUI.addGame(fromServer.split(" ")[1]);
	                        else if (fromServer.startsWith("removegame"))
	                            GUI.removeGame(fromServer.split(" ")[1]);
	                        else if (fromServer.equals("gameisfull"))
	                        	JOptionPane.showMessageDialog(GUI,
	                					"Sorry, that game is full.",
	                					"Cannot Connect",
	                					JOptionPane.ERROR_MESSAGE);
	                        else if (fromServer.startsWith("gamestart"))
	                        {
	                            System.err.println("New game start registered."); 
	                            PrimaryGUI newGame = new PrimaryGUI(true, Integer.parseInt(fromServer.split(" ",3)[1]), new Player("DummyPlayer",null));
	                            runningGameList.put(newGame.getGameID(),newGame);
	                            newGame.setVisible(true);
	                        }
	                        else if (fromServer.startsWith("gameaddplayer"))
	                        {
	                        	(runningGameList.get(Integer.parseInt(fromServer.split(" ",3)[1]))).addPlayer(fromServer.split(" ",3)[2]);
	                        }
	                        else if (fromServer.startsWith("gamechat"))
	                        {
	                            runningGameList.get(Integer.parseInt(fromServer.split(" ",3)[1])).append(fromServer.split(" ",3)[2]);
	                        }
	                        else if (fromServer.startsWith("select"))
	                        {
	                            runningGameList.get(Integer.parseInt(fromServer.split(" ",5)[1])).remoteClick(Integer.parseInt(fromServer.split(" ",5)[2]),Integer.parseInt(fromServer.split(" ",5)[3]),Color.decode(((fromServer.split(" ",5)[4]))));
	                        }
	                        else
	                        {
	                        	System.err.println("Invalid command issued");
	                        }
	                	}
	                	else if (leadingByte == 1)//Receive a Turn.
	                	{
	                		
	                	}
	                	else if (leadingByte == 2)
	                	{
	                		try {
								fromServer = (String)in.readObject();
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	                		GUI.append(fromServer + "\n");
	                	}
	                	else if (leadingByte == 4)
	                	{
	                		try {
								fromServer = (String)in.readObject();
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	                		GUI.disconnect("Kicked by server - " + fromServer);
	                	}
	                } 
	            }
			    catch (SocketException se) {
			    	if (GUI.isConnected())
			    	{
			    		JOptionPane.showMessageDialog(GUI,
        					    "Unable to read from socket, disconnecting.",
        					    "Network Error",
        					    JOptionPane.ERROR_MESSAGE);
                		GUI.disconnect("Lost connection to server.");
			    	}
                }
			}
		}catch(IOException ioe){
        	ioe.printStackTrace();
		}finally{
        	try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } 
	}
}