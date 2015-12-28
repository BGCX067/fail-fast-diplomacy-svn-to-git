package Combat;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Networking.client.NamShubClient;
import UnitStateTracker.Unit;
import WorldStateTracker.Player;

public class CombatWindow extends JFrame implements ActionListener, ListSelectionListener
{
	JFrame cGUI = new JFrame();
	private JFrame combatWindow;
	private JPanel playerA, playerB, buttons;
	private JList unitsA, unitsB;
	private JList combatantsA, combatantsB;
	private HashMap<Player,ArrayList<Unit>> combatants = new HashMap<Player,ArrayList<Unit>>();
	
	public CombatWindow(HashMap<Player,ArrayList<Unit>> com)
	{
		combatants = com;
		
		setTitle("Combat");
		setBackground(Color.LIGHT_GRAY);
		setSize(new Dimension(600,500));
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		
		playerA = new JPanel();
		playerA.setBackground(Color.GRAY);
		playerA.setLayout(new FlowLayout());
		
		playerB = new JPanel();
		playerB.setBackground(Color.LIGHT_GRAY);
		playerB.setLayout(new FlowLayout());
		
		DefaultListModel unitsADLM = new DefaultListModel();
		unitsA = new JList(unitsADLM);
		unitsA.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		unitsA.setSelectedIndex(0);
		unitsA.addListSelectionListener(this);
		unitsA.setPreferredSize(new Dimension(200,500));

		DefaultListModel unitsBDLM = new DefaultListModel();
		unitsB = new JList(unitsBDLM);
		unitsB.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		unitsB.setSelectedIndex(0);
		unitsB.addListSelectionListener(this);
		unitsB.setPreferredSize(new Dimension(200,500));
		
		ArrayList<String> names = new ArrayList<String>();
		for(Player p: combatants.keySet())
		{
			names.add(p.getName());
		}
		
		ArrayList<Unit> forcesA = combatants.get(names.get(0));
		ArrayList<Unit> forcesB = combatants.get(names.get(1));
		for(int i = 0; i<forcesA.size(); i++)
		{
			unitsADLM.addElement(forcesA.get(i).getName());
		}
		for(int i = 0; i<forcesB.size(); i++)
		{
			unitsBDLM.addElement(forcesB.get(i).getName());
		}
		
		buttons = new JPanel();
		buttons.setBackground(Color.DARK_GRAY);
		buttons.setLayout(new FlowLayout());
		
		JButton target = new JButton("Target");
		target.addActionListener(this);
		buttons.add(target);
		
		JButton pass = new JButton("Pass");
		pass.addActionListener(this);
		buttons.add(pass);
		
		JButton done = new JButton("Done");
		done.addActionListener(this);
		buttons.add(done);
		
		playerA.add(unitsA);
		playerB.add(unitsB);
		getContentPane().add(playerA);
		getContentPane().add(playerB);
		getContentPane().add(buttons);
	}

	/**
	 * Need to figure out how to get units into these lists.
	 */
	public void actionPerformed(ActionEvent e) 
	{
		if (e.equals("Target"))
		{
			//(unitsA.getSelectedValue()).setTarget(unitsB.getSelectedValue());
		}
		else if (e.equals("Pass"))
		{
			//(unitsA.getSelectedValue()).setTarget(null);
		}
		else if(e.equals("Done"))
		{
			NamShubClient.toServer(0,"combattargets" + combatantsA);
			JOptionPane.showMessageDialog(null, "Targets Acquired.");
		}
	}

	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
