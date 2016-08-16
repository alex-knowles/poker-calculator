package com.skraylabs.poker.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PocketTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  Pocket fixedPocket;
  Card card1;
  Card card2;

  /**
   * Set up shared test fixture.
   * 
   * <p>
   * Use common Pocket object with predictable Card objects populated.
   *
   * @throws Exception shouldn't happen.
   */
  @Before
  public void setUp() throws Exception {
    card1 = new Card(Rank.ACE, Suit.Hearts);
    card2 = new Card(Rank.ACE, Suit.Diamonds);
    fixedPocket = new Pocket(card1, card2);
  }

  @Test
  public void testCopyConstructor_success() {
    // Exercise
    Pocket pocket = new Pocket(fixedPocket);
    // Verify
    assertThat(pocket, equalTo(fixedPocket));
  }

  @Test
  public void testCopyConstructor_null() {
    // Verify
    exception.expect(NullPointerException.class);
    // Exercise
    @SuppressWarnings("unused")
    Pocket pocket = new Pocket(null);
  }

  @Test
  public void testEquals_same() {
    // Set up
    Pocket equivalentPocket = new Pocket(card1, card2);
    // Exercise
    boolean equals = fixedPocket.equals(equivalentPocket);
    // Verify
    assertThat(equals, is(true));
  }

  @Test
  public void testEquals_null() {
    // Exercise
    boolean equals = fixedPocket.equals(null);
    // Verify
    assertThat(equals, is(false));
  }

  @Test
  public void testEquals_different1() {
    // Set up
    Pocket differentPocket = new Pocket();
    // Exercise
    boolean equals = fixedPocket.equals(differentPocket);
    // Verify
    assertThat(equals, is(false));
  }

  @Test
  public void testEquals_different2() {
    // Set up
    Pocket differentPocket =
        new Pocket(new Card(Rank.ACE, Suit.Spades), new Card(Rank.ACE, Suit.Clubs));
    // Exercise
    boolean equals = fixedPocket.equals(differentPocket);
    // Verify
    assertThat(equals, is(false));
  }

  @Test
  public void testEquals_different3() {
    // Set up
    Pocket differentPocket = new Pocket(card2, card1);
    // Exercise
    boolean equals = fixedPocket.equals(differentPocket);
    // Verify
    assertThat(equals, is(false));
  }

  @Test
  public void testCardsAreEqual_bothNull() {
    // Exercise
    boolean equal = Pocket.cardsAreEqual(null, null);
    // Verify
    assertThat(equal, is(true));
  }

  @Test
  public void testCardsAreEqual_firstNullSecondNonNull() {
    // Exercise
    boolean equal = Pocket.cardsAreEqual(null, card2);
    // Verify
    assertThat(equal, is(false));
  }

  @Test
  public void testCardsAreEqual_firstNonNullSecondNull() {
    // Exercise
    boolean equal = Pocket.cardsAreEqual(card1, null);
    // Verify
    assertThat(equal, is(false));
  }

  @Test
  public void testCardsAreEqual_equal() {
    // Exercise
    boolean equal = Pocket.cardsAreEqual(new Card(card1), new Card(card1));
    // Verify
    assertThat(equal, is(true));
  }

  @Test
  public void testCardsAreEqual_notEqual() {
    // Exercise
    boolean equal = Pocket.cardsAreEqual(card1, card2);
    // Verify
    assertThat(equal, is(false));
  }
}
