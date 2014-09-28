package org.mandfer.lottery;

import static org.hamcrest.Matchers.containsString;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mandfer.lottery.exceptions.InvalidDateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author marcandreuf
 */
public class DateUtilsTest {

  private DateTime today;
  private DateTime monday;
  private DateTime tuesday;
  private DateTime wednesday;
  private DateTime thursday;
  private DateTime friday;
  private DateTime saturday;
  private DateTime sunday;
  private DateUtils dateUtils = new DateUtils();

  private final Logger logger = LoggerFactory.getLogger(DateUtilsTest.class);

  @Before
  public void setUp() {
    today = new DateTime();
    monday = today.dayOfWeek().setCopy(DateTimeConstants.MONDAY);
    tuesday = today.dayOfWeek().setCopy(DateTimeConstants.TUESDAY);
    wednesday = today.dayOfWeek().setCopy(DateTimeConstants.WEDNESDAY);
    thursday = today.dayOfWeek().setCopy(DateTimeConstants.THURSDAY);
    friday = today.dayOfWeek().setCopy(DateTimeConstants.FRIDAY);
    saturday = today.dayOfWeek().setCopy(DateTimeConstants.SATURDAY);
    sunday = today.dayOfWeek().setCopy(DateTimeConstants.SUNDAY);
  }

  @Test
  public void testPrintShortDate() {
    String shortDate = dateUtils.printShort(today);
    assertTrue(shortDate != null && !shortDate.isEmpty());
  }

  @Test
  public void testFindSameDayOfWeek() {
    printGivenDay(monday);
    DateTime nearestMonday = dateUtils.calcNearestDayOfWeek(monday, DateTimeConstants.MONDAY);
    printNearestMonday(nearestMonday);
    assertTrue(nearestMonday.isEqual(monday));
  }

  @Test
  public void testFindThePreviousNearestDayOfWeek() {
    testPreviousMonday(tuesday);
    testPreviousMonday(wednesday);
    testPreviousMonday(thursday);
    testIsNotPreviousMonday(friday);
    testIsNotPreviousMonday(saturday);
    testIsNotPreviousMonday(sunday);
  }

  private void testPreviousMonday(DateTime givenDate) {
    printGivenDay(givenDate);
    DateTime nearestMonday = dateUtils.calcNearestDayOfWeek(givenDate, DateTimeConstants.MONDAY);
    printNearestMonday(nearestMonday);
    assertTrue(nearestMonday.isBefore(givenDate));
  }

  private void printNearestMonday(DateTime nearestMonday) {
    logger.debug("Nearest Monday: " + dateUtils.printShort(nearestMonday));
  }

  private void printGivenDay(DateTime givenDate) {
    logger.debug("GivenDate: " + dateUtils.printShort(givenDate));
  }

  private void testIsNotPreviousMonday(DateTime givenDate) {
    printGivenDay(givenDate);
    DateTime nearestMonday = dateUtils.calcNearestDayOfWeek(givenDate, DateTimeConstants.MONDAY);
    printNearestMonday(nearestMonday);
    assertFalse(nearestMonday.isBefore(givenDate));
  } 
  
  @Test
  public void testFindTheFollowingNearestDayOfWeek() {
    testFollowingMonday(friday);
    testFollowingMonday(saturday);
    testFollowingMonday(sunday);
  }

  private void testFollowingMonday(DateTime givenDate) {
    printGivenDay(givenDate);
    DateTime nearestMonday = dateUtils.calcNearestDayOfWeek(givenDate, DateTimeConstants.MONDAY);
    printNearestMonday(nearestMonday);
    assertTrue(nearestMonday.isAfter(givenDate));
  }
  
  
  
  

  @Test
  public void testDistanceToForwardDayOfTheWeek() {

    int numDaysToTarget = dateUtils.calcDistanceToDayOfWeek(tuesday, DateTimeConstants.MONDAY, DateUtils.FORWARD);
    assertTrue(numDaysToTarget == 6);

    numDaysToTarget = dateUtils.calcDistanceToDayOfWeek(wednesday,
      DateTimeConstants.MONDAY, DateUtils.FORWARD);
    assertTrue(numDaysToTarget == 5);

    numDaysToTarget = dateUtils.calcDistanceToDayOfWeek(thursday,
      DateTimeConstants.MONDAY, DateUtils.FORWARD);
    assertTrue(numDaysToTarget == 4);

    numDaysToTarget = dateUtils.calcDistanceToDayOfWeek(friday,
      DateTimeConstants.MONDAY, DateUtils.FORWARD);
    assertTrue(numDaysToTarget == 3);

    numDaysToTarget = dateUtils.calcDistanceToDayOfWeek(saturday,
      DateTimeConstants.MONDAY, DateUtils.FORWARD);
    assertTrue(numDaysToTarget == 2);

    numDaysToTarget = dateUtils.calcDistanceToDayOfWeek(sunday,
      DateTimeConstants.MONDAY, DateUtils.FORWARD);
    assertTrue(numDaysToTarget == 1);

  }

  @Test
  public void testDistanceToPreviousDayOfTheWeek() {

    int numDaysToTarget = dateUtils.calcDistanceToDayOfWeek(tuesday,
      DateTimeConstants.MONDAY, DateUtils.BACKWARDS);
    assertTrue(numDaysToTarget == 1);

    numDaysToTarget = dateUtils.calcDistanceToDayOfWeek(wednesday,
      DateTimeConstants.MONDAY, DateUtils.BACKWARDS);
    //TODO: setup logger logger.debug("numDays: "+numDaysToTarget);
    assertTrue(numDaysToTarget == 2);

    numDaysToTarget = dateUtils.calcDistanceToDayOfWeek(thursday,
      DateTimeConstants.MONDAY, DateUtils.BACKWARDS);
    assertTrue(numDaysToTarget == 3);

    numDaysToTarget = dateUtils.calcDistanceToDayOfWeek(friday,
      DateTimeConstants.MONDAY, DateUtils.BACKWARDS);
    assertTrue(numDaysToTarget == 4);

    numDaysToTarget = dateUtils.calcDistanceToDayOfWeek(saturday,
      DateTimeConstants.MONDAY, DateUtils.BACKWARDS);
    assertTrue(numDaysToTarget == 5);

    numDaysToTarget = dateUtils.calcDistanceToDayOfWeek(sunday,
      DateTimeConstants.MONDAY, DateUtils.BACKWARDS);
    assertTrue(numDaysToTarget == 6);

  }
  
  
  
  
  
  @Test
  public void testCalcNextDrawDate(){
    testNextDrawOnDate(monday);
    testNextDrawOnDate(tuesday);
    testNextDrawOnDate(wednesday);
    testNextDrawOnDate(thursday);
    testNextDrawOnDate(friday);
    testNextDrawOnDate(saturday);
    testNextDrawOnDate(sunday);
  }  
  
  private void testNextDrawOnDate(DateTime date){
    DateTime nextDrawDay = dateUtils.calcNextDrawDate(date);
    assertTrue("Its Monday", nextDrawDay.getDayOfWeek()==DateUtils.DRAW_DAY_OF_WEEK);
    assertTrue("Its after given date.", nextDrawDay.isAfter(date));  
  }
  
  
  
  
  @Test
  public void testGetDateFromString(){
    DateTime date = dateUtils.parseDateString("29/9/2014");
    assertTrue(date.getDayOfMonth()==29);
    assertTrue(date.getMonthOfYear()==9);
    assertTrue(date.getYear()==2014);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testBadDateStringParsing(){
    dateUtils.parseDateString("wrongDate");
  }
  
  
  @Rule
  public ExpectedException exception = ExpectedException.none();
  
  @Test
  public void testValidateDateTimeIntoXMonthsTime() throws InvalidDateException{    
    DateTime sixMonthsLater = today.plusMonths(6);
    
    ExpectedException.none();
    dateUtils.isDateInTheGivenMonthsPeriodTime(today, sixMonthsLater, 6);    
  }
  
  
  @Test
  public void testFailureValidateDateTimeIntoXMonthsTime() throws InvalidDateException{    
    DateTime sixMonthsLater = today.plusMonths(6);
    
    exception.expect(InvalidDateException.class);
    exception.expectMessage( 
				containsString("Date is out of the required") );
    dateUtils.isDateInTheGivenMonthsPeriodTime(today, sixMonthsLater, 3);    
  }
  
  
  	@Test
	public void testValidDateFormat() throws InvalidDateException{
		ExpectedException.none();

		dateUtils.validateDateFormat("01/11/2013");
	}
	
	
	@Test
	public void testNonValidDateFormatYear() throws InvalidDateException{		
		exception.expect(InvalidDateException.class);
		exception.expectMessage( containsString("not a valid format") );
		
		dateUtils.validateDateFormat("01/11/13");
	}
	
	@Test
	public void testNonValidDateFormatMonth() throws InvalidDateException{		
		exception.expect(InvalidDateException.class);
		exception.expectMessage( containsString("not a valid format") );
		
		dateUtils.validateDateFormat("01/15/2013");
	}

	
	@Test
	public void testNonValidDateFormatDay() throws InvalidDateException{		
		exception.expect(InvalidDateException.class);
		exception.expectMessage( containsString("not a valid format") );
		
		dateUtils.validateDateFormat("50/11/2013");
	}

}
