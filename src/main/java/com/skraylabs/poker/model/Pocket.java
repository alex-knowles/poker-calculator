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
    // Assign card1
    if (pocket.card1 == null) {
      this.card1 = null;
    } else {
      this.card1 = new Card(pocket.card1);
    }
    // Assign card2
    if (pocket.card2 == null) {
      this.card2 = null;
    } else {
      this.card2 = new Card(pocket.card2);
    }
  }

  /**
   * Helper method to determine if two Card objects are equivalent. Both objects being null is
   * considered equal.
   *
   * @param card1
   * @param card2
   * @return true if the cards are equal (or both are null); false otherwise
   */
  static boolean cardsAreEqual(Card card1, Card card2) {
    boolean result = false;
    if (card1 == null && card2 == null) {
      result = true;
    } else if (card1 != null && card2 != null) {
      result = card1.equals(card2);
    }
    return result;
  }

  @Override
  public boolean equals(Object o) {
    boolean result = false;
    if (o instanceof Pocket) {
      Pocket thatPocket = (Pocket) o;
      boolean card1Equals = cardsAreEqual(this.card1, thatPocket.card1);
      boolean card2Equals = cardsAreEqual(this.card2, thatPocket.card2);
      result = card1Equals && card2Equals;
    }
    return result;
  }
} // end of class Pocket