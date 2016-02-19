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
    assertThat(card, notNullValue());
    assertThat(card.rank, equalTo(Rank.Ace));
    assertThat(card.suit, equalTo(Suit.Spades));
  }

}
