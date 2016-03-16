package com.skraylabs.poker.model;

/**
 * Ranks of playing cards.
 */
public enum Rank {
  Ace (14, 1),
  King (13),
  Queen (12),
  Jack (11),
  Ten (10),
  Nine (9),
  Eight (8),
  Seven (7),
  Six (6),
  Five (5),
  Four (4),
  Three (3),
  Two (2);

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
   * @return relative value with Aces low.
   */
  public int aceLowValue() {
    return this.aceLowValue;
  }
}
