package com.skraylabs.poker;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.skraylabs.poker.model.Card;
import com.skraylabs.poker.model.Rank;
import com.skraylabs.poker.model.Suit;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

public class OutcomeCheckerTest {

  private Collection<Card> cards;
  private OutcomeChecker checker;

  @Before
  public void setUp() {
    cards = new ArrayList<>();
  }

  /* hasTwoOfAKind() test methods */

  @Test
  public void givenNegativeCardsHasTwoOfAKindReturnsFalse() {
    cards.add(new Card(Rank.ACE, Suit.CLUBS));
    cards.add(new Card(Rank.QUEEN, Suit.SPADES));
    checker = new OutcomeChecker(cards);

    boolean result = checker.hasTwoOfAKind();

    assertThat(result, is(false));
  }

  @Test
  public void givenPositiveCardsHasTwoOfAKindReturnsTrue() {
    cards.add(new Card(Rank.ACE, Suit.CLUBS));
    cards.add(new Card(Rank.ACE, Suit.SPADES));
    checker = new OutcomeChecker(cards);

    boolean result = checker.hasTwoOfAKind();

    assertThat(result, is(true));
  }

  /* hasThreeOfAKind() test methods */

  @Test
  public void givenNegativeCardsHasThreeOfAKindReturnsFalse() {
    cards.add(new Card(Rank.ACE, Suit.CLUBS));
    cards.add(new Card(Rank.ACE, Suit.SPADES));
    cards.add(new Card(Rank.QUEEN, Suit.SPADES));
    checker = new OutcomeChecker(cards);

    boolean result = checker.hasThreeOfAKind();

    assertThat(result, is(false));
  }

  @Test
  public void givenPositiveCardsHasThreeOfAKindReturnsTrue() {
    cards.add(new Card(Rank.ACE, Suit.CLUBS));
    cards.add(new Card(Rank.ACE, Suit.SPADES));
    cards.add(new Card(Rank.ACE, Suit.HEARTS));
    checker = new OutcomeChecker(cards);

    boolean result = checker.hasThreeOfAKind();

    assertThat(result, is(true));
  }

  /* hasFourOfAKind() test methods */

  @Test
  public void givenNegativeCardsHasFourOfAKindReturnsFalse() {
    cards.add(new Card(Rank.ACE, Suit.CLUBS));
    cards.add(new Card(Rank.ACE, Suit.SPADES));
    cards.add(new Card(Rank.ACE, Suit.HEARTS));
    cards.add(new Card(Rank.QUEEN, Suit.SPADES));
    checker = new OutcomeChecker(cards);

    boolean result = checker.hasFourOfAKind();

    assertThat(result, is(false));
  }

  @Test
  public void givenPositiveCardsHasFourOfAKindReturnsTrue() {
    cards.add(new Card(Rank.ACE, Suit.CLUBS));
    cards.add(new Card(Rank.ACE, Suit.SPADES));
    cards.add(new Card(Rank.ACE, Suit.HEARTS));
    cards.add(new Card(Rank.ACE, Suit.DIAMONDS));
    checker = new OutcomeChecker(cards);

    boolean result = checker.hasFourOfAKind();

    assertThat(result, is(true));
  }

  /* hasFlush() test methods */

  @Test
  public void givenNegativeCardsHasFlushReturnsFalse() {
    cards.add(new Card(Rank.ACE, Suit.HEARTS));
    cards.add(new Card(Rank.TWO, Suit.HEARTS));
    cards.add(new Card(Rank.THREE, Suit.HEARTS));
    cards.add(new Card(Rank.FOUR, Suit.HEARTS));
    cards.add(new Card(Rank.FIVE, Suit.SPADES));
    checker = new OutcomeChecker(cards);

    boolean result = checker.hasFlush();

    assertThat(result, is(false));
  }

  @Test
  public void givenPositiveCardsHasFlushReturnsTrue() {
    cards.add(new Card(Rank.ACE, Suit.HEARTS));
    cards.add(new Card(Rank.TWO, Suit.HEARTS));
    cards.add(new Card(Rank.THREE, Suit.HEARTS));
    cards.add(new Card(Rank.FOUR, Suit.HEARTS));
    cards.add(new Card(Rank.FIVE, Suit.HEARTS));
    checker = new OutcomeChecker(cards);

    boolean result = checker.hasFlush();

    assertThat(result, is(true));
  }

}
