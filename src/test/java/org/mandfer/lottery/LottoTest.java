package org.mandfer.lottery;

import static org.hamcrest.Matchers.containsString;

import java.text.ParseException;
import java.util.Iterator;
import java.util.Set;
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
public class LottoTest {
  
  private Lotto lotto = new Lotto();

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void testValidationNumbersOk() throws InvalidNumberException {
    ExpectedException.none();

    lotto.validateNumbers("34,56,42,31,21,23");
  }

  @Test
  public void testValidtionNumbersErrorWithChars() throws InvalidNumberException {
    exception.expect(InvalidNumberException.class);
    exception.expectMessage(containsString("aa"));

    lotto.validateNumbers("1,2,3,4,aa,6");
  }

  @Test
  public void testValidtionNumbersErrorOutOfRangeMaxValue() throws InvalidNumberException {
    exception.expect(InvalidNumberException.class);
    exception.expectMessage(containsString("70"));

    lotto.validateNumbers("1,2,3,4,70,6");
  }

  @Test
  public void testValidtionNumbersErrorOutOfRangeMinValue() throws InvalidNumberException {
    exception.expect(InvalidNumberException.class);
    exception.expectMessage(containsString("-1"));

    lotto.validateNumbers("-1,2,3,4,7,6");
  }

  @Test
  public void testInsuficientNumbers() throws InvalidNumberException {
    exception.expect(InvalidNumberException.class);
    exception.expectMessage(
      containsString(Lotto.REQUIRED_NUMBERS
        + " numbers are required"));

    lotto.validateNumbers("1,2,3");
  }

  @Test
  public void testTooManyNumbers() throws InvalidNumberException {
    exception.expect(InvalidNumberException.class);
    exception.expectMessage(
      containsString(Lotto.REQUIRED_NUMBERS
        + " numbers are required"));

    lotto.validateNumbers("1,2,3,4,6,8,2,4,6,3,2");
  }

  @Test
  public void testDateRequiredPeriod()
    throws InvalidDateException, ParseException {

    DateTime sampleEndDate = DateTime.parse("2013-6-1");
    DateTime referenceDate = getSampleStartDate();

    ExpectedException.none();

    lotto.validateEndDateGamePeriod(referenceDate, sampleEndDate);
  }

  @Test
  public void testFailEndDateValidationgBeforePeriod()
    throws InvalidDateException, ParseException {
    testEndDateOnMonth(5);
    testEndDateOnMonth(4);
    testEndDateOnMonth(3);
  }

  @Test
  public void testFailEndDateValidationAfterPeriod()
    throws InvalidDateException, ParseException {
    testEndDateOnMonth(7);
    testEndDateOnMonth(8);
    testEndDateOnMonth(9);
  }

  private void testEndDateOnMonth(int month)
    throws ParseException, InvalidDateException {
    DateTime endDate = getTestEndDate(month);
    DateTime startSampleDate = getSampleStartDate();

    exception.expect(InvalidDateException.class);
    exception.expectMessage(
      containsString("Date is out of the required"));

    lotto.validateEndDateGamePeriod(startSampleDate, endDate);
  }

  private DateTime getTestEndDate(int month) {
    DateTime date = DateTime.parse("2013-1-1");
    return date.withMonthOfYear(month);
  }

  private DateTime getSampleStartDate() {
    return DateTime.parse("2013-1-1");
  }
  
  
  //TODO implement test for calculateDrawPrice(...)



}
