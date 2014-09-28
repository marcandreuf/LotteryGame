package org.mandfer.lottery;

import java.util.Arrays;
import java.util.List;
import org.joda.time.DateTime;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mandfer.lottery.exceptions.InvalidDateException;
import org.mandfer.lottery.exceptions.InvalidNumberException;


@RunWith(JUnit4.class)
public class PlayerTest {

	@Rule
	 public ExpectedException exception = ExpectedException.none();	
	
	@Test
	public void testCreateNewPlayer() 
			throws InvalidNumberException, InvalidDateException{
		ExpectedException.none();
        
        Integer[] numbers = {1,2,3,4,5,6};        
        List lstNumbers = Arrays.asList(numbers);
		
		DateTime playerEndDate = DateTime.now();
		
		Player player = Player.getInstance("Marc", 
          playerEndDate, "1,2,3,4,5,6");
        
        assertTrue(player.getName().equals("Marc"));
        assertTrue(player.getNumbers().containsAll(lstNumbers));
        assertTrue(player.getFinalDrawDate().isEqual(playerEndDate));
	}	
	
}