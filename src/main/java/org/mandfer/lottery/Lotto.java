package org.mandfer.lottery;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.mandfer.lottery.exceptions.InvalidDateException;
import org.mandfer.lottery.exceptions.InvalidNumberException;

public class Lotto {

  public static final int UPPER_NUMBERS_BOUND = 60;
  public static final int LOWER_NUMBERS_BOUND = 1;
  public static final int GAME_PERIOD = 6;
  public static final int REQUIRED_NUMBERS = 6;
  public static final int DRAW_LENGHT = 6;
  public static final int Min = 1;
  public static final int Max = 60;

  private static final int SECOND_PRICE_BASE = 1000;
  private static final int THIRD_PRICE_BASE = 10000;

  
  public void validateNumbers(String numbers) throws InvalidNumberException {
    Integer intValue;
    String[] arStrNumbers = numbers.split(",");

    if (arStrNumbers.length < REQUIRED_NUMBERS
      || arStrNumbers.length > REQUIRED_NUMBERS) {
      throw new InvalidNumberException(REQUIRED_NUMBERS
        + " numbers are required.");
    }

    for (String number : arStrNumbers) {
      number = number.trim();
      if (!StringUtils.isNumeric(number)) {
        throw new InvalidNumberException(number
          + "Is not a numeric value.");
      } else {
        intValue = Integer.valueOf(number);
        if (intValue < LOWER_NUMBERS_BOUND
          || intValue > UPPER_NUMBERS_BOUND) {
          throw new InvalidNumberException(number
            + "Is not a number between 1 to 60.");
        }
      }
    }
  }

  public void validateEndDateGamePeriod(DateTime startDate, DateTime endDate)
    throws ParseException, InvalidDateException {

    DateTime proposedEndDate = startDate.plusMonths(GAME_PERIOD - 1);

    if (endDate.getMonthOfYear() != proposedEndDate.getMonthOfYear()) {
      throw new InvalidDateException(
        "Date is out of the required " + REQUIRED_NUMBERS + " months period");
    }
  }

   
    
  
  public List<Integer> draw() {
    List<Integer> newNumbers = new ArrayList<Integer>();

    do {
      newNumbers.add(getNextValueInBounds());
    } while (newNumbers.size() < DRAW_LENGHT);

    return newNumbers;
  }

  private int getNextValueInBounds() {
    return ThreadLocalRandom.current().nextInt(Min, Max);
  }

  
  public List<Integer> calculateMatchings(
        List<Integer> drawNumbers, List<Integer> playerNumbers) {
    List<Integer> matchingNumbers = new ArrayList<Integer>(playerNumbers);
    matchingNumbers.retainAll(drawNumbers);
    return matchingNumbers;
  }

  public List<Integer> getNonMatching(
       List<Integer> drawNumbers, List<Integer> playerNumbers) {
    List<Integer> nonMatchingNumbers = new ArrayList<Integer>(playerNumbers);
    nonMatchingNumbers.removeAll(calculateMatchings(drawNumbers, playerNumbers));
    return nonMatchingNumbers;
  }
  
   
  
  
  public long getFirstPrice(List<Integer> drawNumbers) {    
    long price = 0;
    for(int number : drawNumbers){
      price += number;
    }
    return price;
  }

  public long getSecondPrice(List<Integer> matches, List<Integer> diff) {
    long matchingNumbersPrice = 0;
    long diffNubersProduct = 1;

    for(int number : matches){
      matchingNumbersPrice += SECOND_PRICE_BASE * number;
    }

    for(int number : diff){
      diffNubersProduct *= number;
    }

    return matchingNumbersPrice + diffNubersProduct;
  }

  public long getThirdPrice(List<Integer> drawNumbers) {
    long price = 0;
    for(int number : drawNumbers){
      price += THIRD_PRICE_BASE * number;
    }
    return price;
  }

  public long addSpecialPrice(long currentPrice, DateTime drawDate) {

    if (drawDate.year().isLeap()
      && drawDate.getMonthOfYear() == DateTimeConstants.FEBRUARY) {
      if (drawDate.getDayOfMonth() == 29) {
        return currentPrice * 3;
      } else {
        return currentPrice * 2;
      }
    } else {
      return currentPrice;
    }
  }
  
  
  public long calculateDrawPrice(Player playerOne, List<Integer> drawNumbers,
       List<Integer> matches) {
    long price = 0;
    switch (matches.size()) {
      case 0:
      case 1:
      case 2:
      case 3:
        price = getFirstPrice(drawNumbers);
        break;
      case 4:
      case 5:
        List<Integer> diffNumbers = getNonMatching(drawNumbers, playerOne.getNumbers());
        price = getSecondPrice(matches, diffNumbers);
        break;
      case 6:
        price = getThirdPrice(drawNumbers);
        break;
    }
    return price;
  }

}
