package Purchase;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import GUI.PrimaryGUI;
import UnitStateTracker.Armour;
import UnitStateTracker.CapitolShip;
import UnitStateTracker.Gunship;
import UnitStateTracker.Infantry;
import UnitStateTracker.StrikeCraft;
import UnitStateTracker.Unit;
import WorldStateTracker.GameData;
import WorldStateTracker.Player;

public class PurchaseGUI extends JFrame implements ActionListener, ListSelectionListener
{
	JFrame gui = new JFrame();
	private JPanel listPanel, buttonPanel, factionPanel;
	private DefaultListModel unitDLM, upgradeDLM, boughtDLM;
	private JList unitList, upgradeList, boughtList;
	private int credits;
	boolean opdf = true;
	boolean mdsa = false;
	boolean arenae = false;
	JTextField creditsField;
	Player player;
	Object lastPurchased;
	
	public PurchaseGUI(int credits, Player player)
	{
		this.player = player;
		this.credits = credits;
		setTitle("Item Purchase");
		setBackground(Color.LIGHT_GRAY);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(1000,800);
	
		buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.LIGHT_GRAY);
		buttonPanel.setLayout(new FlowLayout());
		
		listPanel = new JPanel();
		listPanel.setBackground(Color.LIGHT_GRAY);
		listPanel.setLayout(new FlowLayout());
		
		factionPanel = new JPanel();
		factionPanel.setBackground(Color.LIGHT_GRAY);
		factionPanel.setLayout(new FlowLayout());
		
		JRadioButton OPDFButton = new JRadioButton("Ordovican People's Defence Army");
		OPDFButton.addActionListener(this);
		OPDFButton.setActionCommand("OPDFButton");
		OPDFButton.setSelected(true);
		
		JRadioButton MDSAButton = new JRadioButton("Mergoshyre Dissident Suppression Army");
		MDSAButton.addActionListener(this);
		MDSAButton.setActionCommand("MDSAButton");
		
		
		JRadioButton ArenaeButton = new JRadioButton("Arenae National Defence Army");
		ArenaeButton.addActionListener(this);
		ArenaeButton.setActionCommand("ArenaeButton");
		
		ButtonGroup faction = new ButtonGroup();
		faction.add(OPDFButton);
		faction.add(MDSAButton);
		faction.add(ArenaeButton);
		
		factionPanel.add(OPDFButton);
		factionPanel.add(MDSAButton);
		factionPanel.add(ArenaeButton);
		
		creditsField = new JTextField("Credits: " + credits);
		creditsField.setEditable(false);
		
		JButton buyUnitButton = new JButton("Buy Unit");
		buyUnitButton.addActionListener(this);
		buttonPanel.add(buyUnitButton);
		
		/*JButton buyUpgradeButton = new JButton("Buy Upgrade");
		buyUpgradeButton.addActionListener(this);
		buttonPanel.add(buyUpgradeButton);*/
		
		JButton undoButton = new JButton("Undo");
		undoButton.addActionListener(this);
		buttonPanel.add(undoButton);
		
		JButton closeButton = new JButton("Finish and Close");
		closeButton.addActionListener(this);
		buttonPanel.add(closeButton);
		
		unitDLM = new DefaultListModel();
		unitList = new JList(unitDLM);
		unitList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		unitList.addListSelectionListener(this);
		unitList.setPreferredSize(new Dimension(200,500));
		JScrollPane unitScrollPane = new JScrollPane(unitList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		unitDLM.add(0, "Infantry");
		unitDLM.add(1, "Armour");
		unitDLM.add(2, "Gunship");
		unitDLM.add(3, "Strike Craft");
		unitDLM.add(4, "Capitol Ship");
		
		
		/*upgradeDLM = new DefaultListModel();
		upgradeList = new JList(upgradeDLM);
		upgradeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		upgradeList.addListSelectionListener(this);
		upgradeList.setPreferredSize(new Dimension(200,500));
		JScrollPane upgradeScrollPane = new JScrollPane(upgradeList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		for (int i=0; i<GameData.getUpgrades().size(); i++)
		{
			upgradeDLM.add(i, GameData.getUpgrades().get(i));
		}
		*/
		
		boughtDLM = new DefaultListModel();
		boughtList = new JList(boughtDLM);
		boughtList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		boughtList.addListSelectionListener(this);
		boughtList.setPreferredSize(new Dimension(200,500));
		JScrollPane boughtScrollPane = new JScrollPane(boughtList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		listPanel.add(unitList);
		//listPanel.add(upgradeList);
		listPanel.add(boughtList);
		listPanel.add(unitScrollPane);
		//listPanel.add(upgradeScrollPane);
		listPanel.add(boughtScrollPane);
		getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
		getContentPane().add(factionPanel);
		getContentPane().add(listPanel);
		getContentPane().add(buttonPanel);
		getContentPane().add(creditsField);
	}
	@Override
	public void actionPerformed(ActionEvent event) 
	{
		
		if(event.getActionCommand().equals("Buy Unit"))
		{
			if(((String)unitList.getSelectedValue()).equals("Infantry"))
			{
				player.addToForces(new Infantry(null, "Infantry", null, player, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
				lastPurchased = unitList.getSelectedValue();
				credits-=5;
				boughtDLM.addElement("Infantry");
				creditsField.setText("Credits:" + credits);
			}
			else if(((String)unitList.getSelectedValue()).equals("Armour"))
			{
				player.addToForces(new Armour(null, "Armour", null, player, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
				lastPurchased = unitList.getSelectedValue();
				credits-=10;
				boughtDLM.addElement("Armour");
				creditsField.setText("Credits:" + credits);
			}
			else if(((String)unitList.getSelectedValue()).equals("Gunship"))
			{
				player.addToForces(new Gunship(null, "Gunship", null, player, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
				lastPurchased = unitList.getSelectedValue();
				credits-= 15;
				boughtDLM.addElement("Gunship");
				creditsField.setText("Credits:" + credits);
			}
			else if(((String)unitList.getSelectedValue()).equals("Strike Craft"))
			{
				player.addToForces(new StrikeCraft(null, "Strike Craft", null, player, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
				lastPurchased = unitList.getSelectedValue();
				credits-= 20;
				boughtDLM.addElement("Strike Craft");
				creditsField.setText("Credits:" + credits);
			}
			else if(((String)unitList.getSelectedValue()).equals("Capitol Ship"))
			{
				player.addToForces(new CapitolShip(null, "Capitol Ship", null, player, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
				lastPurchased = unitList.getSelectedValue();
				credits-=25;
				boughtDLM.addElement("CapitolShip");
				creditsField.setText("Credits:" + credits);
			}

			if(credits<=0)
			{
				JOptionPane.showMessageDialog(null, "Cannot purchase.", "Insufficient Funds.", JOptionPane.ERROR_MESSAGE);
			}
		}
	
		else if(event.getActionCommand().equals("Undo"))
		{
			if (unitList.getSelectedValue()==null)
			{
				unitList.setSelectedValue(lastPurchased, true);
			}
			if(((String)unitList.getSelectedValue()).equals("Infantry"))
			{
				credits+=5;
				boughtDLM.remove(boughtList.getSelectedIndex());
				creditsField.setText("Credits:" + credits);
			}
			else if(((String)unitList.getSelectedValue()).equals("Armour"))
			{
				credits+=10;
				boughtDLM.remove(boughtList.getSelectedIndex());
				creditsField.setText("Credits:" + credits);
			}
			else if(((String)unitList.getSelectedValue()).equals("Gunship"))
			{
				credits+= 15;
				boughtDLM.remove(boughtList.getSelectedIndex());
				creditsField.setText("Credits:" + credits);
			}
			else if(((String)unitList.getSelectedValue()).equals("Strike Craft"))
			{
				credits+= 20;
				boughtDLM.remove(boughtList.getSelectedIndex());
				creditsField.setText("Credits:" + credits);
			}
			else if(((String)unitList.getSelectedValue()).equals("Capitol Ship"))
			{
				credits+=25;
				boughtDLM.remove(boughtList.getSelectedIndex());
				creditsField.setText("Credits:" + credits);
			}
		}
		
		else if(event.getActionCommand().equals("Finish and Close"))
		{
			PrimaryGUI p = new PrimaryGUI(true, 01342, player);
			p.setVisible(true);
			setVisible(false);
		}
	}
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
