package main;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UnitTest 
{
	private final static ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final static ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final static PrintStream originalOut = System.out;
	private final static PrintStream originalErr = System.err;
	
	@BeforeEach
	public void setUpStreams() 
	{
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	@AfterEach
	public void restoreStreams() 
	{
	    System.setOut(originalOut);
	    System.setErr(originalErr);
	    outContent.reset();
	    errContent.reset();
	}
	
	/**
	 * Input processing tests
	 */

	@Test
	void EmptyInputTest() 
	{
		Farm test_farm = new Farm();
		String test_input = new String("");
		try 
		{
			Reader.GetAllBarrenLand(test_input, test_farm);
		}
		catch (Exception e)
		{
			assertEquals(e.getMessage(), "Empty input given\n");
		}
	}
	
	@Test
	void OutOfBoundsInputTest()
	{
		Farm test_farm = new Farm();
		String test_input = new String("{0 0 999 501}");
		try 
		{
			Reader.GetAllBarrenLand(test_input, test_farm);
		}
		catch (Exception e)
		{
			assertEquals(e.getMessage(), "Barren Land Coordinates are out of bounds\n");
		}
	}

	@Test
	void IncompleteInputTest()
	{
		Farm test_farm = new Farm();
		String test_input = new String("{0 0 5");
		try 
		{
			Reader.GetAllBarrenLand(test_input, test_farm);
		}
		catch (Exception e)
		{
			assertEquals(e.getMessage(), "Barren Land Coordinates not given in correct format\n");
		}
	}
	
	@Test
	void NonIntegerInputTest()
	{
		Farm test_farm = new Farm();
		String test_input = new String("{this isn't an integer}");
		try 
		{
			Reader.GetAllBarrenLand(test_input, test_farm);
		}
		catch (Exception e)
		{
			assertEquals(e.getMessage(), "Coordinates must be integers\n");
		}
	}
	
	@Test
	void ReversedCoordinatesTest()
	{
		Farm test_farm = new Farm();
		String test_input = new String("{\"399 307 0 292\"}");
		try 
		{
			Reader.GetAllBarrenLand(test_input, test_farm);
			BarrenLandAnalysis.PrintFertileLand(test_farm);
			assertEquals("116800 116800 ", outContent.toString());
		}
		catch (Exception e)
		{
			fail("Exception thrown: "+ e.getMessage());
		}
	}
	
	/**
	 * Output tests
	 */
	
	@Test
	void SingleBarrenAreaTest()
	{
		Farm test_farm = new Farm();
		String test_input = new String("{\"0 292 399 307\"}");
		try 
		{
			Reader.GetAllBarrenLand(test_input, test_farm);
			BarrenLandAnalysis.PrintFertileLand(test_farm);
			assertEquals("116800 116800 ", outContent.toString());
		}
		catch (Exception e)
		{
			fail("Exception thrown: "+ e.getMessage());
		}
	}
	
	@Test
	void MultipleBarrenAreaTest()
	{
		Farm test_farm = new Farm();
		String test_input = new String("{“48 192 351 207”, “48 392 351 407”, “120 52 135 547”, “260 52 275 547”}");
		try 
		{
			Reader.GetAllBarrenLand(test_input, test_farm);
			BarrenLandAnalysis.PrintFertileLand(test_farm);
			assertEquals("22816 192608 ", outContent.toString());
		}
		catch (Exception e)
		{
			fail("Exception thrown: "+ e.getMessage());
		}
	}
	
	@Test
	void NoFertileAreaTest()
	{
		Farm test_farm = new Farm();
		String test_input = new String("{\"0 0 399 599\"}");
		try 
		{
			Reader.GetAllBarrenLand(test_input, test_farm);
			BarrenLandAnalysis.PrintFertileLand(test_farm);
			assertEquals("No fertile land found", outContent.toString());
		}
		catch (Exception e)
		{
			fail("Exception thrown: "+ e.getMessage());
		}
	}
	
	@Test
	void SingleCellBarrenAreaTest()
	{
		Farm test_farm = new Farm();
		String test_input = new String("{\"0 0 0 0\"}");
		try 
		{
			Reader.GetAllBarrenLand(test_input, test_farm);
			BarrenLandAnalysis.PrintFertileLand(test_farm);
			assertEquals("239999 ", outContent.toString());
		}
		catch (Exception e)
		{
			fail("Exception thrown: "+ e.getMessage());
		}
	}
	
	@Test
	void EqualFertileAreaTest()
	{
		Farm test_farm = new Farm();
		String test_input = new String("{\"0 99 399 199\", \"0 299 399 399\", \"0 499 399 599\"}");
		try 
		{
			Reader.GetAllBarrenLand(test_input, test_farm);
			BarrenLandAnalysis.PrintFertileLand(test_farm);
			assertEquals("39600 39600 39600 ", outContent.toString());
		}
		catch (Exception e)
		{
			fail("Exception thrown: "+ e.getMessage());
		}
	}
	
	@Test
	void UnequalFertileAreaTest()
	{
		Farm test_farm = new Farm();
		String test_input = new String("{\"0 45 399 100\", \"0 250 399 500\"}");
		try 
		{
			Reader.GetAllBarrenLand(test_input, test_farm);
			BarrenLandAnalysis.PrintFertileLand(test_farm);
			assertEquals("18000 39600 59600 ", outContent.toString());
		}
		catch (Exception e)
		{
			fail("Exception thrown: "+ e.getMessage());
		}
	}
	
	@Test
	void SingleLineFertileAreaTest()
	{
		Farm test_farm = new Farm();
		String test_input = new String("{\"1 0 399 599\"}");
		try 
		{
			Reader.GetAllBarrenLand(test_input, test_farm);
			BarrenLandAnalysis.PrintFertileLand(test_farm);
			assertEquals("600 ", outContent.toString());
		}
		catch (Exception e)
		{
			fail("Exception thrown: "+ e.getMessage());
		}
	}
}
