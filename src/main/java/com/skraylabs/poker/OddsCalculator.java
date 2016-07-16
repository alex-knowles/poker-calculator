package com.skraylabs.poker;

import com.skraylabs.poker.model.Card;
import com.skraylabs.poker.model.CardUtils;
import com.skraylabs.poker.model.GameState;
import com.skraylabs.poker.model.Pocket;
import com.skraylabs.poker.model.Rank;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * For a given {@link com.skraylabs.poker.model.GameState}, calculates the outcome odds for each
 * Player.
 */
class OddsCalculator {

  private GameState gameState;

  public OddsCalculator(GameState gameState) {
    this.gameState = gameState;
  }

  /**
   * Report the odds of a player getting a Two Of A Kind.
   *
   * @param playerIndex index of Player in the GameState. A number in range [0, 9].
   * @return the probability of getting a Two Of A Kind.
   */
  public double twoOfAKindForPlayer(int playerIndex) {
    double result = 0.0;
    Pocket[] pockets = gameState.getPockets();
    Pocket pocket = pockets[playerIndex];
    Collection<Card> cards = CardUtils.collectCards(gameState.getBoard());
    cards.addAll(CardUtils.collectCards(pocket));
    Map<Rank, Long> countByRank =
        cards.stream().collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));
    for (Long count : countByRank.values()) {
      if (count >= 2) {
        result = 1.0;
        break;
      }
    }
    return result;
  }
}
