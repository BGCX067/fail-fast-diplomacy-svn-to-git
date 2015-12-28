package Networking.server;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

public class Game 
{
	public static final int[] colours = new int[]{Color.GREEN.getRGB(), Color.BLUE.getRGB(), Color.CYAN.getRGB(), Color.MAGENTA.getRGB(), Color.ORANGE.getRGB(), Color.RED.getRGB(), Color.YELLOW.getRGB(), Color.PINK.getRGB()};
	private String name;
	private ArrayList<User> players;
	private int gameID;
	public Game(String name, ArrayList<User> players, int gameID) throws GameIsFullException
	{
        System.err.println("A Game was created." + name + " " + gameID + " " + players);
		this.name = name;
		if (players.size()<=8)
			this.players = players;
		else
			throw new GameIsFullException();
		this.gameID = gameID;
		for (User u:players)
		{
            System.err.println(gameID);
			try {
				ServerThread.broadcast(u,"gamestart " + gameID + " " + colours[players.indexOf(u)],0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (User su:players)
				try {
					ServerThread.broadcast(u,"gameaddplayer " + gameID + " " + su.getUserName(),0);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	public void addPlayer(User u) throws GameIsFullException
	{
		if (players.size()<8)
		{
			for (User ou:players)
				try {
					ServerThread.broadcast(ou,"gameaddplayer " + gameID + " " + u.getUserName(),0);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			players.add(u);
			try {
				ServerThread.broadcast(u,"gamestart " + gameID + " " + colours[players.indexOf(u)],0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
			throw new GameIsFullException();
	}
	public void removePlayer(User u)
	{
		players.remove(u);
		for (User ou:players)
		{
			try {
				ServerThread.broadcast(ou,"gameremoveplayer " + gameID + " " + u.getUserName(),0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void gameCast(String s)
	{
		for (User u:players)
			try {
				ServerThread.broadcast(u,"gamechat " + gameID + s, 0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public void gameCommand(String s, int t)
	{
		for (User u:players)
			try {
				ServerThread.broadcast(u,s,t);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public void gameObjCast(Object o, int type)
	{
		for (User u:players)
			try {
				ServerThread.broadcast(u,o,type);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public void remoteSelect(User u, String s)
	{
		for (User ou:players)
		{
            try {
				ServerThread.broadcast(ou,s + " " + colours[players.indexOf(u)],1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public String getName() 
	{
		return name;
	}
	public int getPlayerCount()
	{
		return players.size();
	}
}
