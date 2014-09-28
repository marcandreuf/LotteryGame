package org.mandfer.lottery;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import static org.mandfer.lottery.Lotto.REQUIRED_NUMBERS;
import org.mandfer.lottery.exceptions.InvalidDateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author marc
 */
public class DateUtils {

  public static final int DRAW_DAY_OF_WEEK = DateTimeConstants.MONDAY;
  public static final int BACKWARDS = -1;
  public static final int FORWARD = 1;

  private final Logger logger = LoggerFactory.getLogger(DateUtils.class);
  private final DateTimeFormatter formatter
    = DateTimeFormat.forPattern("dd/MM/yyyy");

  public DateTime calcNearestDayOfWeek(DateTime givenDayOfWeek,
    int targetDayOfWeek) {
    int distanceToBackwardsTarget
      = calcDistanceToDayOfWeek(givenDayOfWeek, targetDayOfWeek, BACKWARDS);
    int distanceToForwardTarget
      = calcDistanceToDayOfWeek(givenDayOfWeek, targetDayOfWeek, FORWARD);

    if (Math.abs(distanceToForwardTarget) > Math.abs(distanceToBackwardsTarget)) {
      return givenDayOfWeek.minusDays(distanceToBackwardsTarget);
    } else {
      return givenDayOfWeek.plusDays(distanceToForwardTarget);
    }
  }

  public int calcDistanceToDayOfWeek(DateTime iniDayOfWeek,
    int targetDayOfWeek, int directionOffset) {

    if (iniDayOfWeek.getDayOfWeek() == targetDayOfWeek) {
      return 0;
    } else {
      return Math.abs(directionOffset) + calcDistanceToDayOfWeek(
        iniDayOfWeek.plusDays(directionOffset),
        targetDayOfWeek, directionOffset);
    }
  }

  public String printShort(DateTime date) {
    return formatter.print(date);
  }

  public DateTime calcNextDrawDate(DateTime date) {
    if (date.getDayOfWeek() == DRAW_DAY_OF_WEEK) {
      return date.plusWeeks(1);
    } else {
      int distanceToNextDrawDayOfWeek
        = calcDistanceToDayOfWeek(date, DRAW_DAY_OF_WEEK, FORWARD);
      return date.plusDays(distanceToNextDrawDayOfWeek);
    }
  }

  public DateTime parseDateString(String dateString) {
    return formatter.parseDateTime(dateString);
  }

  public void isDateInTheGivenMonthsPeriodTime(DateTime date,
    DateTime targetDate, int months) throws InvalidDateException {

    if (targetDate.getMonthOfYear()
      != date.plusMonths(months).getMonthOfYear()) {
      throw new InvalidDateException(
        "Date is out of the required "
        + REQUIRED_NUMBERS + " months period");
    }

  }

  public void validateDateFormat(String endDate) throws InvalidDateException {
    if (!endDate.matches("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)")) {
      throw new InvalidDateException("The date " + endDate + " has not a valid format (dd/mm/yyyy)");
    }
  }

}
