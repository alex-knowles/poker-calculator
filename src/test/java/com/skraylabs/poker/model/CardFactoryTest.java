package com.skraylabs.poker.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CardFactoryTest {

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testValidInput() {
    // Exercise
    Card card = null;
    try {
      card = CardFactory.createCardFromString("As");
    } catch (InvalidCardFormatException e) {
      fail("An InvalidCardFormatException was thrown for a valid Card input.");
    }
    // Verify
    assertCardRankAndSuit(card, Rank.Ace, Suit.Spades);
  }

  /**
   * Test utility to assert 3 properties of a given {@link Card}.
   * <ul>
   * <li>is not null
   * <li>has an expected rank
   * <li>has an expected suit
   * </ul>
   *
   * @param card system under test
   * @param rank expected rank of card
   * @param suit expected suit of card
   */
  static void assertCardRankAndSuit(Card card, Rank rank, Suit suit) {
    assertThat(card, notNullValue());
    assertThat(card.rank, equalTo(rank));
    assertThat(card.suit, equalTo(suit));
  }

}
