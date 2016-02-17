package com.skraylabs.poker.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class CardTest {

  @Test
  public void testGetRank() {
    // Set up
    Card card = new Card(Rank.Ace, Suit.Clubs);
    // Exercise
    Rank rank = card.getRank();
    // Verify
    Rank expectedRank = Rank.Ace;
    assertThat(rank, equalTo(expectedRank));
  }

  @Test
  public void testGetSuit() {
    // Set up
    Card card = new Card(Rank.Eight, Suit.Diamonds);
    // Exercise
    Suit suit = card.getSuit();
    // Verify
    Suit expectedSuit = Suit.Diamonds;
    assertThat(suit, equalTo(expectedSuit));
  }
  
}
