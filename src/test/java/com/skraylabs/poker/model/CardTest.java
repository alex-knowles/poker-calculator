package com.skraylabs.poker.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CardTest {

  @Rule public ExpectedException exception = ExpectedException.none();

  /**
   * Test fixture card.
   */
  Card fixedCard;

  /**
   * Test fixture rank.
   */
  final Rank fixedRank = Rank.Ace;

  /**
   * Test fixture suit.
   */
  Suit fixedSuit = Suit.Clubs;

  @Before
  public void setUp() throws Exception {
    fixedCard = new Card(fixedRank, fixedSuit);
  }

  @Test
  public void testGetRank() {
    // Exercise
    Rank rank = fixedCard.getRank();
    // Verify
    assertThat(rank, equalTo(fixedRank));
  }

  @Test
  public void testGetSuit() {
    // Exercise
    Suit suit = fixedCard.getSuit();
    // Verify
    assertThat(suit, equalTo(fixedSuit));
  }

  @Test
  public void testCopyConstructor_null() {
    // Verify
    exception.expect(NullPointerException.class);
    // Exercise
    @SuppressWarnings("unused")
    Card card = new Card(null);
  }

  @Test
  public void testCopyConstructor_success() {
    // Exercise
    Card card = new Card(fixedCard);
    // Verify
    assertThat(card, equalTo(fixedCard));
  }

  @Test
  public void testEquals_differentSuit() {
    // Set up
    Card differentCard = new Card(fixedRank, Suit.Diamonds);
    // Exercise
    final boolean equals = fixedCard.equals(differentCard);
    // Verify
    assertThat(equals, is(false));
  }

  @Test
  public void testEquals_differentRank() {
    // Set up
    Card differentCard = new Card(Rank.Four, fixedSuit);
    // Exercise
    final boolean equals = fixedCard.equals(differentCard);
    // Verify
    assertThat(equals, is(false));
  }

  @Test
  public void testEquals_differentRankAndDifferentSuit() {
    // Set up
    Card differentCard = new Card(Rank.Queen, Suit.Hearts);
    // Exercise
    final boolean equals = fixedCard.equals(differentCard);
    // Verify
    assertThat(equals, is(false));
  }

  @Test
  public void testEquals_notACard() {
    // Set up
    Object differentObject = "Ace of Clubs";
    // Exercise
    final boolean equals = fixedCard.equals(differentObject);
    // Verify
    assertThat(equals, is(false));
  }

  @Test
  public void testEquals_sameRankAndSameSuit() {
    // Set up
    Card equivalentCard = new Card(fixedRank, fixedSuit);
    // Exercise
    final boolean equals = fixedCard.equals(equivalentCard);
    // Verify
    assertThat(equals, is(true));
  }

  @Test
  public void testHashCode_equalsEquals() {
    // Set up
    Card equivalentCard = new Card(fixedRank, fixedSuit);
    assertThat(fixedCard, equalTo(equivalentCard)); // guard assertion
    // Exercise
    int hashCode1 = fixedCard.hashCode();
    int hashCode2 = equivalentCard.hashCode();
    // Verify
    assertThat(hashCode1, equalTo(hashCode2));
  }
}
