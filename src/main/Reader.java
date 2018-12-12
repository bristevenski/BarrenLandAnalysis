package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/**
 * This class reads and validates the user input
 * @author Brianna Stevenski
 *
 */
public class Reader
{

    public static Integer convertCoordinates( String text ) throws Exception
    {
        try 
        {
            return Integer.parseInt(text);
        } 
        catch (Exception e) 
        {
            throw new Exception("Coordinates must be integers\n");
        }
    }

    static void GetAllBarrenLand( String input, Farm farm ) throws Exception
    {
        // remove the unneeded characters
        input = input.replace("{", "");
        input = input.replace("}", "");
        input = input.replace("\"", "");
        input = input.replace("“", "");
        input = input.replace("”", "");
        
        StringTokenizer st = new StringTokenizer ( input, "," );
        
        if( !st.hasMoreTokens() )
        {
        	//empty input, throw exception
        	throw new Exception("Empty input given\n");
        }
        
        for( int i = 0; st.hasMoreTokens(); i++ )
        {
        	//extract the barren land coordinates and mark them on the farm land
        	String[] coordinates = st.nextToken().trim().split(" ");
        	if( coordinates.length != 4 )
        	{
        		// Format not used correctly, throw exception
        		throw new Exception("Barren Land Coordinates not given in correct format\n");
        	}
        	
        	if( convertCoordinates(coordinates[0]) >= 0 && convertCoordinates(coordinates[0]) < Farm.WIDTH &&
        		convertCoordinates(coordinates[1]) >= 0 && convertCoordinates(coordinates[1]) < Farm.HEIGHT &&
        		convertCoordinates(coordinates[2]) >= 0 && convertCoordinates(coordinates[2]) < Farm.WIDTH &&
        		convertCoordinates(coordinates[3]) >= 0 && convertCoordinates(coordinates[3]) < Farm.HEIGHT )
        	{
        		Integer[] int_coords = new Integer[4];
        		for( i = 0; i < int_coords.length; i++ )
        		{
        			int_coords[i] = convertCoordinates(coordinates[i]);
        		}
        		farm.MarkBarrenLand(int_coords);
        	}
        	else
        	{
        		throw new Exception("Barren Land Coordinates are out of bounds\n");
        	}
        }
    }
        
    public static void ProcessInput( Farm farm ) throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter barren land coordinates in the format {\"x1 y1 x2 y2\", \"x3 y3 x4 y4\", etc} where x1 y1 is the lower left corner and x2 y2 is the upper right corner");
        String input = br.readLine();
        GetAllBarrenLand(input, farm);
    }
}
