package com.skraylabs.poker.model;

/**
 * A playing card.
 */
class Card {
  /**
   * Playing card rank (A, K, Q, J, 10-2).
   */
  Rank rank;

  /**
   * Playing card suit (Spades, Hearts, Diamond, Clubs).
   */
  Suit suit;

  /**
   * Constructor.
   *
   * @param rank
   * @param suit
   */
  Card(Rank rank, Suit suit) {
    this.rank = rank;
    this.suit = suit;
  }

  /**
   * Accessor: Playing card rank (A, K, Q, J, 10-2).
   * @return the rank
   */
  public Rank getRank() {
    return rank;
  }

  /**
   * Accessor: Playing card suit (Spades, Hearts, Diamond, Clubs).
   * @return the suit
   */
  public Suit getSuit() {
    return suit;
  }
}
