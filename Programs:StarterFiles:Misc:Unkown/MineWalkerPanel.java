import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
/**
 * This is a GUI used to play as a game. The way the game works is similar to MineSweeper
 * You will start from the bottom right corner after starting a new game with a specified
 * Grid Size. The Default size is 10. You will then see an 'X' appear on your current position
 * and the tile will have of changed colors to the corresponding number of mines that surround it.
 * 
 * You can only move one space at a time and only move up/down and left/right, no diagonals/
 * hopping. Your goal is to reach the destination (top left corner) with only 3 lives to spare, once
 * you run out of lives, the game is over.
 * 
 * 
 * @author MarshallEddy
 *
 */
@SuppressWarnings("serial")
public class MineWalkerPanel extends JPanel implements ActionListener
{
	private JButton [][] buttons;
	private JTextField textPanel;
	private JButton newGame;
	private JButton showMines;
	private JButton showPath;
	private JLabel score;
	private JLabel lives;
	private int GRID_SIZE = 10; 
	private Color myColor;
	private JPanel gridPanel;
	private Random rand;
	private RandomWalk walk;
	private ArrayList<Point> minePoints;
	private int percentOfMines;
	private Point mine;
	private Point currentLocation = new Point();
	private int numLives = 3;
	private int scoreValue = 5000;
	private int numMoves = 0;
	private Point newMove;
	private boolean valid;
	
	public MineWalkerPanel()
	{
		myColor = new Color(78, 177, 199);
		this.setBackground(myColor);
		this.setPreferredSize(new Dimension(800,600));
		this.setLayout(new BorderLayout());

		
		gridPanel();
		
		controlPanel();
		
		scorePanel();		
		
		colorKeyPanel();	
		
		mainTitle();
		
		createPath();
		
		generateMines();
		
	}
	
	
	/**
	 * This method creates the Main Title Panel which appears above the grid.
	 */
	private void mainTitle()
	{
		JPanel mainTitle = new JPanel();
		JLabel title = new JLabel("MineWalker");
		title.setForeground(new Color(171, 7, 7));
		title.setFont(new Font("Helvetica", Font.BOLD, 24));
		mainTitle.add(title);
		mainTitle.setBackground(myColor);
		this.add(mainTitle, BorderLayout.NORTH);
	}

	
	/**
	 * This method creates the Color Key to let the player know what each color
	 * represents while playing,
	 */
	private void colorKeyPanel()
	{
		JPanel colorKeyPanel = new JPanel();
		colorKeyPanel.add(Box.createRigidArea(new Dimension(0,180)));
		JLabel key = new JLabel("Key");
		key.setFont(new Font("Helvetica", Font.BOLD, 14));
		key.setPreferredSize(new Dimension(50,10));
		colorKeyPanel.add(key);
		
		JLabel green = new JLabel("\t 0 mines nearby");
		green.setOpaque(true);
		green.setBackground(Color.green);
		green.setPreferredSize(new Dimension(120,10));
		colorKeyPanel.add(green);
		
		JLabel yellow = new JLabel("\t 1 mine   nearby");
		yellow.setOpaque(true);
		yellow.setBackground(Color.yellow);
		yellow.setPreferredSize(new Dimension(120,10));
		colorKeyPanel.add(yellow);
		
		JLabel orange = new JLabel("\t 2 mines nearby");
		orange.setOpaque(true);
		orange.setBackground(Color.orange);
		orange.setPreferredSize(new Dimension(120,10));
		colorKeyPanel.add(orange);
		
		JLabel red = new JLabel("\t 3 mines nearby");
		red.setOpaque(true);
		red.setBackground(Color.red);
		red.setPreferredSize(new Dimension(120,10));
		colorKeyPanel.add(red);
		
		JLabel black = new JLabel("\t Exploded mine ");
		black.setForeground(Color.white);
		black.setOpaque(true);
		black.setBackground(Color.black);
		black.setPreferredSize(new Dimension(120,10));
		colorKeyPanel.add(black);
	
		JLabel xYou = new JLabel("\t    'X' <-- You      ");
		xYou.setOpaque(true);
	    xYou.setBackground(Color.gray);
	    xYou.setPreferredSize(new Dimension(120,10));
	    colorKeyPanel.add(xYou);
	    
	    JLabel destination = new JLabel("\t Destination      ");
	    destination.setOpaque(true);
	    destination.setBackground(Color.cyan);
	    destination.setPreferredSize(new Dimension(120,10));
	    colorKeyPanel.add(destination);
	    
		colorKeyPanel.setLayout(new BoxLayout(colorKeyPanel, BoxLayout.Y_AXIS));
		colorKeyPanel.setBackground(myColor);
		this.add(colorKeyPanel, BorderLayout.WEST);		
	}
	
	
	/**
	 * This method is the code that creates the Score/Lives panel at the bottom of the GUI
	 * below the grid.
	 */
	private void scorePanel()
	{
		JPanel scorePanel = new JPanel();
		score = new JLabel("Score:  " + scoreValue);
		lives = new JLabel("Lives: " + numLives);
	
		scorePanel.add(score);
		scorePanel.add(Box.createRigidArea(new Dimension(30,0)));
		scorePanel.add(lives);
		scorePanel.setBackground(myColor);
		
		this.add(scorePanel, BorderLayout.SOUTH);	
	}
	
	
	/**
	 * This method creates all the main buttons. There is a button for starting a new game,
	 * showing all the mines on the grid, and showing the best path to get to the goal.
	 */
	private void controlPanel()
	{
		JPanel controlPanel = new JPanel();
		controlPanel.add(Box.createRigidArea(new Dimension(0,500)));
		controlPanel.setLayout(new GridLayout(4, 1));
		
		textPanel = new JTextField (5);
		textPanel.setEditable(true);
		Border blackLine = BorderFactory.createLineBorder(Color.black);
		Border loweredEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		textPanel.setBorder(blackLine);
		textPanel.setBorder(loweredEtched);
		JLabel gridSizeText = new JLabel("Grid Size: ");
		
		newGame = new JButton("New Game");
		newGame.addActionListener(this);
		
		showMines = new JButton("Show Mines");
		showMines.addActionListener(this);
		
		showPath = new JButton("Show Path");
		showPath.addActionListener(this);
		
		controlPanel.add(newGame);
		controlPanel.add(gridSizeText);
		controlPanel.add(textPanel);
		controlPanel.add(Box.createRigidArea(new Dimension(0,500000)));
		controlPanel.add(Box.createRigidArea(new Dimension(0,500000)));
		controlPanel.add(Box.createRigidArea(new Dimension(0,500000)));
		controlPanel.add(Box.createRigidArea(new Dimension(0,500000)));
		controlPanel.add(showMines);
		controlPanel.add(showPath);
		controlPanel.setBackground(myColor);
		controlPanel.setLayout(new GridLayout(20,5));
		controlPanel.setPreferredSize(new Dimension(150,600));
		
		this.add(controlPanel, BorderLayout.EAST);
	}
	
	
	/**
	 * This method creates the Grid Panel that is the main part of game.
	 */
	private void gridPanel()
	{
		gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
		
		buttons = new JButton[GRID_SIZE][GRID_SIZE];
		for(int i = 0; i< buttons.length; i++)
		{
			for(int j = 0; j< buttons.length; j++)
			{
				buttons [i][j] = new JButton();
				buttons [i][j].addActionListener(this);
				gridPanel.add(buttons[i][j]);
			}
		}
		gridPanel.setBackground(myColor);
		this.add(gridPanel, BorderLayout.CENTER);	
	}

	
	/**
	 * This method uses the button newGame from the controlPanel 
	 * to create a new game depending on the specified grid size.
	 * This 'new Game' button will create the mines and a path to the 
	 * end behind the scenes.
	 */
	private void newGame()
	{
		scoreValue = 5000;
		numLives = 3;
		newGame.setText("Give Up?");
		if(textPanel.getText().equals(""))
		{
			GRID_SIZE = 10;
			textPanel.setText("10");
		}
		else
		{
			int userGridSize = Integer.parseInt(textPanel.getText());
			if(userGridSize < 5 || userGridSize > 30)
			{
				JOptionPane.showMessageDialog(null, "INVALID GRID SIZE. Enter a number between 5-30", "ERROR",JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				GRID_SIZE = userGridSize;
			}
			if(userGridSize > 10)
			{
				numLives = 5;
			}
		}
		currentLocation.setLocation(GRID_SIZE-1, GRID_SIZE-1);
		gridPanel.removeAll();
		gridPanel();
		revalidate();
		for(int i=0; i < buttons.length; i++)
		{
			for(int j=0; j < buttons.length; j++)
			{
				buttons [i][j].setBackground(Color.LIGHT_GRAY);
			}
		}
		score.setText("Score:  " + scoreValue);
		lives.setText("Lives: " + numLives);
		createPath();
		generateMines();
		hideMines();
		hidePath();
		changeButton();
		buttons[currentLocation.x][currentLocation.y].setText("X");
		buttons [0][0].setBackground(Color.cyan);
	}
	
	
	/**
	 * This method randomly generates mines, which make up 25% of the grid.
	 */
	private void generateMines()
	{
		percentOfMines = (GRID_SIZE*GRID_SIZE-walk.getPath().size())/4;
		minePoints = new ArrayList<Point>();
		while(minePoints.size() <= percentOfMines)
		{
			rand = new Random();
			int xValue = rand.nextInt(GRID_SIZE);
			int yValue = rand.nextInt(GRID_SIZE);
			mine = new Point(xValue, yValue);
			if(walk.getPath().contains(mine))
			{
				
			}
			else
			{
				minePoints.add(mine);
			}
		}
	}
	

	/**
	 * This method is use to show the mines to the player by changing their color to black.
	 * This will happen when the user clicks the button, or the game ends.
	 */
	private void showMines()
	{
		for(int i = 0; i < buttons.length; i++)
		{
			for(int j = 0; j < buttons[i].length; j++)
			{
				Point buttonTestPoint = new Point(i,j);
				if(minePoints.contains(buttonTestPoint))
				{
					buttons[i][j].setBackground(Color.black);
				}
			}
		}
		showMines.setText("Hide Mines");
	}
	
	
	/**
	 * This method hides the mines from the user by changing their color gray.
	 */
	private void hideMines()
	{
		for(int i = 0; i < buttons.length; i++)
		{
			for(int j = 0; j < buttons[i].length; j++)
			{
				Point buttonTestPoint = new Point(i,j);
				if(minePoints.contains(buttonTestPoint))
				{
					buttons[i][j].setBackground(Color.LIGHT_GRAY);
				}
			}
		}
		showMines.setText("Show Mines");
	}
	
	
	/**
	 * This method uses the RandomWalk class to create a random path from corner to corner
	 * ensuring their will always be a way to the end.
	 */
	private void createPath()
	{
		walk  = new RandomWalk(GRID_SIZE);
		walk.createWalk();
	}
	
	
	/**
	 * This method shows the solution to the path by changing the color of the path
	 * to blue when the suer hit the correct button, or the game ends.
	 */
	private void showPath()
	{
		for(int i = 0; i < buttons.length; i++)
		{
			for(int j = 0; j < buttons[i].length; j++)
			{
				Point buttonsTestPoint = new Point(i,j);
				if(walk.getPath().contains(buttonsTestPoint))
				{
					buttons[i][j].setBackground(Color.blue);
					buttons[0][0].setBackground(Color.cyan);
				}
			}
		}
		showPath.setText("Hide Path");
	}
	
	
	/**
	 * This method hides the path from the player.
	 */
	private void hidePath()
	{
		for(int i = 0; i < buttons.length; i++)
		{
			for(int j = 0; j < buttons[i].length; j++)
			{
				Point buttonsTestPoint = new Point(i,j);
				if(walk.getPath().contains(buttonsTestPoint))
				{
					buttons[i][j].setBackground(Color.LIGHT_GRAY);
				}
			}
		}
		showPath.setText("Show Path");
	}
	
	/**
	 * Checks to make sure the move was a valid move.
	 * Valid moves being up/down and left/right.
	 */
	private void checkValidMove()
	{
		boolean validButton = buttons[currentLocation.x][currentLocation.y].isBackgroundSet();
		
		if(validButton == false)
		{
			JOptionPane.showMessageDialog(null, "INVALID MOVE: Press 'New Game' before playing.", "ERROR",JOptionPane.ERROR_MESSAGE);
		}
		
		Point nextMoveEast = new Point(currentLocation.x+1,currentLocation.y);
		Point nextMoveNorth = new Point(currentLocation.x,currentLocation.y+1);
		Point nextMoveWest = new Point(currentLocation.x-1,currentLocation.y);
		Point nextMoveSouth = new Point(currentLocation.x,currentLocation.y-1);
		
		
		if(nextMoveEast.x == newMove.x && nextMoveEast.y == newMove.y)
		{
			valid = true;
		}
		else if(nextMoveNorth.y == newMove.y && nextMoveNorth.x == newMove.x)
		{
			valid = true;
		}
		else if(nextMoveWest.x == newMove.x && nextMoveWest.y == newMove.y)
		{
			valid = true;
		}
		else if(nextMoveSouth.y == newMove.y && nextMoveSouth.x == newMove.x)
		{
			valid = true;
		}
		else
		{
			valid = false;
		}
	}
	
	
	/**
	 * Determines the number of mines near the current location and changes the color
	 * of your current location to the corresponding number of mines.
	 * This method also happens to have the code for when you win the game
	 * and keeps track of a lot of different points.
	 */
	private void changeButton()
	{
		int numMines = 0;
		Point testEast = new Point(currentLocation.x+1,currentLocation.y);
		Point testNorth = new Point(currentLocation.x,currentLocation.y+1);
		Point testWest = new Point(currentLocation.x-1,currentLocation.y);
		Point testSouth = new Point(currentLocation.x,currentLocation.y-1);
		
		if(minePoints.contains(testEast))
		{
			numMines ++;
		}
		if(minePoints.contains(testNorth))
		{
			numMines ++;
		}
		if(minePoints.contains(testWest))
		{
			numMines ++;
		}
		if(minePoints.contains(testSouth))
		{
			numMines ++;
		}
						
		if(numMines == 0)
		{
			buttons[currentLocation.x][currentLocation.y].setBackground(Color.green);
		}
		if(numMines == 1)
		{
			buttons[currentLocation.x][currentLocation.y].setBackground(Color.yellow);
		}
		if(numMines == 2)
		{
			buttons[currentLocation.x][currentLocation.y].setBackground(Color.orange);
		}
		if(numMines == 3)
		{
			buttons[currentLocation.x][currentLocation.y].setBackground(Color.red);
		}
		
		if(minePoints.contains(currentLocation))
		{
			scoreValue -= 200;
			numLives--;
			buttons[currentLocation.x][currentLocation.y].setBackground(Color.black);
			lives.setText("Lives: " + numLives);
			score.setText("Score:  " + scoreValue);
		}
		
		if(currentLocation.x == 0 && currentLocation.y ==0)
		{
			scoreValue += 300;
			score.setText("Score:  " + scoreValue);
			JOptionPane.showMessageDialog(null, "\t\tYou WON!!!\n\t\t Score: " + scoreValue,"You're the Best!",JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	/**
	 * Uses the Number of moves you made to calculate and add a final value to your score.
	 */
	private void scoreCalculation()
	{
		if(numMoves >= 10)
		{
			scoreValue -=(numMoves*2);		
		}
		if(numMoves >= 20)
		{
			scoreValue -= (numMoves*3);
		}	
		score.setText("Score:  " + scoreValue);
		
	}
	/**
	 * Checks to see if the game is over from a loss of lives.
	 */
	private void gameOverCheck()
	{
		if(numLives <= 0)
		{
			JOptionPane.showMessageDialog(null,"Sorry, you lose...Noob","You're a scrub", JOptionPane.INFORMATION_MESSAGE);
			showMines();
			showPath();
			
		}
		
	}
	
	/**
	 * All the code to make sure the buttons do what they're suppose to do  when they are clicked.
	 */
	@Override
	public void actionPerformed(ActionEvent event)
	{
		JButton source = (JButton) event.getSource();
		if(source == newGame)
		{
			if(newGame.getText().equals("Give Up?"))
			{
				showMines();
				showPath();
				newGame.setText("New Game");
			}
			else
			{
				newGame();
			}
		}
		else if(source == showMines)
		{
			if(showMines.getText().equals("Hide Mines"))
			{
				hideMines();
			}
			else
			{	
				showMines();
				scoreValue -= 5000;
			}	
		}
		else if(source == showPath)
		{
			if(showPath.getText().equals("Hide Path"))
			{
				hidePath();
			}
			else
			{
				showPath();
				scoreValue -= 5000;
			}
		}		
		else
		{
			for(int i = 0; i < buttons.length; i++)
			{
				for(int j = 0; j < buttons[i].length; j++)
				{
					if(source == buttons[i][j])
					{
						newMove = new Point();
						newMove.setLocation(i,j);
						checkValidMove();						
						if(valid == true)
						{
							buttons[currentLocation.x][currentLocation.y].setText("");
							currentLocation.setLocation(i,j);
							numMoves++;
							changeButton();
							buttons[i][j].setText("X");
							gameOverCheck();
						}
					}
				}
			}
			scoreCalculation();
		}
	}
}