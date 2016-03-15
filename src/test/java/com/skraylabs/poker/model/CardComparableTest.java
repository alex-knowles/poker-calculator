package com.skraylabs.poker.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CardComparableTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  Card aceOfSpades;
  Card aceOfClubs;
  Card kingOfSpades;
  Card twoOfSpades;
  Card anotherAceOfSpades;
  Card threeOfClubs;
  Card threeOfHearts;
  Card threeOfDiamonds;

  int result;

  /**
   * Set up shared test fixture.
   *
   * @throws Exception shouldn't happen
   */
  @Before
  public void setUp() throws Exception {
    aceOfSpades = new Card(Rank.Ace, Suit.Spades);
    aceOfClubs = new Card(Rank.Ace, Suit.Clubs);
    kingOfSpades = new Card(Rank.King, Suit.Spades);
    twoOfSpades = new Card(Rank.Two, Suit.Spades);
    anotherAceOfSpades = new Card(aceOfSpades);
    threeOfClubs = new Card(Rank.Three, Suit.Clubs);
    threeOfHearts = new Card(Rank.Three, Suit.Hearts);
    threeOfDiamonds = new Card(Rank.Three, Suit.Diamonds);
  }

  @Test
  public void testNull() {
    // Verify
    exception.expect(NullPointerException.class);
    // Exercise
    aceOfSpades.compareTo(null);
  }

  @Test
  public void testAceGreaterThanTwo() {
    // Exercise
    result = aceOfSpades.compareTo(twoOfSpades); 
    // Verify
    assertTrue(result > 0);
  }

  @Test
  public void testTwoLessThanAce() {
    // Exercise
    result = twoOfSpades.compareTo(aceOfSpades);
    // Verify
    assertTrue(result < 0);
  }

  @Test
  public void testAceGreaterThanKing() {
    // Exercise
    result = aceOfSpades.compareTo(kingOfSpades); 
    // Verify
    assertTrue(result > 0);
  }

  @Test
  public void testKingLessThanAce() {
    // Exercise
    result = kingOfSpades.compareTo(aceOfSpades); 
    // Verify
    assertTrue(result < 0);
  }

  @Test
  public void testSpadeGreaterThanClub() {
    // Exercise
    result = aceOfSpades.compareTo(aceOfClubs); 
    // Verify
    assertTrue(result > 0);
  }

  @Test
  public void testClubLessThanSpade() {
    // Exercise
    result = aceOfClubs.compareTo(aceOfSpades); 
    // Verify
    assertTrue(result < 0);
  }

  @Test
  public void testEqualIsEqual() {
    // Guard assertion
    assertThat(aceOfSpades, equalTo(anotherAceOfSpades));
    // Exercise
    result = aceOfSpades.compareTo(anotherAceOfSpades);
    // Verify
    assertThat(result, is(0));
  }

  @Test
  public void testTransitive() {
    // Exercise
    int fooGreaterThanBar = threeOfHearts.compareTo(threeOfDiamonds);
    int barGreaterThanBaz = threeOfDiamonds.compareTo(threeOfClubs);
    int fooGreaterThanBaz = threeOfHearts.compareTo(threeOfClubs);
    // Verify
    assertTrue(fooGreaterThanBar > 0);
    assertTrue(barGreaterThanBaz > 0);
    assertTrue(fooGreaterThanBaz > 0);
  }
}
