import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * 
 * Driver class for the MineWalkerPanel.
 * 
 * @author MarshallEddy
 *
 */


public class MineWalker
{
	 public static void main(String[] args)
	{
		 JFrame myFrame = new JFrame("MineWalker");
		 myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 myFrame.setPreferredSize(new Dimension(800,600));
		 
		 MineWalkerPanel myPanel = new MineWalkerPanel();
		 myFrame.getContentPane().add(myPanel);

	     myFrame.pack();
	     myFrame.setVisible(true);
		 
	 }
}
