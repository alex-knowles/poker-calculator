package com.skraylabs.poker.model;

/**
 * The "board" is the set of 5 community cards shared by all players in a game Texas Hold 'Em
 * Poker.
 */
public class Board {
  /**
   * First of three cards dealt in the second round (the "flop").
   */
  public Card flopCard1;
  /**
   * Second of three cards dealt in the second round (the "flop").
   */
  public Card flopCard2;
  /**
   * Third of three cards dealt in the second round (the "flop").
   */
  public Card flopCard3;
  /**
   * Card dealt in the third round (the "turn").
   */
  public Card turnCard;
  /**
   * Card dealt in the fourth and final round (the "river").
   */
  public Card riverCard;

  /**
   * Default constructor.
   */
  public Board() {
    this(null, null, null);
  }

  /**
   * Initializing constructor.
   *
   * @param flopCard1
   * @param flopCard2
   * @param flopCard3
   */
  public Board(Card flopCard1, Card flopCard2, Card flopCard3) {
    this(flopCard1, flopCard2, flopCard3, null);
  }

  /**
   * Initializing constructor.
   *
   * @param flopCard1
   * @param flopCard2
   * @param flopCard3
   * @param turnCard
   */
  public Board(Card flopCard1, Card flopCard2, Card flopCard3, Card turnCard) {
    this(flopCard1, flopCard2, flopCard3, turnCard, null);
  }

  /**
   * Initializing constructor.
   *
   * @param flopCard1
   * @param flopCard2
   * @param flopCard3
   * @param turnCard
   * @param riverCard
   */
  public Board(Card flopCard1, Card flopCard2, Card flopCard3, Card turnCard, Card riverCard) {
    this.flopCard1 = flopCard1;
    this.flopCard2 = flopCard2;
    this.flopCard3 = flopCard3;
    this.turnCard = turnCard;
    this.riverCard = riverCard;
  }

  @Override
  public boolean equals(Object o) {
    boolean result = false;
    if (o instanceof Board) {
      Board thatBoard = (Board) o;
      result = true;
      for (int i = 0 ; i < 5; ++i) {
        Card thisCard = null;
        Card thatCard = null;
        switch (i) {
          case 0:
            thisCard = this.flopCard1;
            thatCard = thatBoard.flopCard1;
            break;
          case 1:
            thisCard = this.flopCard2;
            thatCard = thatBoard.flopCard2;
            break;
          case 2:
            thisCard = this.flopCard3;
            thatCard = thatBoard.flopCard3;
            break;
          case 3:
            thisCard = this.turnCard;
            thatCard = thatBoard.turnCard;
            break;
          case 4:
            thisCard = this.riverCard;
            thatCard = thatBoard.riverCard;
            break;
        }
        if (thisCard != null) {
          if (thisCard.equals(thatCard)) {
            continue;
          } else {
            result = false;
            break;
          }
        } else if (thatCard == null) {
          continue;
        } else {
          result = false;
          continue;
        }
      }
    }
    return result;
  }
} // end of class Board