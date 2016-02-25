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

  /**
   * Helper method to retrieve a member Card based on a number 0 through 4.
   *
   * <p>
   * This is useful for arbitrarily comparing Card values between 2 different instances of the Board
   * class. For example, in {@link Board#equals(Object)}.
   *
   * @param board instance from which to retrieve a Card member variable.
   * @param n an integer in the range [0, 4]. A value of 0 represents the first flop card and a
   *        value of 4 represents the river card.
   * @return the "nth" {@link Card} instance from {@code board}
   */
  static protected Card getNthCard(Board board, int n) {
    Card result = null;
    if (board == null) {
      throw new IllegalArgumentException("Argument 'board' must be non-null");
    }
    switch (n) {
      case 0:
        result = board.flopCard1;
        break;
      case 1:
        result = board.flopCard2;
        break;
      case 2:
        result = board.flopCard3;
        break;
      case 3:
        result = board.turnCard;
        break;
      case 4:
        result = board.riverCard;
        break;
      default:
        throw new IllegalArgumentException("Argument 'n' must be in range [0, 4]");
    }
    return result;
  }

  @Override
  public boolean equals(Object o) {
    boolean result = false;
    if (o instanceof Board) {
      Board thatBoard = (Board) o;
      result = true;
      for (int i = 0 ; i < 5; ++i) {
        Card thisCard = Board.getNthCard(this, i);
        Card thatCard = Board.getNthCard(thatBoard, i);
        if (thisCard == null && thatCard == null) {
          continue;
        } else {
          if (thisCard != null && thatCard != null) {
            if (thisCard.equals(thatCard)) {
              continue;
            } else {
              result = false;
              break;
            }
          } else {
            // One card must be null and the other card must be non-null
            result = false;
            break;
          }
        }
      }
    }
    return result;
  }
} // end of class Board