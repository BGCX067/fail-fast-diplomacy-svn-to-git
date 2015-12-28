package GUI;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputListener;

import Graphics.HexGrid;
import Graphics.Hexagon;
import Graphics.Point;
import Graphics.RelativeDistanceComparator;
import Networking.client.NamShubClient;
import UnitStateTracker.Infantry;
import UnitStateTracker.Unit;
import WorldStateTracker.IllegalUndoException;
import WorldStateTracker.Player;
import WorldStateTracker.Turn;

public class PrimaryGUI extends JFrame implements KeyListener, ActionListener
{
    /**
     * 
     */
    private static final long serialVersionUID = -4920355002620129413L;
    private HexGrid hg = new HexGrid(20,20,100);
    private Container contentPane = null;
    private JPanel contentsPanel = null, gamePanel = null, sendPanel = null, chatPanel = null;
    private JTextField inputField;
    private JList contentsList = null, unitPurchaseList = null;
    private DefaultListModel dlm = new DefaultListModel(), unitPurchaseDLM = null;
    private JScrollPane listScrollPane = null;
    private boolean isConnected = false;
    private JTextArea chatLogArea;
    private int gameID = 0;
    private DrawFrame df = null;
    private ArrayList<Unit> limbo = new ArrayList<Unit>();
    private boolean activeMove = false, autoScroll = true;
    private Hexagon moveStart;
    private JFrame unitPurchaseWindow = null, miniMap = null;
    
    //TODO Make a central game object to store and distribute things like this
    //What the hell are these doing in the GUI?
    Turn currentTurn = new Turn(0);
    private Player player;
    
    public PrimaryGUI(boolean isConnected, int gameID, Player player)
    {
    	this.player = player;
    	this.isConnected = isConnected;
    	this.gameID = gameID;
        contentPane = getContentPane();
        setSize(new Dimension(1124,1024));
        setTitle("Fail Fast Diplomacy");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        df = new DrawFrame(hg, this);
        df.addMouseListener(df);
        df.addMouseMotionListener(df);
        df.setPreferredSize(new Dimension(new Dimension(Math.min((3*this.getWidth()/4),this.getWidth()-300),this.getHeight()-300)));
        
        contentsPanel = new JPanel();
        contentsPanel.setBackground(Color.LIGHT_GRAY);
        contentsPanel.setPreferredSize(new Dimension((this.getWidth()/4),300));
        contentsList = new JList(dlm);
        contentsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listScrollPane = new JScrollPane(contentsList);
        listScrollPane.setPreferredSize(new Dimension(Math.min((this.getWidth()/4)-10,290), this.getHeight()-300));
        contentsPanel.add(listScrollPane);
        
        JButton moveButton = new JButton("Move");
        moveButton.addActionListener(this);
        contentsPanel.add(moveButton);
        
        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(this);
        contentsPanel.add(undoButton);
        
        JButton endTurnButton = new JButton("End Turn");
        endTurnButton.addActionListener(this);
        contentsPanel.add(endTurnButton);
        
        JButton addUnitButton = new JButton("Add Unit");
        addUnitButton.addActionListener(this);
        contentsPanel.add(addUnitButton);
        
        JButton miniMapButton = new JButton("Map");
        miniMapButton.addActionListener(this);
        contentsPanel.add(miniMapButton);
        
        miniMap = new JFrame("Minimap");
        miniMap.setBackground(Color.WHITE);
        miniMap.setLayout(new FlowLayout());
        miniMap.setPreferredSize(new Dimension(600,600));
        miniMap.setDefaultCloseOperation(HIDE_ON_CLOSE);
        miniMap.add(new MiniMap(df));
        
        if (!isConnected)
        {
            contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
            contentPane.add(df);
            contentPane.add(contentsPanel);
        }
        else
        {
            gamePanel = new JPanel();
            gamePanel.setBackground(Color.LIGHT_GRAY);
            gamePanel.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()-100));
            gamePanel.setLayout(new BoxLayout(gamePanel,BoxLayout.X_AXIS));
            
            gamePanel.add(df);
            gamePanel.add(contentsPanel);
            
            sendPanel = new JPanel();
            sendPanel.setBackground(Color.LIGHT_GRAY);
            sendPanel.setLayout(new BoxLayout(sendPanel,BoxLayout.X_AXIS));
            sendPanel.setPreferredSize(new Dimension(this.getWidth(),30));
            
            chatPanel = new JPanel();
            
            chatLogArea = new JTextArea();
            chatLogArea.setEditable(false);
            
            
            JScrollPane chatLogScrollPane = new JScrollPane(chatLogArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            chatLogScrollPane.setPreferredSize(new Dimension(this.getWidth(),200));
            
            chatPanel.add(chatLogScrollPane);
            
            inputField = new JTextField(70);
            inputField.addKeyListener(this);
            inputField.addKeyListener(new KeyAdapter() 
            { //Set up a key listener to react to the enter key and process entry.
                public void keyPressed(KeyEvent ke)
                {
                    if (ke.getKeyCode()==KeyEvent.VK_ENTER)
                    {
                        send(inputField.getText());
                        inputField.setText("");
                    }
                }
            });
            sendPanel.add(inputField);
            
            JButton SendButton = new JButton("Send");
            SendButton.addActionListener(this);
            sendPanel.add(SendButton);
            
            JCheckBox autoScrollBox = new JCheckBox("Autoscroll");
    		autoScrollBox.setSelected(true);
    		autoScrollBox.addActionListener(this);
    		sendPanel.add(autoScrollBox);
            
            contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.Y_AXIS));
            contentPane.add(gamePanel);
            contentPane.add(chatPanel);
            contentPane.add(sendPanel);
        }
        setExtendedState(Frame.MAXIMIZED_BOTH);   
    }
    public void send(String s)
    {
    	NamShubClient.toServer(0, "gamechat " + gameID + " " + s);
    }
    public void setList(HashMap<Player,ArrayList<Unit>> contents)
    {
    	dlm.clear();
    	for(Player p: contents.keySet())
    	{
    		dlm.addElement(p.getName());
    		for(Unit u: contents.get(p))
    			dlm.addElement(u.getName());
    		dlm.addElement("");
    	}
    }
    public int getGameID()
    {
    	return gameID;
    }
    public void remoteClick(int x, int y, Color c)
    {
        System.err.println("Remote click registered. " + x + "," + y + " " + c);
    	df.updateSelection(x, y, c);
    }

    public void keyTyped(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_ENTER)
        {
            NamShubClient.toServer(0, "gamechat " + gameID + " " + inputField.getText());
            inputField.setText("");
        }     
    }

    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    public void actionPerformed(ActionEvent e) 
    {
        if (e.getActionCommand().equals("Send"))
        {
            send(inputField.getText());
            inputField.setText("");
        }
        else if (e.getActionCommand().equals("Move"))
        {
        	limbo.clear();
        	for (Unit u:(Unit[])contentsList.getSelectedValues())
        		limbo.add(u);
        	if (!limbo.isEmpty())
        	{
        		moveStart = hg.getCurrentlySelected();
        		activeMove = true;
        	}
        }
        else if (e.getActionCommand().equals("Undo"))
        {
        	if (!currentTurn.getActions().isEmpty())
        	{
        		try {
        			currentTurn.getActions().get(currentTurn.getActions().size()-1).undo();
        			currentTurn.getActions().remove(currentTurn.getActions().size()-1);
        		} catch (IllegalUndoException e1) {
        			// TODO Auto-generated catch block
        			e1.printStackTrace();
        		}	
        	}
        	
        }
        else if (e.getActionCommand().equals("End Turn"))
        {
        	if (JOptionPane.showConfirmDialog(
        		    this,
        		    "Prepare orders for encryption and delivery?",
        		    "Confirm Orders",
        		    JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
        	{
    			NamShubClient.toServer(1, currentTurn);
    			currentTurn = new Turn(currentTurn.getNumber()+1);
        	}
        }
        else if (e.getActionCommand().equals("Autoscroll"))
        {
        	autoScroll = !autoScroll;
        }
        else if (e.getActionCommand().equals("Add Unit"))
        {
        	unitPurchaseWindow = new UnitPurchaseWindow(hg.getCurrentlySelected(), currentTurn, player);
        	unitPurchaseWindow.setVisible(true);
        	unitPurchaseWindow.setSize(600,600);
        }
        else if (e.getActionCommand().equals("Map"))
        {
        	miniMap.setVisible(true);
        	miniMap.setSize(600,600);
        }
    }
    public void append(String s)
    {
        chatLogArea.append(s+"\n");
        if (autoScroll)
			chatLogArea.setCaretPosition(chatLogArea.getDocument().getLength());
    }
    public void clearLimbo(Hexagon selectedHex)
    {
		currentTurn.addMove(player, moveStart, selectedHex, limbo, gameID);
    }
	public void addPlayer(String string) {
		// TODO Auto-generated method stub
		
	}
}

/**
 * Responsible for rendering the world map and everything on it.
 * Creates a buffered image representing the current state of the board, and allows the player to pan over it by dragging the mouse.
 * Also handles tile selection and other map interactions.
 * 
 * @see invisible Hello World
 */
class DrawFrame extends Canvas implements MouseInputListener
{
    //TODO fix fixed resolution
	private Point cv1 = Point.ORIGIN, cv2 = new Point(cv1.getX()+getWidth(),cv1.getY()+getHeight());
    private static final long serialVersionUID = -5108636112428055820L;
    private HexGrid hg = null;
    private int startx, starty;
    private Image dbImage = null;
    private Graphics dbg = null;
    private RelativeDistanceComparator rdc = new RelativeDistanceComparator();
    private ArrayList<Hexagon> hgt = new ArrayList<Hexagon>(); 
    private PrimaryGUI GUI = null;
    
    public DrawFrame(HexGrid hg, PrimaryGUI GUI)
    {
    	this.GUI = GUI;
        this.hg = hg;
        setForeground(Color.BLACK);
        setBackground(Color.WHITE);
        dbImage = new BufferedImage((int)hg.getWidth(),(int)hg.getHeight(),BufferedImage.TYPE_INT_ARGB);
        dbg = dbImage.getGraphics();
    }
    public void paint(Graphics g)
    {
    	for(Hexagon h:hg)
            h.render(g);
    }
    public void update(Graphics g)
    {
    	// clear screen in background
        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, dbImage.getWidth(null), dbImage.getHeight(null));
        
        // draw elements in background
        dbg.setColor(getForeground());
        paint(dbg);
        
        cv2.setX(cv1.getX()+this.getWidth());
        cv2.setY(cv1.getY()+this.getHeight());
        
        g.drawImage(dbImage, 
        		0, 0, this.getWidth(), this.getHeight(), 
        		(int)cv1.getX(), (int)cv1.getY(), (int)cv2.getX(), (int)cv2.getY(), null);
    }
    public void setView(int x, int y)
    {
    	cv1.setX(x);
    	cv1.setY(y);
    	if (cv1.getX()+this.getWidth()>=dbImage.getWidth(null))
    		cv1.setX(dbImage.getWidth(null)-this.getWidth()-10);
    	if(cv1.getY()+this.getHeight()>=dbImage.getHeight(null))
    		cv1.setY(dbImage.getHeight(null)-this.getHeight()-10);
    	if (cv1.getX()<0)
    		cv1.setX(0);
    	if (cv1.getY()<0)
    		cv1.setY(0);
    	repaint();
    }
    public void changeView(int dx, int dy)
    {
    	cv1.setX(cv1.getX() - dx);	
    	cv1.setY(cv1.getY() - dy);
    	if (cv1.getX()+this.getWidth()>=dbImage.getWidth(null))
    		cv1.setX(dbImage.getWidth(null)-this.getWidth()-10);
    	if(cv1.getY()+this.getHeight()>=dbImage.getHeight(null))
    		cv1.setY(dbImage.getHeight(null)-this.getHeight()-10);
    	if (cv1.getX()<0)
    		cv1.setX(0);
    	if (cv1.getY()<0)
    		cv1.setY(0);
    	repaint();
    }
    public void updateSelection(int x, int y, Color c)
    {
        //crossCount = 0;
        rdc.setClickCoords(new Point(x,y));
        //Line testLine = new Line(Point.ORIGIN, new Point(x,y));
        hgt = hg.getList();
        Collections.sort(hgt, rdc);
        /*for (Hexagon h:hgt)
        {
            lines = h.getLines();
            for (Line l:lines)
            {
                if (testLine.crosses(l))
                    crossCount++;
            }
            if (crossCount == 1)
            {
                hg.select(h);
                GUI.setList(h.getForcesPresent());
                return;
            }
            crossCount = 0;
        }*/
        hg.select(hgt.get(0));
        GUI.setList(hgt.get(0).getForcesPresent());
        repaint();
    }
    public void updateSelection(int x, int y)
    {
    	//crossCount = 0;
		rdc.setClickCoords(new Point(x,y));
        //Line testLine = new Line(Point.ORIGIN, new Point(x,y));
		hgt = hg.getList();
		Collections.sort(hgt, rdc);
		/*for (Hexagon h:hgt)
		{
			lines = h.getLines();
			for (Line l:lines)
			{
				if (testLine.crosses(l))
					crossCount++;
			}
			if (crossCount == 1)
			{
				hg.select(h);
				GUI.setList(h.getForcesPresent());
				return;
			}
            crossCount = 0;
		}*/
		hg.select(hgt.get(0));
		GUI.setList(hgt.get(0).getForcesPresent());
		repaint();
    }
	public void mouseClicked(MouseEvent e) 
	{
		//TODO clean this mess up
		
		if (e.getButton()==MouseEvent.BUTTON1)
		{
			updateSelection((int)(e.getX()+cv1.getX()),(int)(e.getY()+cv1.getY()));
			repaint();
		}
	}
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mousePressed(MouseEvent e) {
		if (e.getModifiersEx()==MouseEvent.BUTTON3_DOWN_MASK)
		{
			startx = e.getX();
			starty = e.getY();
		}
	}
	public void mouseReleased(MouseEvent e) {

	}
	public void mouseDragged(MouseEvent e) 
	{
		if (e.getModifiersEx()==MouseEvent.BUTTON3_DOWN_MASK)
			changeView(e.getX()-startx,e.getY()-starty);
	}
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * @return cv1
	 */
	public Point getCv1() {
		return cv1;
	}
	/**
	 * @return cv2
	 */
	public Point getCv2() {
		return cv2;
	}
	/**
	 * @return the dbImage
	 */
	public Image getDbImage() {
		return dbImage;
	}
}
class MiniMap extends Canvas implements MouseInputListener
{
	private static final long serialVersionUID = 568245040952517299L;
	int startx, starty;
	DrawFrame df = null;
	double xScale, yScale;
	Graphics g;
	public MiniMap(DrawFrame df)
	{
		this.df = df;
		g = this.getGraphics();
		setBackground(Color.WHITE);
	}
	public void update()
	{
		// clear screen in background
        g.setColor(getBackground());
        g.fillRect(0, 0, df.getDbImage().getWidth(null), df.getDbImage().getHeight(null));
        
        
		g.drawImage(df.getDbImage(), 
        		0, 0, this.getWidth(), this.getHeight(), null);
		g.setColor(Color.WHITE);
		g.drawRect((int)(df.getCv1().getX()/xScale),(int)(df.getCv1().getY()/yScale),(int)(df.getCv2().getX()/xScale),(int)(df.getCv2().getY()/yScale));
	}
	public void mouseClicked(MouseEvent e) {
		/*if (e.getModifiersEx()==MouseEvent.BUTTON1_DOWN_MASK)
		{
			xScale = df.getWidth()/getWidth();
			yScale = df.getHeight()/getHeight();
			df.setView((int)(e.getX()*xScale),(int)(e.getY()*yScale));
		}*/
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {
		if (e.getModifiersEx()==MouseEvent.BUTTON1_DOWN_MASK)
		{
			xScale = df.getWidth()/getWidth();
			yScale = df.getHeight()/getHeight();
			df.setView((int)(e.getX()*xScale),(int)(e.getY()*yScale));
			startx = e.getX();
			starty = e.getY();
		}
	}
	public void mouseReleased(MouseEvent e) {

	}
	public void mouseDragged(MouseEvent e) 
	{
		xScale = df.getWidth()/getWidth();
		yScale = df.getHeight()/getHeight();
		if (e.getModifiersEx()==MouseEvent.BUTTON1_DOWN_MASK)
			df.changeView((int)((e.getX()-startx)*xScale),(int)((e.getY()-starty)*yScale));
	}
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
class KeyAdapter implements KeyListener
{
    public void keyPressed(KeyEvent ke) {}

    public void keyReleased(KeyEvent ke) {}

    public void keyTyped(KeyEvent ke) {}
}
class UnitPurchaseWindow extends JFrame implements ActionListener, ListSelectionListener
{
	private static final long serialVersionUID = -6370696802469019920L;
	private JList unitPurchaseList = null;
	private DefaultListModel unitPurchaseDLM = null;
	private JPanel purchasePanel = null;
	private JButton purchaseButton = null;
	private Unit unit;
	private Hexagon d;
	private Turn t;
	private Player p;
	
	public UnitPurchaseWindow(Hexagon destination, Turn turn, Player player)
	{
		d = destination;
		t = turn;
		p = player;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Purchase Units:");
		setBackground(Color.LIGHT_GRAY);
		setSize(new Dimension(600, 600));
		setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		setResizable(false);

		unitPurchaseDLM = new DefaultListModel();
		unitPurchaseList = new JList(unitPurchaseDLM);
		unitPurchaseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		unitPurchaseList.addListSelectionListener(this);
		unitPurchaseList.setPreferredSize(new Dimension(600,500));
		
		unitPurchaseDLM.add(0, "Infantry");
		
		purchasePanel = new JPanel();
		purchasePanel.setBackground(Color.LIGHT_GRAY);
		purchasePanel.setSize(100,600);
		
		purchaseButton = new JButton("Purchase Unit");
		purchaseButton.addActionListener(this);
		purchasePanel.add(purchaseButton);
		
		add(unitPurchaseList);
		add(purchasePanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Purchase Unit"))
		{
			t.addUnit(unit, d);
		}
		
	}

	@Override
	public void valueChanged(ListSelectionEvent vc) {
		if (unitPurchaseList.getSelectedValue().equals("Infantry"))
		{
			unit = new Infantry(null, "Infantry", null, p, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
		}
		
	}
}

