Lottery Test
============

A big lottery company is launching a new and exciting game.  The rules are simple; players will need to guess six numbers out of sixty ranging from 1 to 60.

The draw occurs every Monday (including bank holiday Mondays) and players who wish to register need to commit to six monthsâ€™ worth of draws and the same six numbers of their choosing throughout all draws.

As part of their registration to the game, the players will be required to specify a date marking the end of the six months (the lottery company will select the nearest Monday) and a set of six numbers.

For registered players the lottery will randomly select six numbers every Monday and calculate the winners.  This generous lottery provider had decided that in this game everyoneâ€™s a winner and applied the following winning scheme:
*	If the player guessed less than three numbers in the draw, they will win the sum of the numbers drawn for that Monday.
*	If they guessed three or more numbers they will win Â£1,000 pounds for every number they guessed plus the multiplication of the numbers they missed.
*	If they guessed all six numbers they will win the sum of Â£10,000 multiplied by every number drawn (for numbers 1,2,3,4,5,6 the winning prize is Â£210,000)

There are a few special cases, though. During the month of February on a leap year, all winnings are doubled and if a draw falls on Monday the 29th of February they are tripled.

You are entrusted with writing the software which will manage this new game. The input for your software is the date of the playerâ€™s choosing (dd/mm/yyyy) marking the last date the player wishes to play and a series of six numbers. Your software will then play all draws for that player and output a list of the following structure:

```
<draw date>; <draw numbers (separated by commas)>; <winning prize>
```


