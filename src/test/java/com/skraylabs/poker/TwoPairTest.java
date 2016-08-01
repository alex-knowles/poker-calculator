package com.skraylabs.poker;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.skraylabs.poker.model.Card;
import com.skraylabs.poker.model.Rank;
import com.skraylabs.poker.model.Suit;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

public class TwoPairTest {

  @Test
  public void givenNotATwoPairReturnsFalse() {
    Collection<Card> cards = new ArrayList<Card>();
    cards.add(new Card(Rank.Ace, Suit.Hearts));
    cards.add(new Card(Rank.Ace, Suit.Spades));
    cards.add(new Card(Rank.King, Suit.Hearts));

    boolean result = ProbabilityCalculator.hasTwoPair(cards);

    assertThat(result, is(false));
  }

  @Test
  public void givenNotATwoPairReturnsTrue() {
    Collection<Card> cards = new ArrayList<Card>();
    cards.add(new Card(Rank.Ace, Suit.Hearts));
    cards.add(new Card(Rank.Ace, Suit.Spades));
    cards.add(new Card(Rank.King, Suit.Clubs));
    cards.add(new Card(Rank.King, Suit.Diamonds));

    boolean result = ProbabilityCalculator.hasTwoPair(cards);

    assertThat(result, is(true));
  }
}
