package main;

/**
 * This class represents a 400m by 600m farm
 * @author Brianna Stevenski
 *
 */
public class Farm
{
	// Farm Land dimensions
	public static final int WIDTH    = 400;
	public static final int HEIGHT   = 600;
	
	// States of farm land
	public static final int BARREN    = -1;
	public static final int UNVISITED = 0;
	public static final int FERTILE   = 1;
	
	// Coordinates
	private static final int START_X = 0;
	private static final int END_X   = 2;
	private static final int START_Y = 1;
	private static final int END_Y   = 3;
	
	int farm_land[][] = new int[WIDTH][HEIGHT];
	
	/**
	 * Constructor - zeroes out the farm land
	 */
	public Farm()
	{
		for( int y = 0; y < HEIGHT; y++ )
		{
			for( int x = 0; x < WIDTH; x++ )
			{
				farm_land[x][y] = UNVISITED;
			}
		}
	}
	
	/**
	 * Marks all barren land as BARREN
	 * @param coordinates the coordinates given for the corners of the barren land rectangle
	 */
	public void MarkBarrenLand( Integer[] coordinates )
	{
		coordinates = CheckCoordinates( coordinates );
		int barren_start_x = coordinates[START_X];
		int barren_end_x   = coordinates[END_X];
		int barren_start_y = coordinates[START_Y];
		int barren_end_y   = coordinates[END_Y];
		
		for( int y = 0; y < HEIGHT; y++ )
		{
			for( int x = 0; x < WIDTH; x++ )
			{
				if( x >= barren_start_x && x <= barren_end_x &&
					y >= barren_start_y && y <= barren_end_y )
				{
					farm_land[x][y] = BARREN;
				}
			}
		}
	}
	
	/**
	 * Find the smaller of the x and y coordinates and add to Integer array in
	 * the following order:
	 * Lower X coordinate
	 * Upper X coordinate
	 * Lower Y coordinate
	 * Upper Y coordinate
	 * 
	 * This ensures the that rectangle is found correctly even if the user inputs the
	 * coordinates in the wrong order. Does not protect against x/y coordinates being
	 * passed in at y/x coordinates.
	 * 
	 * @param coordinates the coordinates to check for order
	 * @return the ordered coordinates
	 */
	private Integer[] CheckCoordinates( Integer[] coordinates )
	{
		Integer[] new_coords = new Integer[4];

		if( coordinates[0] <= coordinates[2] )
		{
			new_coords[START_X] = coordinates[0];
			new_coords[END_X]   = coordinates[2];
		}
		else
		{
			new_coords[START_X] = coordinates[2];
			new_coords[END_X]   = coordinates[0];
		}
		
		if( coordinates[1] <= coordinates[3] )
		{
			new_coords[START_Y] = coordinates[1];
			new_coords[END_Y]   = coordinates[3];
		}
		else
		{
			new_coords[START_Y] = coordinates[3];
			new_coords[END_Y]   = coordinates[1];
		}
		
		return new_coords;
	}
}