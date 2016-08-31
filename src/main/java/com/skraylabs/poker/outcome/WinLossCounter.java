package com.skraylabs.poker.outcome;

/**
 * A binary counter that counts "wins" and "losses". For example, if you wanted to count the number
 * of heads-up outcomes for 100 coin flips. If you counted 46 heads, and 54 tails, there would be 46
 * "wins" and 54 "losses".
 */
class WinLossCounter {
  private int wins;
  private int losses;

  public WinLossCounter() {
    this.wins = 0;
    this.losses = 0;
  }

  public int getWins() {
    return this.wins;
  }

  public int getLosses() {
    return this.losses;
  }

  public int getCountTotal() {
    return this.wins + this.losses;
  }

  public double getWinPercentage() {
    return ((double) this.wins) / getCountTotal();
  }

  public double getLossPercentage() {
    return ((double) this.losses) / getCountTotal();
  }

  public void incrementWinsBy(int wins) {
    if (wins <= 0) {
      throw new IllegalArgumentException("Argument must be a positive integer.");
    }
    this.wins += wins;
  }

  public void incrementLossesBy(int losses) {
    if (losses <= 0) {
      throw new IllegalArgumentException("Argument must be a positive integer.");
    }
    this.losses += losses;
  }

  public void incrementBy(WinLossCounter other) {
    this.wins += other.wins;
    this.losses += other.losses;
  }
}
