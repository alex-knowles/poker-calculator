package com.skraylabs.poker;

import com.skraylabs.poker.model.Board;
import com.skraylabs.poker.model.Card;
import com.skraylabs.poker.model.CardUtils;
import com.skraylabs.poker.model.GameState;
import com.skraylabs.poker.model.Pocket;
import com.skraylabs.poker.model.Rank;

import java.util.ArrayList;
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
    Board board = gameState.getBoard();
    Collection<Card> cards = CardUtils.collectCards(board);
    cards.addAll(CardUtils.collectCards(pocket));
    Map<Rank, Long> countByRank =
        cards.stream().collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));
    for (Long count : countByRank.values()) {
      if (count >= 2) {
        result = 1.0;
        break;
      }
    }
    if (result < 1.0) {
      int numberOfCardsToBeDealt = 5 - board.size();
      double numberOfOuts = numberOfCardsToBeDealt * cards.size() * 3;
      double numberOfRemainingCards = 52 - cards.size();
      result = numberOfOuts / numberOfRemainingCards;
    }
    return result;
  }

  /**
   * Helper method that determines if a Two of a Kind exists on a given combination of board and
   * pocket cards.
   *
   * @param board cards collected from a {@link Board}
   * @param pocket cards collected from a {@link Pocket}
   * @return {@code true} if there is at least one Two of a Kind; {@code false} otherwise
   */
  static boolean hasTwoOfAKind(Collection<Card> board, Collection<Card> pocket) {
    boolean result = false;
    Collection<Card> cards = new ArrayList<>(board);
    cards.addAll(pocket);
    Map<Rank, Long> countByRank =
        cards.stream().collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));
    for (Long count : countByRank.values()) {
      if (count >= 2) {
        result = true;
        break;
      }
    }
    return result;
  }
}
