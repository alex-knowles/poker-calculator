package com.skraylabs.poker;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.skraylabs.poker.model.Card;
import com.skraylabs.poker.model.Rank;
import com.skraylabs.poker.model.Suit;

import org.junit.Test;

import java.util.ArrayList;

public class StraightFlushTest {

  @Test
  public void givenLessThanFiveCardsReturnsFalse() {
    ArrayList<Card> cards = new ArrayList<Card>();
    cards.add(new Card(Rank.Ace, Suit.Clubs));
    cards.add(new Card(Rank.Two, Suit.Clubs));
    cards.add(new Card(Rank.Three, Suit.Clubs));

    boolean result = ProbabilityCalculator.hasStraightFlush(cards);

    assertThat(result, is(false));
  }

  @Test
  public void givenAFlushReturnsFalse() {
    ArrayList<Card> cards = new ArrayList<Card>();
    cards.add(new Card(Rank.Ace, Suit.Clubs));
    cards.add(new Card(Rank.Two, Suit.Clubs));
    cards.add(new Card(Rank.Three, Suit.Clubs));
    cards.add(new Card(Rank.Four, Suit.Clubs));
    cards.add(new Card(Rank.King, Suit.Clubs));

    boolean result = ProbabilityCalculator.hasStraightFlush(cards);

    assertThat(result, is(false));
  }

  @Test
  public void givenAStraightReturnsFalse() {
    ArrayList<Card> cards = new ArrayList<Card>();
    cards.add(new Card(Rank.Ace, Suit.Clubs));
    cards.add(new Card(Rank.Two, Suit.Clubs));
    cards.add(new Card(Rank.Three, Suit.Clubs));
    cards.add(new Card(Rank.Four, Suit.Clubs));
    cards.add(new Card(Rank.Five, Suit.Hearts));

    boolean result = ProbabilityCalculator.hasStraightFlush(cards);

    assertThat(result, is(false));
  }

  @Test
  public void givenAStraightFlushReturnsTrue() {
    ArrayList<Card> cards = new ArrayList<Card>();
    cards.add(new Card(Rank.Ace, Suit.Clubs));
    cards.add(new Card(Rank.Two, Suit.Clubs));
    cards.add(new Card(Rank.Three, Suit.Clubs));
    cards.add(new Card(Rank.Four, Suit.Clubs));
    cards.add(new Card(Rank.Five, Suit.Clubs));

    boolean result = ProbabilityCalculator.hasStraightFlush(cards);

    assertThat(result, is(true));
  }
}
