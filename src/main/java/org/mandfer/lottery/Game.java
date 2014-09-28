package org.mandfer.lottery;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.console.ConsoleException;
import org.console.TextDevice;
import org.console.TextDevices;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.mandfer.lottery.exceptions.InvalidDateException;
import org.mandfer.lottery.exceptions.InvalidNumberException;

public class Game {

  private final TextDevice io;
  private final DateUtils dateUtils;
  private final Lotto lotto;

  private final DateTime today = new DateTime();
  private final List<Player> players = new ArrayList<Player>();

  private Game(TextDevice io, DateUtils dateUtils, Lotto lotto) {
    this.io = io;
    this.dateUtils = dateUtils;
    this.lotto = lotto;
  }

  public static void main(String[] args) {
    Game game = new Game(
      TextDevices.defaultTextDevice(),
      new DateUtils(),
      new Lotto()
    );
    game.run();
  }

  public void run() {
    gameHeader();
    //TODO: Add and play with more players in a loop.
    createNewPlayer();
    Player firstPlayer = players.get(0);
    play(firstPlayer);
  }

  private void play(Player player) {
    long price;
    DateTime drawDate = dateUtils.calcNextDrawDate(today);
    while (drawDate.isBefore(player.getFinalDrawDate())) {
      List<Integer> drawNumbers = lotto.draw();
      List<Integer> matches
        = lotto.calculateMatchings(drawNumbers, player.getNumbers());
      price = lotto.calculateDrawPrice(player, drawNumbers, matches);
      price = lotto.addSpecialPrice(price, drawDate);
      printSummary(price, drawDate, drawNumbers);
      drawDate = dateUtils.calcNextDrawDate(drawDate);
    }
  }

  private void gameHeader() {
    io.writer().println("-------- Welcome to awesome lotto game --------");
    io.writer().println("");
  }

  private void printSummary(long price, DateTime drawDate, List<Integer> drawNumbers) {
    io.writer().println(
      "Draw date:" + dateUtils.printShort(drawDate) + ": "
      + "Draw numbers: " + printNumbers(drawNumbers) + ": "
      + "Price: " + price);
  }

  private String printNumbers(List<Integer> numbers) {
    String strNums = "";    
    for(int number : numbers){
      strNums += number + ",";
    }
    return strNums.substring(0, strNums.length() - 2);
  }

  private void createNewPlayer() {
    BufferedReader in = new BufferedReader(io.reader());
    String name;
    DateTime finalDrawDate;
    String numbers;
    do {
      try {
        name = askPlayerName(in);
        finalDrawDate = askFinalDrawDate(in);
        numbers = askPlayerNumbers(in);
        addNewPlayer(name, finalDrawDate, numbers);
      } catch (Exception e) {
        io.writer().println("Error: " + e.getMessage());
      }
    } while (players.isEmpty());
  }

  private String askPlayerName(BufferedReader in) throws ConsoleException, IOException {
    io.writer().println("-------- New player --------");
    io.writer().println("");
    io.writer().println("Please enter your name: ");
    return in.readLine();
  }

  private DateTime askFinalDrawDate(BufferedReader in) throws IOException, InvalidDateException, ConsoleException {
    io.writer().println("Please enter your final date : (format like dd/MM/yyyy) ");
    String userEndDate = in.readLine();
    dateUtils.validateDateFormat(userEndDate);
    DateTime endDate = dateUtils.parseDateString(userEndDate);
    dateUtils.isDateInTheGivenMonthsPeriodTime(today, endDate, 6);
    return dateUtils.calcNearestDayOfWeek(endDate, DateTimeConstants.MONDAY);
  }

  private String askPlayerNumbers(BufferedReader in) throws IOException, ConsoleException, InvalidNumberException {
    String numbers;
    io.writer().println("Please enter your 6 numbers between 1 to 60. "
      + "i.e: 1,2,3,4,5,6 ");
    numbers = in.readLine();
    lotto.validateNumbers(numbers);
    return numbers;
  }

  private void addNewPlayer(String name, DateTime finalDrawDate, String numbers) throws InvalidDateException, ConsoleException, InvalidNumberException {
    Player newPlayer = Player.getInstance(name, finalDrawDate, numbers);
    players.add(newPlayer);
    io.writer().println("Welcome to play " + newPlayer.getName()
      + ", your final draw will be the " + dateUtils.printShort(newPlayer.getFinalDrawDate()));
  }

}
