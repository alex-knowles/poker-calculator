package com.skraylabs.poker.outcome;

import com.skraylabs.poker.model.Card;
import com.skraylabs.poker.model.Rank;
import com.skraylabs.poker.model.Suit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Analyze a collection of Cards to determine if various poker outcomes are present or not.
 */
public class OutcomeChecker {
  private static final int TWO_PAIR_SIZE = 4;
  private static final int STRAIGHT_SIZE = 5;

  private Collection<Card> cards;

  private static HashMap<Outcome, Predicate<OutcomeChecker>> predicateMap = new HashMap<>();

  static {
    predicateMap.put(Outcome.TWO_OF_A_KIND, OutcomeChecker::hasTwoOfAKind);
    predicateMap.put(Outcome.TWO_PAIR, OutcomeChecker::hasTwoPair);
    predicateMap.put(Outcome.THREE_OF_A_KIND, OutcomeChecker::hasThreeOfAKind);
    predicateMap.put(Outcome.STRAIGHT, OutcomeChecker::hasStraight);
    predicateMap.put(Outcome.FLUSH, OutcomeChecker::hasFlush);
    predicateMap.put(Outcome.FULL_HOUSE, OutcomeChecker::hasFullHouse);
    predicateMap.put(Outcome.FOUR_OF_A_KIND, OutcomeChecker::hasFourOfAKind);
    predicateMap.put(Outcome.STRAIGHT_FLUSH, OutcomeChecker::hasStraightFlush);
    predicateMap.put(Outcome.ROYAL_FLUSH, OutcomeChecker::hasRoyalFlush);
  }

  public OutcomeChecker(Collection<Card> cards) {
    this.cards = cards;
  }

  /**
   * Check for a given {@link Outcome}.
   *
   * @param outcome to check for.
   * @return {@code true} if the {@code outcome} conditions are met; {@code false} otherwise
   */
  public boolean hasOutcome(Outcome outcome) {
    Predicate<OutcomeChecker> predicate = predicateMap.get(outcome);
    return predicate.test(this);
  }

  /**
   * Check for a Two of a Kind.
   *
   * @return {@code true} if there is at least one Two of a Kind; {@code false} otherwise
   */
  public boolean hasTwoOfAKind() {
    return hasNOfAKind(2);
  }

  /**
   * Check for a Two Pair.
   *
   * @return {@code true} if there is a Two Pair; {@code false} otherwise
   */
  public boolean hasTwoPair() {
    boolean result = false;
    if (cards.size() >= TWO_PAIR_SIZE) {
      Collection<Card> cardsCopy = new ArrayList<>(cards);
      OutcomeChecker copyChecker = new OutcomeChecker(cardsCopy);
      Collection<Card> firstPair = copyChecker.collectNOfAType(2, Card::getRank);
      if (!firstPair.isEmpty()) {
        cardsCopy.removeAll(firstPair);
        Collection<Card> secondPair = copyChecker.collectNOfAType(2, Card::getRank);
        if (!secondPair.isEmpty()) {
          result = true;
        }
      }
    }
    return result;
  }

  /**
   * Check for a Three of a Kind.
   *
   * @return {@code true} if there is at least one Three of a Kind; {@code false} otherwise
   */
  public boolean hasThreeOfAKind() {
    return hasNOfAKind(3);
  }

  /**
   * Check for a Straight.
   *
   * @return {@code true} if there is at least one Straight; {@code false} otherwise
   */
  public boolean hasStraight() {
    boolean result = false;
    if (cards.size() >= STRAIGHT_SIZE) {
      List<Rank> sortedRanksAceLowAndHigh = extractRanksInOrder(cards);

      // Gather adjacencies
      ArrayList<Rank> rankSequence = new ArrayList<>();
      for (Rank rank : sortedRanksAceLowAndHigh) {
        if (rankSequence.isEmpty()) {
          // Begin a sequence
          rankSequence.add(rank);
        } else {
          Rank previousRank = rankSequence.get(rankSequence.size() - 1);
          if (ranksAreAdjacent(rank, previousRank)) {
            // Advance the sequence
            rankSequence.add(rank);
            if (rankSequence.size() == STRAIGHT_SIZE) {
              result = true;
              break;
            }
          } else if (rank != previousRank) {
            // Restart the sequence
            rankSequence.clear();
            rankSequence.add(rank);
          }
        }
      }
    }

    return result;
  }

  /**
   * Check for a Flush.
   *
   * @return {@code true} if there is at least one Flush; {@code false} otherwise
   */
  public boolean hasFlush() {
    return hasNOfAType(5, Card::getSuit);
  }

  /**
   * Check for a Full House.
   *
   * @return {@code true} if there is at least one Full House; {@code false} otherwise
   */
  public boolean hasFullHouse() {
    boolean result = false;
    if (cards.size() >= 5) {
      Collection<Card> cardsCopy = new ArrayList<>(cards);
      OutcomeChecker copyChecker = new OutcomeChecker(cardsCopy);
      Collection<Card> threeOfAKind = copyChecker.collectNOfAType(3, Card::getRank);
      if (!threeOfAKind.isEmpty()) {
        cardsCopy.removeAll(threeOfAKind);
        Collection<Card> pair = copyChecker.collectNOfAType(2, Card::getRank);
        if (!pair.isEmpty()) {
          result = true;
        }
      }
    }
    return result;
  }

  /**
   * Check for a Four of a Kind.
   *
   * @return {@code true} if there is at least one Four of a Kind; {@code false} otherwise
   */
  public boolean hasFourOfAKind() {
    return hasNOfAKind(4);
  }

  /**
   * Check for a Straight Flush.
   *
   * @return {@code true} if there is at least one Straight Flush; {@code false} otherwise
   */
  public boolean hasStraightFlush() {
    boolean result = false;
    if (cards.size() >= STRAIGHT_SIZE) {
      Map<Suit, List<Card>> cardsBySuit =
          cards.stream().collect(Collectors.groupingBy(Card::getSuit));
      for (List<Card> suitedCards : cardsBySuit.values()) {
        if (suitedCards.size() >= STRAIGHT_SIZE) {
          OutcomeChecker straightChecker = new OutcomeChecker(suitedCards);
          if (straightChecker.hasStraight()) {
            result = true;
            break;
          }
        }
      }
    }
    return result;
  }

  /**
   * Check for a Royal Flush.
   *
   * @return {@code true} if there is at least one Royal Flush; {@code false} otherwise
   */
  public boolean hasRoyalFlush() {
    List<Card> topFiveRanks =
        cards.stream().filter(card -> card.getRank().aceHighValue() >= Rank.TEN.aceHighValue())
            .collect(Collectors.toList());
    OutcomeChecker straightChecker = new OutcomeChecker(topFiveRanks);
    return straightChecker.hasStraightFlush();
  }

  /**
   * Helper method that checks for an <i>n</i> of a Kind exists -- e.g. for n = 3, it will check for
   * a Three of a Kind.
   *
   * @param number a positive integer <i>n</i>
   * @return {@code true} if there is are {@code number} or more cards of the same rank.
   */
  private boolean hasNOfAKind(int number) {
    return hasNOfAType(number, Card::getRank);
  }

  /**
   * Helper method that checks for an <i>n</i> of a Type -- e.g. for n = 3 and T = Rank, it will
   * check for a Three of a Kind.
   *
   * @param number a positive integer <i>n</i>
   * @param typeFunction a Function that derives a Type from a {@link Card} -- in practice this
   *        function should return either {@link Rank} or {@link Suit}
   * @return {@code true} if there is are {@code number} or more cards of the same type.
   */
  private <T> boolean hasNOfAType(int number, Function<Card, T> typeFunction) {
    Collection<Card> cardsOfType = collectNOfAType(number, typeFunction);
    return !cardsOfType.isEmpty();
  }

  /**
   * Helper method that checks for an <i>n</i> of a Type -- e.g. for n = 3 and T = Rank, it will
   * check for a Three of a Kind. A single collection of cards matching this criteria is returned.
   *
   * @param number a positive integer <i>n</i>
   * @param typeFunction a Function that derives a Type from a {@link Card} -- in practice this
   *        function should return either {@link Rank} or {@link Suit}
   * @return a collection of <i>n</i> or more cards of the same Type, if found; otherwise, an empty
   *         collection
   */
  private <T> Collection<Card> collectNOfAType(int number, Function<Card, T> typeFunction) {
    // Sanity checks
    if (number <= 0) {
      throw new IllegalArgumentException("Parameter \"number\" must be a positive value.");
    }

    List<Card> result = new ArrayList<Card>();
    Map<T, List<Card>> cardsByType = cards.stream().collect(Collectors.groupingBy(typeFunction));
    for (List<Card> cardsOfType : cardsByType.values()) {
      if (cardsOfType.size() >= number) {
        result = cardsOfType;
      }
    }
    return result;
  }

  /**
   * Returns a "distilled" list of Ranks from a given card collection. Each Rank appears only once,
   * except for Ace, which will be placed both low and high. Results are sorted from low to high.
   *
   * @param cards collection from which to extract ranks
   * @return ranks representing {@code cards}
   */
  private static List<Rank> extractRanksInOrder(Collection<Card> cards) {
    Set<Rank> ranks = cards.stream().collect(Collectors.groupingBy(Card::getRank)).keySet();

    List<Rank> sortedRanksAceLow = ranks.stream()
        .sorted((rank1, rank2) -> rank1.aceLowValue() - rank2.aceLowValue())
        .collect(Collectors.toList());

    List<Rank> sortedRanksAceLowAndHigh = sortedRanksAceLow;
    if (ranks.contains(Rank.ACE)) {
      sortedRanksAceLowAndHigh.add(Rank.ACE);
    }

    return sortedRanksAceLowAndHigh;
  }

  /**
   * Helper method that returns true if two Ranks are "adjacent". For example, a Jack is adjacent to
   * a Ten, but not to a King.
   *
   * @param rank1 rank to compare
   * @param rank2 rank to compare
   * @return {@code true} if {@code rank1} and {@code rank2} are adjacent; {@code false} if they are
   *         equivalent are non-neighboring
   */
  private static boolean ranksAreAdjacent(Rank rank1, Rank rank2) {
    int shortestDifference = Math.min(Math.abs(rank1.aceLowValue() - rank2.aceLowValue()),
        Math.abs(rank1.aceHighValue() - rank2.aceHighValue()));
    return shortestDifference == 1;
  }
}
