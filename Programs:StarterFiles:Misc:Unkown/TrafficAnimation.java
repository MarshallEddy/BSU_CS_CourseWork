/* 
 * TrafficAnimation.java 
 * CS 121 Project 1: Traffic Animation
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Animates a Truck moving across a street with driver visible
 * @author Marshall Eddy
 */
@SuppressWarnings("serial")
public class TrafficAnimation extends JPanel {
	//Note: This area is where you declare constants and variables that
	//	need to keep their values between calls	to paintComponent().
	//	Any other variables should be declared locally, in the
	//	method where they are used.

	//constant to regulate the frequency of Timer events
	// Note: 100ms is 10 frames per second - you should not need
	// a faster refresh rate than this
	private final int DELAY = 100; //milliseconds
	//anchor coordinate for drawing / animating
	private int x = 0;
	//pixels added to x each time paintComponent() is called
	private int stepSize = 10;
	
	/* This method draws on the panel's Graphics context.
	 * This is where the majority of your work will be.
	 *
	 * (non-Javadoc)
	 * @see java.awt.Container#paint(java.awt.Graphics)
	 */
	public void paintComponent(Graphics canvas) 
	{
		//clears the previous image
		//super.paintComponent(canvas);
		
		//account for changes to window size
		int width = getWidth(); // panel width
		int height = getHeight(); // panel height
		
		//Fill the canvas with the background color
		Color myColor = new Color(50,255,150);
		canvas.setColor(myColor);
		canvas.fillRect(0, 0, width, height);
    	
		//Calculate the new position
		x = (x + stepSize) % width;
    	
		
		int squareSide = height/5;
		int y = height/2 - squareSide/2;
		
		canvas.setColor(Color.gray);
		canvas.fillRect(0, y-20, width, height/2);    //the road
		
		canvas.setColor(Color.red);
		canvas.fillRect(x, y, squareSide*3, squareSide+20);		//body of the truck
		
		canvas.setColor(Color.black);
		canvas.setFont(new Font("Arial Black", Font.BOLD, width/10));
		canvas.drawString("I love driving", (width/5)-40 , height/7);
			
		canvas.fillOval(x+squareSide/4, y+squareSide, squareSide/2 , squareSide/2);	//wheel
		canvas.fillOval(x+squareSide*2, y+squareSide, squareSide/2 ,squareSide/2);	//wheel
		
		canvas.setColor(Color.white);
		canvas.fillOval(x+squareSide*2, y, width/10, height/8);//truck window
		canvas.setColor(Color.yellow);
		canvas.fillOval(width/2, height/6, width/6, height/6);//avatar
		
		int headx = width/10;
		int heady = height/8;
		
		canvas.setColor(Color.black);
		canvas.fillOval((x+squareSide*2)+headx/4, 10*heady/3, headx/2, heady/2);	//drivers head
		canvas.drawLine((x+squareSide*2)+headx/2, 10*heady/3,
			            (x+squareSide*2)+headx/2 , 17*heady/4);//drivers body
		canvas.fillArc(width/2, height/6, width/6, height/6, -20, 60);//avatars mouth
		canvas.setColor(Color.darkGray);
		canvas.fillOval(width/7, 7*height/8, width/5, height/8); //man-hole
		canvas.fillOval((width/2)+20, (height/5)-5, width/30, height/30);
		
	}

	/**
	 * Constructor for the display panel initializes
	 * necessary variables. Only called once, when the
	 * program first begins.
	 * This method also sets up a Timer that will call
	 * paint() with frequency specified by the DELAY
	 * constant.
	 */
	public TrafficAnimation() 
	{
		setBackground(Color.black);
		//Do not initialize larger than 800x600
		int initWidth = 600;
		int initHeight = 400;
		setPreferredSize(new Dimension(initWidth, initHeight));
		this.setDoubleBuffered(true);
		
		//Start the animation - DO NOT REMOVE
		startAnimation();
	}

	/////////////////////////////////////////////
	// DO NOT MODIFY main() or startAnimation()
	/////////////////////////////////////////////
	
	/**
	 * Starting point for the TrafficAnimation program
	 * @param args unused
	 */
	public static void main (String[] args)
	{
		JFrame frame = new JFrame ("Traffic Animation");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new TrafficAnimation());
		frame.pack();
		frame.setVisible(true);
	}

   /**
    * Create an animation thread that runs periodically
	* DO NOT MODIFY this method!
	*/
    private void startAnimation()
    {
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                repaint();
            }
        };
        new Timer(DELAY, taskPerformer).start();
    }
}
