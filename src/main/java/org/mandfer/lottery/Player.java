package org.mandfer.lottery;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.joda.time.DateTime;

import org.mandfer.lottery.exceptions.InvalidDateException;
import org.mandfer.lottery.exceptions.InvalidNumberException;

public final class Player {

  private final String name;
  private final DateTime finalDrawDate;
  private final List<Integer> numbers;

  private Player(String name, DateTime finalDate, List<Integer> numbers) {
    this.name = name;
    this.finalDrawDate = finalDate;
    this.numbers = numbers;
  }

  public String getName() {
    return name;
  }

  public DateTime getFinalDrawDate() {
    return finalDrawDate;
  }

  public List<Integer> getNumbers() {
    return numbers;
  }
  
  public static Player getInstance(String name, DateTime finalDrawDate,
    String numbers) throws InvalidNumberException, InvalidDateException {
    List<Integer> setNumbers = parseNumbers(numbers);
    return new Player(name, finalDrawDate, setNumbers);
  }

  private static List<Integer> parseNumbers(String numbers) {
    List<Integer> lstNumbers = new ArrayList<Integer>();
    for (String number : numbers.split(",")) {
      lstNumbers.add(Integer.valueOf(number));
    }
    return lstNumbers;
  }

}
