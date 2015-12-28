package Networking.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

import GUI.ServerGUI;

public class ConnectionHandler extends Thread
{
    private boolean listening = true;
    private ServerThread newestThread = null;
    private ServerSocket serverSocket = null;
    private Socket newestConnection = null;
    private ServerGUI GUI = null;
    public ConnectionHandler(ServerSocket s, ServerGUI G)
    {
        serverSocket = s;
        GUI = G;
    }
    public void run()
    {
        while (listening)
        {
            try
            {
                newestConnection = serverSocket.accept();
                newestThread = new ServerThread(newestConnection, GUI, this);
                GUI.append("Accepted connection from client.\n");
                newestThread.start();
            } catch (IOException uae) {
                if (listening)
                {
                    JOptionPane.showMessageDialog(null,
                            "Unable to accept connection.",
                            "Network Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            
        }
    }
    public void setListening(boolean b)
    {
        listening = b;
    }
    public boolean isListening()
    {
    	return listening;
    }
}
