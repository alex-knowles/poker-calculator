package com.skraylabs.poker;

import com.skraylabs.poker.model.Card;
import com.skraylabs.poker.model.Rank;
import com.skraylabs.poker.model.Suit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Analyze a collection of Cards to determine if various poker outcomes are present or not.
 */
public class OutcomeChecker {
  private Collection<Card> cards;

  private static HashMap<Outcome, Predicate<OutcomeChecker>> predicateMap = new HashMap<>();

  static {
    predicateMap.put(Outcome.TWO_OF_A_KIND, OutcomeChecker::hasTwoOfAKind);
    // TODO: replace stubs below
    predicateMap.put(Outcome.TWO_PAIR, checker -> false);
    predicateMap.put(Outcome.THREE_OF_A_KIND, checker -> false);
    predicateMap.put(Outcome.STRAIGHT, checker -> false);
    predicateMap.put(Outcome.FLUSH, checker -> false);
    predicateMap.put(Outcome.FULL_HOUSE, checker -> false);
    predicateMap.put(Outcome.FOUR_OF_A_KIND, checker -> false);
    predicateMap.put(Outcome.STRAIGHT_FLUSH, checker -> false);
    predicateMap.put(Outcome.ROYAL_FLUSH, checker -> false);
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
    for (T key : cardsByType.keySet()) {
      List<Card> cardsOfType = cardsByType.get(key);
      if (cardsOfType.size() >= number) {
        result = cardsOfType;
      }
    }
    return result;
  }
}
