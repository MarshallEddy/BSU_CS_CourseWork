import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * 
 * 
 * The main method reads in data from the keyboard for three songs
 * (song title. artist, play time, and file name).
 * 
 * Then it will find the average play time of the songs, finds the song with the
 * play time closest to 4 minutes, and builds a play list of songs from the shortest
 * to the longest play time and print the songs in order.
 * 
 * 
 * @author Marshall Eddy
 * 
 */
public class PlayList
{
	public static void main(String[] args)
	{
		String title1, title2, title3;
		String artist1, artist2, artist3;
		String playTime1, playTime2, playTime3;
		String fileName1, fileName2, fileName3;

		
		
		Scanner scan = new Scanner(System.in);
		
		// Obtaining first list
		System.out.println("Enter title: ");
		title1 = scan.nextLine();
		System.out.println("Enter artist: ");
		artist1 = scan.nextLine();
		System.out.println("Enter play time (mm:ss): ");
		playTime1 = scan.nextLine();
		System.out.println("Enter file name: ");
		fileName1 = scan.nextLine();
		// Converts play time1 string to integers
		int index1 = playTime1.indexOf(':');
		int min1 = Integer.parseInt(playTime1.substring(0, index1));
		int sec1 = Integer.parseInt(playTime1.substring(index1+1, playTime1.length()));
		
		int minSec1 = min1*60;	// converts min1 to seconds
		int totalSec1 = sec1+minSec1;	// Combines seconds to get total seconds1
		
		Song song1 = new Song(title1, artist1, totalSec1, fileName1);
		
		// Obtaining second list
		System.out.println("Enter title: ");
		title2 = scan.nextLine();
		System.out.println("Enter artist: ");
		artist2 = scan.nextLine();
		System.out.println("Enter play time (mm:ss): ");
		playTime2 = scan.nextLine();
		System.out.println("Enter file name: ");
		fileName2 = scan.nextLine();
		
		// Converts play time2 string to integers
		int index2 = playTime2.indexOf(':');
		int min2 = Integer.parseInt(playTime2.substring(0, index2));
		int sec2 = Integer.parseInt(playTime2.substring(index2+1, playTime2.length()));
		
		int minSec2 = min2*60;	// converts min2 to seconds
		int totalSec2 = sec2+minSec2;	// Combines seconds to get total seconds2	
		
		Song song2 = new Song(title2, artist2, totalSec2, fileName2);
		
		// Obtaining third list
		System.out.println("Enter title: ");
		title3 = scan.nextLine();
		System.out.println("Enter artist: ");
		artist3 = scan.nextLine();
		System.out.println("Enter play time (mm:ss): ");
		playTime3 = scan.nextLine();
		System.out.println("Enter file name: ");
		fileName3 = scan.nextLine();
		
		// Converts play time3 string to integers
		int index3 = playTime3.indexOf(':');
		int min3 = Integer.parseInt(playTime3.substring(0, index3));
		int sec3 = Integer.parseInt(playTime3.substring(index3+1, playTime3.length()));
		
		int minSec3 = min3*60;	// converts min3 to seconds
		int totalSec3 = sec3+minSec3;  // Combines seconds to get total seconds3
		
		Song song3 = new Song(title3, artist3, totalSec3, fileName3);
		
		double avgTime = ((double)(totalSec1+totalSec2+totalSec3)/3.0);	// Average play time of all songs
		
		DecimalFormat myFormatter = new DecimalFormat("000.00");
		String output = myFormatter.format(avgTime);
		
		int diff1 = Math.abs(song1.getPlayTime()-240);
		int diff2 = Math.abs(song2.getPlayTime()-240);
		int diff3 = Math.abs(song3.getPlayTime()-240);
		
		System.out.println(" ");
		System.out.println("Average playtime: "+ output);
		System.out.println(" ");
		
		Song close = null;
		//	Logic for which song is closest to 240 seconds.
		if (diff1 <= diff2 && diff1 <= diff3)
		{
			close = song1;
			System.out.println("Song with play time closest to 240 seconds is: " + close.getTitle());
		}
	
		else if (diff2 <= diff1 && diff2 <= diff3)
		{
			close = song2;
			System.out.println("Song with play time closest to 240 seconds is: " + close.getTitle());
		}	
			
		else if (diff3 <= diff1 && diff3 <= diff2)
		{
			close = song3;
			System.out.println("Song with play time closest to 240 seconds is: " + close.getTitle());
		}
		
		else {System.out.println("Two songs were in equal length.");}
		
		
		Song smallLength = null;
		Song medLength = null;
		Song longLength = null;
		//	Logic for deciding which song is the shortest, longest, or in-between.
		
		// Smallest length of a song (logic)
		if (totalSec1 <= totalSec2 && totalSec1 <= totalSec3)
		{
			smallLength = song1;
		}
		else if (totalSec2 <= totalSec1 && totalSec2 <= totalSec3)
		{
			smallLength = song2;
		}
		else if (totalSec3 <= totalSec1 && totalSec3 <= totalSec3)
		{
			smallLength = song3;
		}
		
		// Medium length of a song (logic)
		if (totalSec1 <= totalSec2 && totalSec1 >= totalSec3)
		{
			medLength = song1;
		}
		else if (totalSec1 >= totalSec2 && totalSec1 <= totalSec3)
		{
			medLength = song1;
		}
		else if (totalSec2 <= totalSec1 && totalSec2 >= totalSec3)
		{
			medLength = song2;
		}
		else if (totalSec2 >= totalSec1 && totalSec2 <= totalSec3)
		{
			medLength = song2;
		}
		else if (totalSec3 <= totalSec1 && totalSec3 >= totalSec2)
		{
			medLength = song3;
		}
		else if (totalSec3 >= totalSec1 && totalSec3 <= totalSec2)
		{
			medLength = song3;
		}
	
		// Longest length of a song (logic)
		if (totalSec1 >= totalSec2 && totalSec1 >= totalSec3)
		{
			longLength = song1;
		}
		else if (totalSec2 >= totalSec1 && totalSec2 >= totalSec3)
		{
			longLength = song2;
		}
		else if (totalSec3 >= totalSec1 && totalSec3 >= totalSec3)
		{
			longLength = song3;
		}
		
		
		System.out.println(" ");
		System.out.println("==============================================================================");
		System.out.println("Title                Artist               File Name                  Play Time");
		System.out.println("==============================================================================");
		System.out.println(smallLength.toString());
		System.out.println(medLength.toString());
		System.out.println(longLength.toString());
		System.out.println("==============================================================================");
		
	}
}