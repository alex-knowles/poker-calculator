package com.skraylabs.poker.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CardFactoryTest {

  @Rule public ExpectedException exception = ExpectedException.none();

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

  @Test
  public void testInvalidInput_null() throws InvalidCardFormatException {
    // Verify
    exception.expect(InvalidCardFormatException.class);
    exception.expectMessage(InvalidCardFormatException.MSG_DEFAULT);
    // Exercise
    CardFactory.createCardFromString(null);
  }

  @Test
  public void testInvalidInput_empty() throws InvalidCardFormatException {
    // Verify
    String expectedMessage = String.format(InvalidCardFormatException.MSG_WITH_INVALID_STRING, "");
    exception.expect(InvalidCardFormatException.class);
    exception.expectMessage(expectedMessage);
    // Exercise
    CardFactory.createCardFromString("");
  }

  @Test
  public void testInvalidInput_blank() throws InvalidCardFormatException {
    // Verify
    String expectedMessage = String.format(InvalidCardFormatException.MSG_WITH_INVALID_STRING, " ");
    exception.expect(InvalidCardFormatException.class);
    exception.expectMessage(expectedMessage);
    // Exercise
    CardFactory.createCardFromString(" ");
  }

  @Test
  public void testInvalidInput_tooFewChars() throws InvalidCardFormatException {
    // Set up
    final String cardInput = "A";
    // Verify
    String expectedMessage =
        String.format(InvalidCardFormatException.MSG_WITH_INVALID_STRING, cardInput);
    exception.expect(InvalidCardFormatException.class);
    exception.expectMessage(expectedMessage);
    // Exercise
    CardFactory.createCardFromString(cardInput);
  }

  @Test
  public void testInvalidInput_tooManyChars() throws InvalidCardFormatException {
    // Set up
    final String cardInput = "Ask";
    // Verify
    String expectedMessage =
        String.format(InvalidCardFormatException.MSG_WITH_INVALID_STRING, cardInput);
    exception.expect(InvalidCardFormatException.class);
    exception.expectMessage(expectedMessage);
    // Exercise
    CardFactory.createCardFromString(cardInput);
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
