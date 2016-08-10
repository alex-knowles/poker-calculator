Poker Calculator
----------------

This is a simple program that calculates probable outcomes for a
hand of Texas Hold 'Em poker.

The program can accept information regarding the community cards
at various stages of the hand (pre-flop, flop, turn, and river).
It can also accept info about each player's (up to 10) hole cards.

The program will then calculate the odds of each outcome for each
player.

File input format uses 2-character abbreviations to denote each card.

Suits:

* s: Spades
* h: Hearts
* d: Diamonds
* c: Clubs

Ranks:

* A: Ace
* K: King
* Q: Queen
* J: Jack
* T: Ten
* 9
* 8
* 7
* 6
* 5
* 4
* 3
* 2

The first line of the file describes the community cards.
An empty first line equates to pre-flop. Successive lines
describe a player's hole cards.

Example:


```
5d 8c Ah Qh
7h 2c
Ac Kd
5s 5c
```

This is the "turn" phase with community cards:

* 5 of Diamonds
* 8 of Clubs
* Ace of Hearts
* Queen of Hearts

Player 1's hole cards:

* 7 of Hearts
* 2 of Clubs

Player 2's hole cards:

* Ace of Clubs
* King of Diamonds

Player 3's hole cards:

* 5 of Spades
* 5 of Clubs

Upon completion, the program will print out the various
outcomes and their probabilities for each player.

Build instructions
-----
See [BUILDING.md](BUILDING.md).
