package com.skraylabs.poker;

import com.skraylabs.poker.model.Board;
import com.skraylabs.poker.model.Card;
import com.skraylabs.poker.model.CardUtils;
import com.skraylabs.poker.model.GameState;
import com.skraylabs.poker.model.Pocket;
import com.skraylabs.poker.model.Rank;
import com.skraylabs.poker.model.Suit;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
   * Report the probability of a player getting a Two Pair.
   *
   * @param playerIndex index of Player in the GameState. A number in range [0, 9].
   * @return the probability of getting a Two Pair.
   */
  public double twoPairForPlayer(int playerIndex) {
    return outcomeForAPlayer(ProbabilityCalculator::hasTwoPair, playerIndex);
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
   * Report the probability of a player getting a Straight.
   *
   * @param playerIndex index of Player in the GameState. A number in range [0, 9].
   * @return the probability of getting a Straight.
   */
  public double straightForPlayer(int playerIndex) {
    return 0.0;
  }

  /**
   * Report the probability of a player getting a Flush.
   *
   * @param playerIndex index of Player in the GameState. A number in range [0, 9].
   * @return the probability of getting a Flush.
   */
  public double flushForPlayer(int playerIndex) {
    return outcomeForAPlayer(ProbabilityCalculator::hasFlush, playerIndex);
  }

  /**
   * Report the probability of a player getting a Full House.
   *
   * @param playerIndex index of Player in the GameState. A number in range [0, 9].
   * @return the probability of getting a Full House.
   */
  public double fullHouseForPlayer(int playerIndex) {
    return outcomeForAPlayer(ProbabilityCalculator::hasFullHouse, playerIndex);
  }

  /**
   * Report the probability of a player getting a Four Of A Kind.
   *
   * @param playerIndex index of Player in the GameState. A number in range [0, 9].
   * @return the probability of getting a Four Of A Kind.
   */
  public double fourOfAKindForPlayer(int playerIndex) {
    return outcomeForAPlayer(ProbabilityCalculator::hasFourOfAKind, playerIndex);
  }

  /**
   * Report the probability of a player getting a Straight Flush.
   *
   * @param playerIndex index of Player in the GameState. A number in range [0, 9].
   * @return the probability of getting a Straight Flush.
   */
  public double straightFlushForPlayer(int playerIndex) {
    return 0.0;
  }

  /**
   * Report the probability of a player getting a Royal Flush.
   *
   * @param playerIndex index of Player in the GameState. A number in range [0, 9].
   * @return the probability of getting a Royal Flush.
   */
  public double royalFlushForPlayer(int playerIndex) {
    return 0.0;
  }

  /**
   * Helper method that determines if an <i>n</i> of a Type exists on a given combination of board
   * and pocket cards -- e.g. for n = 3 and T = Rank, it will determine if there is a Three of a
   * Kind. A single collection of cards matching this criteria is returned.
   *
   * @param cards combined cards form a player's Pocket and the community Board
   * @param number a positive integer <i>n</i>
   * @param typeFunction a Function that derives a Type from a {@link Card} -- in practice this
   *        function should return either {@link Rank} or {@link Suit}
   * @return a collection of <i>n</i> or more cards of the same Type, if found; otherwise, an empty
   *         collection
   */
  private static <T> Collection<Card> collectNOfAType(Collection<Card> cards, int number,
      Function<Card, T> typeFunction) {
    List<Card> result = new ArrayList<Card>();
    Map<T, List<Card>> cardsByType = cards.stream().collect(Collectors.groupingBy(typeFunction));
    for (T key : cardsByType.keySet()) {
      List<Card> cardsOfType = cardsByType.get(key);
      if (cardsOfType.size() >= number) {
        result = cardsOfType;
      }
    }
    return result;
  }

  /**
   * Helper method that determines if an <i>n</i> of a Type exists on a given combination of board
   * and pocket cards -- e.g. for n = 3 and T = Rank, it will determine if there is a Three of a
   * Kind.
   *
   * @param cards combined cards from a player's Pocket and the community Board
   * @param number a positive integer <i>n</i>
   * @param typeFunction a Function that derives a Type from a {@link Card} -- in practice this
   *        function should return either {@link Rank} or {@link Suit}
   * @return {@code true} if there is are {@code number} or more cards of the same type.
   */
  private static <T> boolean hasNOfAType(Collection<Card> cards, int number,
      Function<Card, T> typeFunction) {
    if (cards == null) {
      throw new IllegalArgumentException("Parameter \"cards\" must be non-null.");
    }
    if (number <= 0) {
      throw new IllegalArgumentException("Parameter \"number\" must be a positive value.");
    }
    boolean result = false;
    Map<T, Long> countByType =
        cards.stream().collect(Collectors.groupingBy(typeFunction, Collectors.counting()));
    for (Long count : countByType.values()) {
      if (count >= number) {
        result = true;
        break;
      }
    }
    return result;
  }

  /**
   * Helper method that determines if an <i>n</i> of a Kind exists on a given combination of board
   * and pocket cards -- e.g. for n = 3, it will determine if there is a Three of a Kind.
   *
   * @param cards combined cards from a player's Pocket and the community Board
   * @param number a positive integer <i>n</i>
   * @return {@code true} if there is are {@code number} or more cards of the same rank.
   */
  static boolean hasNOfAKind(Collection<Card> cards, int number) {
    return hasNOfAType(cards, number, Card::getRank);
  }

  /**
   * Helper method that determines if a Two of a Kind exists on a given combination of board and
   * pocket cards.
   *
   * @param cards combined cards from a player's Pocket and the community Board
   * @return {@code true} if there is at least one Two of a Kind; {@code false} otherwise
   */
  static boolean hasTwoOfAKind(Collection<Card> cards) {
    return hasNOfAKind(cards, 2);
  }

  /**
   * Helper method that determines if a Two Pair exists on a given combination of board and pocket
   * cards.
   *
   * @param cards combined cards from a player's Pocket and the community Board
   * @return {@code true} if there is a Two Pair; {@code false} otherwise
   */
  static boolean hasTwoPair(Collection<Card> cards) {
    boolean result = false;
    if (cards.size() >= 4) {
      Collection<Card> cardsCopy = new ArrayList<Card>(cards);
      Collection<Card> firstPair = collectNOfAType(cardsCopy, 2, Card::getRank);
      if (!firstPair.isEmpty()) {
        cardsCopy.removeAll(firstPair);
        Collection<Card> secondPair = collectNOfAType(cardsCopy, 2, Card::getRank);
        if (!secondPair.isEmpty()) {
          result = true;
        }
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
    return hasNOfAKind(cards, 3);
  }

  /**
   * Helper method that determines if a Four of a Kind exists on a given combination of board and
   * pocket cards.
   *
   * @param cards combined cards from a player's Pocket and the community Board
   * @return {@code true} if there is a Four of a Kind; {@code false} otherwise
   */
  static boolean hasFourOfAKind(Collection<Card> cards) {
    return hasNOfAKind(cards, 4);
  }

  /**
   * Helper method that determines if a Flush exists on a given combination of board and pocket
   * cards.
   *
   * @param cards combined cards from a player's Pocket and the community Board
   * @return {@code true} if there are 5 or more cards of the same Suit; {@code false} otherwise
   */
  static boolean hasFlush(Collection<Card> cards) {
    return hasNOfAType(cards, 5, Card::getSuit);
  }

  /**
   * Helper method that determines if a Full House exists on a given combination of board and pocket
   * cards.
   *
   * @param cards combined cards from a player's Pocket and the community Board
   * @return {@code true} if there are 3 or more cards of the same Suit and 2 or more cards of a
   *         different Suit; {@code false} otherwise
   */
  static boolean hasFullHouse(Collection<Card> cards) {
    boolean result = false;
    if (cards.size() >= 5) {
      Collection<Card> cardsCopy = new ArrayList<Card>(cards);
      Collection<Card> threeOfAKind = collectNOfAType(cardsCopy, 3, Card::getRank);
      if (!threeOfAKind.isEmpty()) {
        cardsCopy.removeAll(threeOfAKind);
        Collection<Card> pair = collectNOfAType(cardsCopy, 2, Card::getRank);
        if (!pair.isEmpty()) {
          result = true;
        }
      }
    }
    return result;
  }
}
