package org.mandfer.lottery;

import java.util.Iterator;
import java.util.List;
import static org.junit.Assert.*;


import org.junit.Test;

public class LottoDrawTest {

  private final Lotto lotto = new Lotto();

  @Test
  public void testDrawHasExpectedLenght() {
    List<Integer> newNumbers = lotto.draw();

    assertTrue("Set has expected lenght",
      newNumbers.size() == Lotto.DRAW_LENGHT);
  }

  @Test
  public void testNumbersAreBounded() {
    List<Integer> newNumbers = lotto.draw();
    int number;

    Iterator<Integer> itNumbers = newNumbers.iterator();

    while (itNumbers.hasNext()) {
      number = itNumbers.next();
      assertTrue("Set number "+number+" is bounded",
        number >= Lotto.Min && number < Lotto.Max);
    }
  }

}
