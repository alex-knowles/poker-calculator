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

  @Override
  public boolean equals(Object o) {
    boolean result = false;
    if (o instanceof Pocket) {
      Pocket thatPocket = (Pocket) o;
      // Compare card1 values
      boolean card1Equals = false;
      if (this.card1 != null) {
        card1Equals = this.card1.equals(thatPocket.card1);
      } else {
        // this.card1 is null
        if (thatPocket.card1 == null) {
          card1Equals = true;
        } else {
          card1Equals = false;
        }
      }
      // Compare card2 values
      boolean card2Equals = false;
      if (this.card2 != null) {
        card2Equals = this.card2.equals(thatPocket.card2);
      } else {
        // this.card2 is null
        if (thatPocket.card2 == null) {
          card2Equals = true;
        } else {
          card2Equals = false;
        }
      }
      result = card1Equals && card2Equals;
    }
    return result;
  }
} // end of class Pocket