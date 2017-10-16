/**
 * The <code>Song</code> class represents a song. Each song
 * has a title, artist, play time, and file name.
 *
 * Here is an example of how a song can be created.
 *
 * <p><blockquote><pre>
 *     Song song = new Song("Amsterdam", "Paul Oakenfold", 318, "music/Amsterdam.mp3");
 * </pre></blockquote><p>
 *
 * Here is an example of how a song can be used.
 * <p><blockquote><pre>
 *     System.out.println("Artist: " + song.getArtist());
 *     System.out.println(song);
 * </pre></blockquote><p>
 *
 * @author amit
 */
public class Song
{
	private String title;
	private String artist;
	private int playTime; // in seconds
	private String fileName;
	
	/**
	 * Constructor: Builds a song using the given parameters.
	 * @param title song's title
	 * @param artist song's artist
	 * @param playTime song's length in seconds
	 * @param fileName song file to load
	 */
	public Song(String title, String artist, int playTime, String fileName)
	{
		this.title = title;
		this.artist = artist;
		this.playTime = playTime;
		this.fileName = fileName;
	}

	/**
     * Returns the title of this <code>Song</code>.
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
     *  Returns the artist of this <code>Song</code>.
	 * @return the artist
	 */
	public String getArtist()
	{
		return artist;
	}

	/**
     *  Returns the play time of this <code>Song</code> in seconds.
	 * @return the playTime
	 */
	public int getPlayTime()
	{
		return playTime;
	}

	/**
     *  Returns the file name of this <code>Song</code>.
	 * @return the fileName
	 */
	public String getFileName()
	{
		return fileName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return  String.format("%-20s %-20s %-25s %10s",title, artist, fileName, playTime);
	}
}
