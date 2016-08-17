package com.skraylabs.poker;

import com.skraylabs.poker.model.Board;
import com.skraylabs.poker.model.Card;
import com.skraylabs.poker.model.CardUtils;
import com.skraylabs.poker.model.GameState;
import com.skraylabs.poker.model.Pocket;
import com.skraylabs.poker.model.Rank;
import com.skraylabs.poker.model.Suit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
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
   * A Function that evaluates a collection of cards to see if it matches conditions for a type of
   * Poker outcome (e.g. Two of Kind, Royal Flush, etc...).
   */
  interface HandEvaluator extends Function<Collection<Card>, Boolean> {
  }

  /**
   * Generic helper method that calculates the probabilities of multiple outcomes for a given
   * player. Instead of making successive calls to {@link #outcomeForAPlayer(HandEvaluator, int)}
   * for each type of outcome, this method offers a way to batch together calculations for multiple
   * outcome types.
   *
   * @param outcomeEvaluators specifies which {@link Outcome} types to calculate and a matching
   *        {@link HandEvaulator}.
   * @param playerIndex index of Player in the GameState. A number in range [0, 9].
   *
   * @return probabilities for each type of {@link Outcome} requested
   */
  Map<Outcome, Double> outcomesForAPlayer(Map<Outcome, HandEvaluator> outcomeEvaluators,
      int playerIndex) {
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
    Map<Outcome, WinLossCounter> counts = countOutcomes(outcomeEvaluators,
        CardUtils.collectCards(board), CardUtils.collectCards(pocket), deck);
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
   * @param outcomeEvaluator evaluates if a hand meets the criteria of a categorical poker outcome
   *        (Two Of Kind, Full House, etc...)
   * @param playerIndex index of Player in the GameState. A number in range [0, 9].
   * @return the probability of getting the specified poker outcome
   */
  double outcomeForAPlayer(HandEvaluator outcomeEvaluator, int playerIndex) {
    Outcome arbitraryKey = Outcome.FLUSH;
    HashMap<Outcome, HandEvaluator> evaluators = new HashMap<>();
    evaluators.put(arbitraryKey, outcomeEvaluator);
    Map<Outcome, Double> outcomes = outcomesForAPlayer(evaluators, playerIndex);
    return outcomes.get(arbitraryKey);
  }

  /**
   * Helper method that evaluates all the remaining combinations for a given set of board cards and
   * counts how many of contain a given Poker type (e.g. Two of a Kind).
   *
   * @param evaluators Map of Outcomes (keys) to corresponding HandEvaluators (values)
   * @param board cards collected from a {@link Board}
   * @param pocket cards collected from a {@link Pocket}
   * @param undealtCards collection of cards that have yet to be dealt
   * @return Map of Outcomes to Points, where each Point is a pair of numbers (x, y) where x is the
   *         number of target outcomes, and y is the total number of outcomes
   */
  static Map<Outcome, WinLossCounter> countOutcomes(Map<Outcome, HandEvaluator> evaluators,
      Collection<Card> board, Collection<Card> pocket, Collection<Card> undealtCards) {
    Map<Outcome, WinLossCounter> result = evaluators.keySet().stream()
        .collect(Collectors.toMap(outcome -> outcome, outcome -> new WinLossCounter()));
    if (board.size() == 5) {
      // Board is complete
      Collection<Card> cards = collectHandCards(board, pocket);
      for (Outcome outcome : evaluators.keySet()) {
        HandEvaluator evaluator = evaluators.get(outcome);
        if (evaluator.apply(cards)) {
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
            countOutcomes(evaluators, nextBoard, pocket, nextUndealtCards);
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
    HashMap<Outcome, HandEvaluator> evaluators = new HashMap<>();
    evaluators.put(Outcome.TWO_OF_A_KIND, ProbabilityCalculator::hasTwoOfAKind);
    evaluators.put(Outcome.TWO_PAIR, ProbabilityCalculator::hasTwoPair);
    evaluators.put(Outcome.THREE_OF_A_KIND, ProbabilityCalculator::hasThreeOfAKind);
    evaluators.put(Outcome.STRAIGHT, ProbabilityCalculator::hasStraight);
    evaluators.put(Outcome.FLUSH, ProbabilityCalculator::hasFlush);
    evaluators.put(Outcome.FULL_HOUSE, ProbabilityCalculator::hasFullHouse);
    evaluators.put(Outcome.FOUR_OF_A_KIND, ProbabilityCalculator::hasFourOfAKind);
    evaluators.put(Outcome.STRAIGHT_FLUSH, ProbabilityCalculator::hasStraightFlush);
    evaluators.put(Outcome.ROYAL_FLUSH, ProbabilityCalculator::hasRoyalFlush);
    return outcomesForAPlayer(evaluators, playerIndex);
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
    return outcomeForAPlayer(ProbabilityCalculator::hasStraight, playerIndex);
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
    return outcomeForAPlayer(ProbabilityCalculator::hasStraightFlush, playerIndex);
  }

  /**
   * Report the probability of a player getting a Royal Flush.
   *
   * @param playerIndex index of Player in the GameState. A number in range [0, 9].
   * @return the probability of getting a Royal Flush.
   */
  public double royalFlushForPlayer(int playerIndex) {
    return outcomeForAPlayer(ProbabilityCalculator::hasRoyalFlush, playerIndex);
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
    // Sanity checks
    if (cards == null) {
      throw new IllegalArgumentException("Parameter \"cards\" must be non-null.");
    }
    if (number <= 0) {
      throw new IllegalArgumentException("Parameter \"number\" must be a positive value.");
    }

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
    Collection<Card> cardsOfType = collectNOfAType(cards, number, typeFunction);
    return !cardsOfType.isEmpty();
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
      Collection<Card> cardsCopy = new ArrayList<>(cards);
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
   * Helper method that determines if a Straight exists on a given combination of board and pocket
   * cards.
   *
   * @param cards combined cards from a player's Pocket and the community Board
   * @return {@code true} if there is a Straight; {@code false} otherwise
   */
  static boolean hasStraight(Collection<Card> cards) {
    boolean result = false;
    if (cards.size() >= 5) {
      // Check for Straights with Aces Low
      result = hasStraight(cards, rank -> rank.aceLowValue());

      // Check for Straights with Aces High
      if (!result) {
        result = hasStraight(cards, rank -> rank.aceHighValue());
      }
    }

    return result;
  }

  /**
   * Helper method that determines if a collection of cards contains a straight. Ordering of card
   * rank is singular and is determined by a given Function that maps {@link Rank} to an integer
   * value. In other words, Aces can be low or high, but not both.
   *
   * @param cards to evaluate
   * @param rankFunction returns a single integer value for a given {@link Rank}
   * @return {@code true} if the cards contain 5 cards of consecutive Rank; {@code false} otherwise
   */
  private static boolean hasStraight(Collection<Card> cards, Function<Rank, Integer> rankFunction) {
    boolean result = false;
    if (cards.size() >= 5) {
      // Sort cards by Rank, according to rankFunction
      ArrayList<Card> sortedCards = new ArrayList<>(cards);
      Comparator<Card> rankComparator = (card1, card2) ->
          rankFunction.apply(card1.getRank()) - rankFunction.apply(card2.getRank());
      sortedCards.sort(rankComparator);

      // Check for Straights, according to rankFunction
      ArrayList<Card> cardSequence = new ArrayList<>();
      for (Card card : sortedCards) {
        if (cardSequence.isEmpty()) {
          // Begin a sequence
          cardSequence.add(card);
        } else {
          Card previousCard = cardSequence.get(cardSequence.size() - 1);
          int cardRankValue = rankFunction.apply(card.getRank());
          int previousCardRankValue = rankFunction.apply(previousCard.getRank());
          int rankValueDelta = Math.abs(cardRankValue - previousCardRankValue);
          if (rankValueDelta == 1) {
            // Advance the sequence
            cardSequence.add(card);
            if (cardSequence.size() == 5) {
              result = true;
              break;
            }
          } else if (rankValueDelta > 1) {
            // Restart the sequence
            cardSequence.clear();
            cardSequence.add(card);
          } else if (rankValueDelta == 0) {
            // Do nothing, the sequence already has one of this Rank
          }
        }
      }
    }

    return result;
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
      Collection<Card> cardsCopy = new ArrayList<>(cards);
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
   * Helper method that determines if a Straight Flush exists on a given combination of board and
   * pocket cards.
   *
   * @param cards combined cards from a player's Pocket and the community Board
   * @return {@code true} if there is a Straight Flush; {@code false} otherwise
   */
  static boolean hasStraightFlush(Collection<Card> cards) {
    boolean result = false;
    if (cards.size() >= 5) {
      Map<Suit, List<Card>> cardsBySuit =
          cards.stream().collect(Collectors.groupingBy(Card::getSuit));
      for (List<Card> suitedCards : cardsBySuit.values()) {
        if (suitedCards.size() >= 5) {
          if (hasStraight(suitedCards)) {
            result = true;
            break;
          }
        }
      }
    }
    return result;
  }

  /**
   * Helper method that determines if a Royal Flush exists on a given combination of board and
   * pocket cards.
   *
   * @param cards combined cards from a player's Pocket and the community Board
   * @return {@code true} if there is a Royal Flush; {@code false} otherwise
   */
  static boolean hasRoyalFlush(Collection<Card> cards) {
    Predicate<Card> tenAndHigherFilter =
        card -> card.getRank().aceHighValue() >= Rank.TEN.aceHighValue();
    List<Card> topFiveRanks =
        cards.stream().filter(tenAndHigherFilter).collect(Collectors.toList());
    return hasStraightFlush(topFiveRanks);
  }
}
