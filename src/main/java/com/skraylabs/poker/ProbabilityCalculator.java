package com.skraylabs.poker;

import com.skraylabs.poker.model.Board;
import com.skraylabs.poker.model.Card;
import com.skraylabs.poker.model.CardUtils;
import com.skraylabs.poker.model.GameState;
import com.skraylabs.poker.model.Pocket;
import com.skraylabs.poker.model.Rank;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * For a given {@link com.skraylabs.poker.model.GameState}, calculates the outcome probability for
 * each Player.
 */
class ProbabilityCalculator {

  private GameState gameState;

  public ProbabilityCalculator(GameState gameState) {
    this.gameState = gameState;
  }

  /**
   * Generic helper method that calculates a given outcome for a given player.
   *
   * @param outcomeEvaluator evaluates if a hand meets the criteria of a categorical poker outcome
   *        (Two Of Kind, Full House, etc...)
   * @param playerIndex index of Player in the GameState. A number in range [0, 9].
   * @return the probability of getting the specified poker outcome
   */
  double outcomeForAPlayer(Function<Collection<Card>, Boolean> outcomeEvaluator, int playerIndex) {
    if (playerIndex < 0 || playerIndex >= GameState.MAX_PLAYERS) {
      throw new IllegalArgumentException(String
          .format("Parameter \"playerIndex\" must be in range [0, %d].", GameState.MAX_PLAYERS));
    }
    Collection<Card> dealtCards = CardUtils.collectCards(gameState);
    // Make a deck of undealt cards
    ArrayList<Card> deck = new ArrayList<Card>();
    for (int i = 0; i < 52; i++) {
      Card card = CardUtils.cardFromNumber(i);
      if (!dealtCards.contains(card)) {
        deck.add(card);
      }
    }
    // Iterate through every possible GameState branch
    Board board = gameState.getBoard();
    Pocket pocket = gameState.getPockets()[playerIndex];
    Point count = countOutcomes(outcomeEvaluator, CardUtils.collectCards(board),
        CardUtils.collectCards(pocket), deck);
    return ((double) count.x) / count.y;
  }

  /**
   * Helper method that evaluates all the remaining combinations for a given set of board cards and
   * counts how many of contain a given Poker type (e.g. Two of a Kind).
   *
   * @param evaulator tests if a Card Collection contains a target Poker hand (e.g. Two of a Kind,
   *        Full House, etc...)
   * @param board cards collected from a {@link Board}
   * @param pocket cards collected from a {@link Pocket}
   * @param undealtCards collection of cards that have yet to be dealt
   * @return a pair of numbers (x, y) where x is the number of target outcomes, and y is the total
   *         number of outcomes
   */
  static Point countOutcomes(Function<Collection<Card>, Boolean> evaluator, Collection<Card> board,
      Collection<Card> pocket, Collection<Card> undealtCards) {
    int winOutcomes = 0;
    int totalOutcomes = 0;
    if (board.size() == 5) {
      // Board is complete
      Collection<Card> cards = collectHandCards(board, pocket);
      if (evaluator.apply(cards)) {
        winOutcomes++;
      }
      totalOutcomes++;
    } else {
      // Board is incomplete
      // Recurse on all possible cards that could be dealt next
      Collection<Card> dealtCards = new ArrayList<Card>();
      for (Card card : undealtCards) {
        Collection<Card> nextBoard = new ArrayList<Card>(board);
        nextBoard.add(card);
        dealtCards.add(card);
        Collection<Card> nextUndealtCards = new ArrayList<Card>(undealtCards);
        nextUndealtCards.removeAll(dealtCards);
        Point nextCount = countOutcomes(evaluator, nextBoard, pocket, nextUndealtCards);
        winOutcomes += nextCount.x;
        totalOutcomes += nextCount.y;
      }
    }
    return new Point(winOutcomes, totalOutcomes);
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
    Collection<Card> cards = new ArrayList<Card>(board);
    cards.addAll(pocket);
    return cards;
  }

  /**
   * Report the probability of a player getting a Two Of A Kind.
   *
   * @param playerIndex index of Player in the GameState. A number in range [0, 9].
   * @return the probability of getting a Two Of A Kind.
   */
  public double twoOfAKindForPlayer(int playerIndex) {
    return outcomeForAPlayer(ProbabilityCalculator::hasTwoOfAKind, playerIndex);
  }

  /**
   * Report the probability of a player getting a Three Of A Kind.
   *
   * @param playerIndex index of Player in the GameState. A number in range [0, 9].
   * @return the probability of getting a Three Of A Kind.
   */
  public double threeOfAKindForPlayer(int playerIndex) {
    return outcomeForAPlayer(ProbabilityCalculator::hasThreeOfAKind, playerIndex);
  }

  /**
   * Helper method that determines if a Two of a Kind exists on a given combination of board and
   * pocket cards.
   *
   * @param cards combined cards from a player's Pocket and the community Board
   * @return {@code true} if there is at least one Two of a Kind; {@code false} otherwise
   */
  static boolean hasTwoOfAKind(Collection<Card> cards) {
    boolean result = false;
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

  /**
   * Helper method that determines if a Three of a Kind exists on a given combination of board and
   * pocket cards.
   *
   * @param cards combined cards from a player's Pocket and the community Board
   * @return {@code true} if there is a Three of a Kind; {@code false} otherwise
   */
  static boolean hasThreeOfAKind(Collection<Card> cards) {
    boolean result = false;
    Map<Rank, Long> countByRank =
        cards.stream().collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));
    for (Long count : countByRank.values()) {
      if (count >= 3) {
        result = true;
        break;
      }
    }
    return result;
  }
}
