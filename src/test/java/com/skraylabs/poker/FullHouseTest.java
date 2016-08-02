package com.skraylabs.poker;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.skraylabs.poker.model.Card;
import com.skraylabs.poker.model.Rank;
import com.skraylabs.poker.model.Suit;

import org.junit.Test;

import java.util.ArrayList;

public class FullHouseTest {

  @Test
  public void givenLessThanFiveCardsReturnsFalse() {
    ArrayList<Card> cards = new ArrayList<Card>();
    cards.add(new Card(Rank.Ace, Suit.Clubs));
    cards.add(new Card(Rank.Two, Suit.Clubs));
    cards.add(new Card(Rank.Three, Suit.Clubs));

    boolean result = ProbabilityCalculator.hasFullHouse(cards);

    assertThat(result, is(false));
  }

  @Test
  public void givenNotAFullHouseReturnsFalse() {
    ArrayList<Card> cards = new ArrayList<Card>();
    cards.add(new Card(Rank.Ace, Suit.Clubs));
    cards.add(new Card(Rank.Ace, Suit.Spades));
    cards.add(new Card(Rank.Three, Suit.Clubs));
    cards.add(new Card(Rank.Three, Suit.Spades));
    cards.add(new Card(Rank.Ten, Suit.Spades));
    cards.add(new Card(Rank.Jack, Suit.Spades));
    cards.add(new Card(Rank.King, Suit.Spades));

    boolean result = ProbabilityCalculator.hasFullHouse(cards);

    assertThat(result, is(false));
  }

  @Test
  public void givenAFullHouseReturnsTrue() {
    ArrayList<Card> cards = new ArrayList<Card>();
    cards.add(new Card(Rank.Ace, Suit.Clubs));
    cards.add(new Card(Rank.Ace, Suit.Spades));
    cards.add(new Card(Rank.Three, Suit.Clubs));
    cards.add(new Card(Rank.Three, Suit.Spades));
    cards.add(new Card(Rank.Ten, Suit.Spades));
    cards.add(new Card(Rank.Jack, Suit.Spades));
    cards.add(new Card(Rank.Ace, Suit.Hearts));

    boolean result = ProbabilityCalculator.hasFullHouse(cards);

    assertThat(result, is(true));
  }

}
