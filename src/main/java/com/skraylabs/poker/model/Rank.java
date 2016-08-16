package com.skraylabs.poker.model;

/**
 * Ranks of playing cards.
 */
public enum Rank {
  ACE (14, 1),
  KING (13),
  QUEEN (12),
  JACK (11),
  TEN (10),
  NINE (9),
  EIGHT (8),
  SEVEN (7),
  SIX (6),
  FIVE (5),
  FOUR (4),
  THREE (3),
  TWO (2);

  private final int aceHighValue;
  private final int aceLowValue;

  Rank(int aceHighValue, int aceLowValue) {
    this.aceHighValue = aceHighValue;
    this.aceLowValue = aceLowValue;
  }

  Rank(int aceHighValue) {
    this(aceHighValue, aceHighValue);
  }

  /**
   * Get relative rank value.
   *
   * <p>
   * Aces high: Ace is a "14".
   *
   * @return relative value with Aces high.
   */
  public int aceHighValue() {
    return this.aceHighValue;
  }

  /**
   * Get relative rank value.
   *
   * <p>
   * Aces low: Ace is a "1".
   *
   * @return relative value with Aces low.
   */
  public int aceLowValue() {
    return this.aceLowValue;
  }
}
