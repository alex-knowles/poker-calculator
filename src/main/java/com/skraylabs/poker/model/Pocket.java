package com.skraylabs.poker.model;

/**
 * Pair of face-down cards held by each player in a game of Texas Hold 'Em poker.
 */
public class Pocket {
  /**
   * First of two pocket cards.
   */
  public Card card1;
  /**
   * Second of two pocket cards.
   */
  public Card card2;

  /**
   * Default constructor.
   */
  public Pocket() {
    this(null, null);
  }

  /**
   * Initializing constructor
   *
   * @param card1 first of two cards
   * @param card2 second of two cards
   */
  public Pocket(Card card1, Card card2) {
    this.card1 = card1;
    this.card2 = card2;
  }

  /**
   * Copy constructor.
   *
   * @param pocket non-null instance from which to copy attributes.
   */
  public Pocket(Pocket pocket) {
    // TODO: implement me!
  }
} // end of class Pocket