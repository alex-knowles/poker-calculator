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
    // TODO: implement me!
    boolean result = false;
    return result;
  }
} // end of class Board