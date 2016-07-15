package com.skraylabs.poker;

import com.skraylabs.poker.model.GameState;

/**
 * For a given {@link com.skraylabs.poker.model.GameState}, calculates the outcome odds for each
 * Player.
 */
class OddsCalculator {

  public OddsCalculator(GameState gameState) {}

  /**
   * Report the odds of a player getting a Two Of A Kind.
   *
   * @param playerIndex index of Player in the GameState.  A number in range [0, 9].
   * @return the probability of getting a Two Of A Kind.
   */
  public double twoOfAKindForPlayer(int playerIndex) {
    return 0;
  }
}
