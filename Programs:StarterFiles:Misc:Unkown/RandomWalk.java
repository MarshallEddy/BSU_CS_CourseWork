
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
/**
 * 
 * @author MarshallEddy
 *
 */

public class RandomWalk
{
	private boolean done = false;
	private int gridSize;
	
	Point start = new Point(0,0);
	ArrayList<Point> path = new ArrayList<Point>();
	Random rand;

	/**
	 * Initializes instance variables and the starting point of the walk,
	 * but doesn't create the walk. Creates random number generator without a seed.
	 * 
	 * @param gridSize
	 */
	public RandomWalk(int gridSize)
	{
		this.gridSize = gridSize;
		rand = new Random();
		path.add(start);
	}


	/**
	 * Same as the above constructor except there is a specified seed value for
	 * the random generator.
	 * 
	 * @param gridSize
	 * @param seed
	 */
	public RandomWalk(int gridSize, long seed)
	{
		this.gridSize = gridSize;
		rand = new Random(seed);
		path.add(start);
	}


	/**
	 * Makes the walk go one more step.
	 * 
	 */
	public void step()

	{	
		Point currentP = new Point(path.get(path.size()-1));


		if (rand.nextBoolean() == true)	//move x value once to the right.
		{
			if(valid(currentP.x+1, currentP.y) == true)
			{

				Point addX = new Point(currentP.x+1, currentP.y);
				path.add(addX);
			}
		}

		else	//moves y value up once.
		{
			if(valid(currentP.x, currentP.y+1) == true)
			{
				Point addY = new Point(currentP.x, currentP.y+1);
				path.add(addY);
			}	

		}
		Point check = new Point(path.get(path.size()-1));
		if(check.x == gridSize-1 && check.y == gridSize-1)
		{
			done = true;
		}
	}

	/**
	 * Validates that the next point will be on the grid.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean valid(int x, int y)
	{
		if(x >= 0 && x <= gridSize-1 && y>= 0 && y<= gridSize-1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Creates the entire walk in one call by internally using the 'step()' method.
	 * 
	 */
	public void createWalk()
	{
		while (done == false)
		{
			step();
		}
	}


	/**
	 * Returns the current value of the 'done' variable.
	 * 
	 * @return
	 */
	public boolean isDone()
	{
		return done;
	}


	/**
	 * Getter to get reference to the random walk path.
	 * 
	 * @return
	 */
	public ArrayList<Point> getPath()
	{
		return path;
	}


	/**
	 * Returns the path as a nicely formatted string.
	 * 
	 */
	public String toString()
	{
		String pathSize=  "";
		for(int i=0; i< path.size(); i++)
		{
			pathSize += "[" + path.get(i).x + "," +path.get(i).y + "] ";
		}
		return  pathSize;
	}
}