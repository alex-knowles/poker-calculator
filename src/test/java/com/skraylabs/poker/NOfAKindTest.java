package com.skraylabs.poker;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.skraylabs.poker.model.Card;
import com.skraylabs.poker.model.Rank;
import com.skraylabs.poker.model.Suit;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Collection;

public class NOfAKindTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  Collection<Card> zeroCards;
  Collection<Card> twoAces;

  /**
   * Initializes test fixtures to be used by test methods.
   */
  @Before
  public void initCardCollections() {
    zeroCards = new ArrayList<Card>();

    twoAces = new ArrayList<Card>();
    twoAces.add(new Card(Rank.ACE, Suit.Spades));
    twoAces.add(new Card(Rank.ACE, Suit.Hearts));
  }

  @Test
  public void negativeNumberCausesException() {
    exception.expect(IllegalArgumentException.class);

    ProbabilityCalculator.hasNOfAKind(zeroCards, -1);
  }

  @Test
  public void zeroNumberCausesException() {
    exception.expect(IllegalArgumentException.class);

    ProbabilityCalculator.hasNOfAKind(zeroCards, 0);
  }

  @Test
  public void nullCardsCausesException() {
    exception.expect(IllegalArgumentException.class);

    ProbabilityCalculator.hasNOfAKind(null, 1);
  }

  @Test
  public void givenNoCardsReturnsFalse() {
    boolean result = ProbabilityCalculator.hasNOfAKind(zeroCards, 1);

    assertThat(result, is(false));
  }

  @Test
  public void givenLessThanNOfAKindReturnsFalse() {
    Collection<Card> cards = new ArrayList<Card>();
    cards.add(new Card(Rank.ACE, Suit.Spades));
    cards.add(new Card(Rank.ACE, Suit.Hearts));

    boolean result = ProbabilityCalculator.hasNOfAKind(twoAces, 3);

    assertThat(result, is(false));
  }

  @Test
  public void givenExactlyNCardsReturnsTrue() {
    Collection<Card> cards = new ArrayList<Card>();
    cards.add(new Card(Rank.ACE, Suit.Spades));
    cards.add(new Card(Rank.ACE, Suit.Hearts));

    boolean result = ProbabilityCalculator.hasNOfAKind(twoAces, 2);

    assertThat(result, is(true));
  }

  @Test
  public void givenMoreThanNCardsReturnsTrue() {
    boolean result = ProbabilityCalculator.hasNOfAKind(twoAces, 1);

    assertThat(result, is(true));
  }
}
