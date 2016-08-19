package com.skraylabs.poker;

import com.skraylabs.poker.model.Board;
import com.skraylabs.poker.model.Card;
import com.skraylabs.poker.model.CardUtils;
import com.skraylabs.poker.model.GameState;
import com.skraylabs.poker.model.Pocket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * For a given {@link com.skraylabs.poker.model.GameState}, calculates the outcome probability for
 * each Player.
 */
class ProbabilityCalculator {

  private static final int DECK_SIZE = 52;

  private GameState gameState;

  public ProbabilityCalculator(GameState gameState) {
    this.gameState = gameState;
  }

  /**
   * Generic helper method that calculates the probabilities of multiple outcomes for a given
   * player. Instead of making successive calls to {@link #outcomeForAPlayer(Outcome, int)} for each
   * type of outcome, this method offers a way to batch together calculations for multiple outcome
   * types.
   *
   * @param outcomes specifies which {@link Outcome} types to calculate
   * @param playerIndex index of Player in the GameState. A number in range [0, 9].
   *
   * @return probabilities for each type of {@link Outcome} requested
   */
  Map<Outcome, Double> outcomesForAPlayer(Collection<Outcome> outcomes, int playerIndex) {
    // Sanity check
    if (playerIndex < 0 || playerIndex >= GameState.MAX_PLAYERS) {
      throw new IllegalArgumentException(String
          .format("Parameter \"playerIndex\" must be in range [0, %d].", GameState.MAX_PLAYERS));
    }

    Map<Outcome, Double> result = new HashMap<>();
    Collection<Card> dealtCards = CardUtils.collectCards(gameState);
    Collection<Card> deck = makeDeckOfUndealtCards(dealtCards);

    // Iterate through every possible GameState branch
    Board board = gameState.getBoard();
    Pocket pocket = gameState.getPockets()[playerIndex];
    Map<Outcome, WinLossCounter> counts = countOutcomes(outcomes, CardUtils.collectCards(board),
        CardUtils.collectCards(pocket), deck);
    for (Outcome outcome : counts.keySet()) {
      WinLossCounter count = counts.get(outcome);
      double probability = ((double) count.getWins()) / count.getCountTotal();
      result.put(outcome, probability);
    }

    return result;
  }

  /**
   * Generic helper method that calculates a given outcome for a given player.
   *
   * @param outcome poker outcome to calculate probability for
   * @param playerIndex index of Player in the GameState. A number in range [0, 9].
   * @return the probability of getting the specified poker outcome
   */
  double outcomeForAPlayer(Outcome outcome, int playerIndex) {
    Collection<Outcome> outcomes = new ArrayList<>();
    outcomes.add(outcome);
    Map<Outcome, Double> results = outcomesForAPlayer(outcomes, playerIndex);
    return results.get(outcome);
  }

  /**
   * Helper method that evaluates all the remaining combinations for a given set of board cards and
   * counts how many of contain a given Poker type (e.g. Two of a Kind).
   *
   * @param outcomes events to count
   * @param board cards collected from a {@link Board}
   * @param pocket cards collected from a {@link Pocket}
   * @param undealtCards collection of cards that have yet to be dealt
   * @return Map of Outcomes to Points, where each Point is a pair of numbers (x, y) where x is the
   *         number of target outcomes, and y is the total number of outcomes
   */
  static Map<Outcome, WinLossCounter> countOutcomes(Collection<Outcome> outcomes,
      Collection<Card> board, Collection<Card> pocket, Collection<Card> undealtCards) {
    Map<Outcome, WinLossCounter> result = outcomes.stream()
        .collect(Collectors.toMap(outcome -> outcome, outcome -> new WinLossCounter()));
    if (board.size() == 5) {
      // Board is complete
      Collection<Card> cards = collectHandCards(board, pocket);
      OutcomeChecker checker = new OutcomeChecker(cards);
      for (Outcome outcome : outcomes) {
        if (checker.hasOutcome(outcome)) {
          result.get(outcome).incrementWinsBy(1);
        } else {
          result.get(outcome).incrementLossesBy(1);
        }
      }
    } else {
      // Board is incomplete
      // Recurse on all possible cards that could be dealt next
      Collection<Card> dealtCards = new ArrayList<>();
      for (Card card : undealtCards) {
        Collection<Card> nextBoard = new ArrayList<>(board);
        nextBoard.add(card);
        dealtCards.add(card);
        Collection<Card> nextUndealtCards = new ArrayList<>(undealtCards);
        nextUndealtCards.removeAll(dealtCards);
        Map<Outcome, WinLossCounter> nextCounts =
            countOutcomes(outcomes, nextBoard, pocket, nextUndealtCards);
        for (Outcome outcome : result.keySet()) {
          result.get(outcome).incrementBy(nextCounts.get(outcome));
        }
      }
    }
    return result;
  }

  /**
   * Helper method that creates a full deck of 52 cards minus a collection of cards that have
   * already been dealt. This is useful when evaluating which outcomes are possible given an
   * incomplete {@link GameState}.
   *
   * @param cardsToExclude cards that should not be part of the undealt deck
   * @return deck of cards minus {@code cardsToExclude}
   */
  private Collection<Card> makeDeckOfUndealtCards(Collection<Card> cardsToExclude) {
    return IntStream.range(0, DECK_SIZE)
        .mapToObj(CardUtils::cardFromNumber)
        .filter(card -> !cardsToExclude.contains(card))
        .collect(Collectors.toList());
  }

  /**
   * Helper method to gather cards that could form a player's hand -- the combination of community
   * cards (Board) and Pocket cards.
   *
   * @param board Board cards
   * @param pocket Pocket cards
   * @return Collection of cards drawn from {@code board} and {@code pocket}
   */
  static Collection<Card> collectHandCards(Collection<Card> board, Collection<Card> pocket) {
    Collection<Card> cards = new ArrayList<>(board);
    cards.addAll(pocket);
    return cards;
  }

  /**
   * Report the probabilities of each kind of poker {@link Outcome} for a player.
   *
   * @param playerIndex index of Player in the GameState. A number in range [0, 9].
   * @return a map o probabilities for each category of poker outcome.
   */
  public Map<Outcome, Double> allOutcomesForAPlayer(int playerIndex) {
    Collection<Outcome> outcomes = new ArrayList<>();
    outcomes.add(Outcome.TWO_OF_A_KIND);
    outcomes.add(Outcome.TWO_PAIR);
    outcomes.add(Outcome.THREE_OF_A_KIND);
    outcomes.add(Outcome.STRAIGHT);
    outcomes.add(Outcome.FLUSH);
    outcomes.add(Outcome.FULL_HOUSE);
    outcomes.add(Outcome.FOUR_OF_A_KIND);
    outcomes.add(Outcome.STRAIGHT_FLUSH);
    outcomes.add(Outcome.ROYAL_FLUSH);
    return outcomesForAPlayer(outcomes, playerIndex);
  }

  /**
   * Report the probability of a player getting a Two Of A Kind.
   *
   * @param playerIndex index of Player in the GameState. A number in range [0, 9].
   * @return the probability of getting a Two Of A Kind.
   */
  public double twoOfAKindForPlayer(int playerIndex) {
    return outcomeForAPlayer(Outcome.TWO_OF_A_KIND, playerIndex);
  }

  /**
   * Report the probability of a player getting a Two Pair.
   *
   * @param playerIndex index of Player in the GameState. A number in range [0, 9].
   * @return the probability of getting a Two Pair.
   */
  public double twoPairForPlayer(int playerIndex) {
    return outcomeForAPlayer(Outcome.TWO_PAIR, playerIndex);
  }

  /**
   * Report the probability of a player getting a Three Of A Kind.
   *
   * @param playerIndex index of Player in the GameState. A number in range [0, 9].
   * @return the probability of getting a Three Of A Kind.
   */
  public double threeOfAKindForPlayer(int playerIndex) {
    return outcomeForAPlayer(Outcome.THREE_OF_A_KIND, playerIndex);
  }

  /**
   * Report the probability of a player getting a Straight.
   *
   * @param playerIndex index of Player in the GameState. A number in range [0, 9].
   * @return the probability of getting a Straight.
   */
  public double straightForPlayer(int playerIndex) {
    return outcomeForAPlayer(Outcome.STRAIGHT, playerIndex);
  }

  /**
   * Report the probability of a player getting a Flush.
   *
   * @param playerIndex index of Player in the GameState. A number in range [0, 9].
   * @return the probability of getting a Flush.
   */
  public double flushForPlayer(int playerIndex) {
    return outcomeForAPlayer(Outcome.FLUSH, playerIndex);
  }

  /**
   * Report the probability of a player getting a Full House.
   *
   * @param playerIndex index of Player in the GameState. A number in range [0, 9].
   * @return the probability of getting a Full House.
   */
  public double fullHouseForPlayer(int playerIndex) {
    return outcomeForAPlayer(Outcome.FULL_HOUSE, playerIndex);
  }

  /**
   * Report the probability of a player getting a Four Of A Kind.
   *
   * @param playerIndex index of Player in the GameState. A number in range [0, 9].
   * @return the probability of getting a Four Of A Kind.
   */
  public double fourOfAKindForPlayer(int playerIndex) {
    return outcomeForAPlayer(Outcome.FOUR_OF_A_KIND, playerIndex);
  }

  /**
   * Report the probability of a player getting a Straight Flush.
   *
   * @param playerIndex index of Player in the GameState. A number in range [0, 9].
   * @return the probability of getting a Straight Flush.
   */
  public double straightFlushForPlayer(int playerIndex) {
    return outcomeForAPlayer(Outcome.STRAIGHT_FLUSH, playerIndex);
  }

  /**
   * Report the probability of a player getting a Royal Flush.
   *
   * @param playerIndex index of Player in the GameState. A number in range [0, 9].
   * @return the probability of getting a Royal Flush.
   */
  public double royalFlushForPlayer(int playerIndex) {
    return outcomeForAPlayer(Outcome.ROYAL_FLUSH, playerIndex);
  }
}