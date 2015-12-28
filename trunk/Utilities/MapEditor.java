package Utilities;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MapEditor extends JPanel implements MouseListener, ActionListener {
	private static final long serialVersionUID = 830959077334114803L;
	private Dimension area; // indicates area taken up by graphics
	private Vector<Rectangle> circles; // coordinates used to draw graphics
	private JPanel drawingPane;

	private final Color colors[] = { Color.red, Color.blue, Color.green,
			Color.orange, Color.cyan, Color.magenta, Color.darkGray,
			Color.yellow };
	private final int color_n = colors.length;
	static Color radioColor= Color.yellow;


	public MapEditor() {
		super(new BorderLayout());
	

		JRadioButton swamp = new JRadioButton("Swamp");
		swamp.setSelected(true);
		swamp.addActionListener(this);
		JRadioButton mountain = new JRadioButton("Mountain");
		mountain.addActionListener(this);
		JRadioButton forest = new JRadioButton("Forest");
		forest.addActionListener(this);
		JRadioButton ocean = new JRadioButton("Ocean");
		ocean.addActionListener(this);
		JRadioButton planes = new JRadioButton("Planes");
		planes.addActionListener(this);

		ButtonGroup group = new ButtonGroup();
		group.add(swamp);
		group.add(mountain);
		group.add(forest);
		group.add(ocean);
		group.add(planes);
		

		JPanel radioPanel = new JPanel(new GridLayout(0, 1));
		radioPanel.add(swamp);
		radioPanel.add(mountain);
		radioPanel.add(forest);
		radioPanel.add(ocean);
		radioPanel.add(planes);
		
		add(radioPanel, BorderLayout.LINE_START);
		area = new Dimension(0, 0);
		circles = new Vector<Rectangle>();

		// Set up the drawing area.
		drawingPane = new DrawingPane();
		drawingPane.setBackground(Color.white);
		drawingPane.addMouseListener(this);

		// Put the drawing area in a scroll pane.
		JScrollPane scroller = new JScrollPane(drawingPane);
		scroller.setPreferredSize(new Dimension(800, 600));
		int size=20;
    	int height=(int)(size * (Math.sqrt(3) / 2));
		for (int j=0;j<10;j++){
    		for (int i=1;i<10;i++){
    			Hex hex = new Hex(i*size*3,10+(height*2*j),size);
    			
   		}
    		for (int i=1;i<10;i++){
    			Hex hex = new Hex((i*size*3)+(int)(size*1.5),10+(height*2*j)+height,size);
    			    		}
    	}

		// Lay out this demo.
		add(scroller, BorderLayout.CENTER);
	}
    public void actionPerformed(ActionEvent e) {
    	System.out.println("event performed");
    	if (e.getActionCommand().equals("Swamp")){
    		radioColor=Color.yellow;
    	}
    	if (e.getActionCommand().equals("Mountain")){
    		radioColor=Color.cyan;
    	}
    	if (e.getActionCommand().equals("Forest")){
    		radioColor=Color.green;
    	}
    	if (e.getActionCommand().equals("Planes")){
    		radioColor=Color.lightGray;
    	}
    	if (e.getActionCommand().equals("Ocean")){
    		radioColor=Color.blue;
    	}
    }


	/** The component inside the scroll pane. */
	public class DrawingPane extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 201049470022126786L;

		protected void paintComponent(Graphics g) {
        	super.paintComponent(g);
            for (int i=0;i<Hex.hexList.size();i++) {
            	//g.drawPolygon(Hex.hexList.get(i).getPoly());
            	g.setColor(Hex.hexList.get(i).getColor());
            	g.fillPolygon(Hex.hexList.get(i).getPoly());
            }
        	Rectangle rect;
            for (int i = 0; i < circles.size(); i++) {
                rect = circles.elementAt(i);
                g.setColor(colors[(i % color_n)]);
                g.fillOval(rect.x, rect.y, rect.width, rect.height);
            }
        }
	}

	// Handle mouse events.
	public void mouseReleased(MouseEvent e) {
		Hex target = Hex.getHex(e.getPoint());
		if (target!=null){
			target.setColor(MapEditor.radioColor);
		}
		drawingPane.repaint();
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("Map Editor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		JComponent newContentPane = new MapEditor();
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// Display the window.
		frame.pack();
		frame.setVisible(true);

	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
