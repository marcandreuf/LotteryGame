package org.mandfer.lottery;

import java.util.ArrayList;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.joda.time.DateTime;

import org.junit.Before;
import org.junit.Test;

public class LottoPriceTest {

  private final Lotto lotto = new Lotto();
  private List<Integer> setSampleDraw;
  private List<Integer> setPlayerSample;

  @Before
  public void setup() {
    Integer[] arSampleDraw = {1, 2, 3, 4, 5, 6};
    setSampleDraw = new ArrayList<Integer>(Arrays.asList(arSampleDraw));

    Integer[] arPlayerSample = {1, 2, 3, 7, 8, 9};
    setPlayerSample = new ArrayList<Integer>(Arrays.asList(arPlayerSample));
  }

  @Test
  public void testMatchingNumbers() {
    List<Integer> matches = 
      lotto.calculateMatchings(setSampleDraw, setPlayerSample);

    assertTrue("3 numbers matching", matches.size() == 3);
  }

  @Test
  public void testNonMatchingNumbers() {
    List<Integer> nonMatching = 
      lotto.getNonMatching(setSampleDraw, setPlayerSample);

    assertTrue("3 numbers non matching ", nonMatching.size() == 3);
  }

  @Test
  public void testFirstPrice() {
    long firstPrice = lotto.getFirstPrice(setSampleDraw);

    assertTrue("First prices ", firstPrice == 21);
  }

  @Test
  public void testSecondPrice() {
    Integer[] arMatches = {1, 2, 3};
    List<Integer> matches = new ArrayList<Integer>(Arrays.asList(arMatches));

    Integer[] arNonMatches = {7, 8, 9};
    List<Integer> diff = new ArrayList<Integer>(Arrays.asList(arNonMatches));

    long secondPrice = lotto.getSecondPrice(matches, diff);

    assertTrue("Second price ", secondPrice == 6504);
  }

  @Test
  public void testThirdPrice() {
    long thirdPrice = lotto.getThirdPrice(setSampleDraw);

    assertTrue("Third price ", thirdPrice == 210000);
  }

  @Test
  public void testSpecialPriceFebruaryLeapYear() {
    long currentPrice = 10;
    DateTime firstOfFebLeapYear = DateTime.parse("2012-2-1");

    long specialPrice
      = lotto.addSpecialPrice(currentPrice, firstOfFebLeapYear);

    assertTrue("Special price on leap year February draws",
      specialPrice == (currentPrice) * 2);
  }

  @Test
  public void testSpecialPrice29Feb() {
    long currentPrice = 10;
    DateTime lastDayofFebLeapYear
      = DateTime.parse("2012-2-1").withDayOfMonth(29);

    long specialPrice
      = lotto.addSpecialPrice(currentPrice, lastDayofFebLeapYear);

    assertTrue("Special price on 29th leap year",
      specialPrice == (currentPrice) * 3);
  }
}
