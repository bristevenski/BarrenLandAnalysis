package main;

import java.util.Queue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This class runs the analysis on a Farm object with the barren land
 * coordinates read from STDIN.
 * @author Brianna Stevenski
 *
 */
public class BarrenLandAnalysis 
{
	public static List<Integer> FindFertileLand( Farm farm )
	{
		int current_area = 0;
		Queue<Integer[]> land_to_process = new LinkedList<>();
		List<Integer> fertile_land = new ArrayList<>();
		
		for( int x = 0; x < Farm.WIDTH; x++ )
		{
			for( int y = 0; y < Farm.HEIGHT; y++ )
			{
				if( farm.farm_land[x][y] == Farm.UNVISITED )
				{
					// Found new area of fertile land, add to queue to process
					land_to_process.add(new Integer[] {x, y});
					farm.farm_land[x][y] = Farm.FERTILE;
				}
				
				while( !land_to_process.isEmpty() )
				{
					Integer[] current_cell = land_to_process.remove();
					int curr_x = current_cell[0];
					int curr_y = current_cell[1];
					
					// Process fertile land: add to the area and check neighboring cells for more fertile land
					current_area++;
					
					if( curr_x - 1 >= 0 && farm.farm_land[curr_x - 1][curr_y] == Farm.UNVISITED )
					{
						// neighboring cell is fertile and unvisited, add to queue to process
						land_to_process.add(new Integer[] {curr_x - 1, curr_y});
						farm.farm_land[curr_x - 1][curr_y] = Farm.FERTILE;
					}				

					if( curr_x + 1 < Farm.WIDTH && farm.farm_land[curr_x + 1][curr_y] == Farm.UNVISITED )
					{
						// neighboring cell is fertile and unvisited, add to queue to process
						land_to_process.add(new Integer[] {curr_x + 1, curr_y});
						farm.farm_land[curr_x + 1][curr_y] = Farm.FERTILE;
					}

					if( curr_y - 1 >= 0 && farm.farm_land[curr_x][curr_y - 1] == Farm.UNVISITED )
					{
						// neighboring cell is fertile and unvisited, add to queue to process
						land_to_process.add(new Integer[] {curr_x, curr_y - 1});
						farm.farm_land[curr_x][curr_y - 1] = Farm.FERTILE;
					}

					if( curr_y + 1 < Farm.HEIGHT && farm.farm_land[curr_x][curr_y + 1] == Farm.UNVISITED )
					{
						// neighboring cell is fertile and unvisited, add to queue to process
						land_to_process.add(new Integer[] {curr_x, curr_y + 1});
						farm.farm_land[curr_x][curr_y + 1] = Farm.FERTILE;
					}
				}
				
				// Queue is empty, which means we either found no fertile land or found all the fertile land within the current area
				// Add current_area to the list of fertile land and continue searching the rest of the farm land
				if( current_area > 0 )
				{
					fertile_land.add(current_area);
				}
				current_area = 0;
			}
		}
		
		//Land is done processing and all fertile land has been found, return the list of areas
		return fertile_land;
	}
	
	public static void PrintFertileLand( Farm farm )
	{
		List<Integer> fertile_land = FindFertileLand(farm);
		if( fertile_land.isEmpty() )
		{
			// No fertile land was found
			System.out.print("No fertile land found");
		}
		else
		{
			Collections.sort(fertile_land);
			
			for( int i = 0; i < fertile_land.size(); i++ )
			{
				System.out.print(fertile_land.get(i));
				System.out.print(" ");
			}
		}
	}
	
	public static void main( String[] args )
	{
		Farm farm = new Farm();
		
		try
		{
			// read the coordinates for the barren land and mark them on the farm
			Reader.ProcessInput(farm);
						
			// find all fertile land areas, sort them, and print them
			PrintFertileLand(farm);
			
		}
		catch ( Exception e )
		{
			// error occurred when reading input, print error message and exit
            System.out.print(e.getMessage());
            System.exit(1);
        }
	}
}
