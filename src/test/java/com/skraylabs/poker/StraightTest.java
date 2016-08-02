package com.skraylabs.poker;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.skraylabs.poker.model.Card;
import com.skraylabs.poker.model.Rank;
import com.skraylabs.poker.model.Suit;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class StraightTest {

  @Before
  public void setUp() throws Exception {}

  @Test
  public void givenLessThanFiveCardsReturnsFalse() {
    ArrayList<Card> cards = new ArrayList<Card>();
    cards.add(new Card(Rank.Ace, Suit.Clubs));
    cards.add(new Card(Rank.Two, Suit.Clubs));
    cards.add(new Card(Rank.Three, Suit.Clubs));

    boolean result = ProbabilityCalculator.hasStraight(cards);

    assertThat(result, is(false));
  }

  @Test
  public void givenNotAStraightReturnsFalse() {
    ArrayList<Card> cards = new ArrayList<Card>();
    cards.add(new Card(Rank.Ace, Suit.Clubs));
    cards.add(new Card(Rank.Two, Suit.Clubs));
    cards.add(new Card(Rank.Three, Suit.Clubs));
    cards.add(new Card(Rank.Four, Suit.Clubs));
    cards.add(new Card(Rank.King, Suit.Clubs));

    boolean result = ProbabilityCalculator.hasStraight(cards);

    assertThat(result, is(false));
  }

  @Test
  public void givenAStraightReturnsTrue() {
    ArrayList<Card> cards = new ArrayList<Card>();
    cards.add(new Card(Rank.Ace, Suit.Clubs));
    cards.add(new Card(Rank.Two, Suit.Clubs));
    cards.add(new Card(Rank.Three, Suit.Clubs));
    cards.add(new Card(Rank.Four, Suit.Clubs));
    cards.add(new Card(Rank.Five, Suit.Clubs));

    boolean result = ProbabilityCalculator.hasStraight(cards);

    assertThat(result, is(true));
  }

}
