package com.skraylabs.poker;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.skraylabs.poker.model.Card;
import com.skraylabs.poker.model.Rank;
import com.skraylabs.poker.model.Suit;

import org.junit.Test;

import java.util.ArrayList;

public class RoyalFlushTest {

  @Test
  public void givenNotARoyalFlushReturnsFalse() {
    ArrayList<Card> cards = new ArrayList<Card>();
    cards.add(new Card(Rank.Ace, Suit.Clubs));
    cards.add(new Card(Rank.King, Suit.Clubs));
    cards.add(new Card(Rank.Queen, Suit.Clubs));
    cards.add(new Card(Rank.Jack, Suit.Clubs));
    cards.add(new Card(Rank.Two, Suit.Clubs));

    boolean result = ProbabilityCalculator.hasRoyalFlush(cards);

    assertThat(result, is(false));
  }

  @Test
  public void givenARoyalFlushReturnsTrue() {
    ArrayList<Card> cards = new ArrayList<Card>();
    cards.add(new Card(Rank.Ace, Suit.Clubs));
    cards.add(new Card(Rank.King, Suit.Clubs));
    cards.add(new Card(Rank.Queen, Suit.Clubs));
    cards.add(new Card(Rank.Jack, Suit.Clubs));
    cards.add(new Card(Rank.Ten, Suit.Clubs));

    boolean result = ProbabilityCalculator.hasRoyalFlush(cards);

    assertThat(result, is(true));
  }

}
