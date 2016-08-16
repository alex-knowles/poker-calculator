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

public class CardFactoryCardTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testInvalidInput_null() throws CardFormatException {
    // Verify
    exception.expect(CardFormatException.class);
    exception.expectMessage(CardFormatException.MSG_DEFAULT);
    // Exercise
    CardFactory.createCardFromString(null);
  }

  @Test
  public void testInvalidInput_empty() throws CardFormatException {
    // Verify
    String expectedMessage = String.format(CardFormatException.MSG_WITH_INVALID_STRING, "");
    exception.expect(CardFormatException.class);
    exception.expectMessage(expectedMessage);
    // Exercise
    CardFactory.createCardFromString("");
  }

  @Test
  public void testInvalidInput_blank() throws CardFormatException {
    // Verify
    String expectedMessage = String.format(CardFormatException.MSG_WITH_INVALID_STRING, " ");
    exception.expect(CardFormatException.class);
    exception.expectMessage(expectedMessage);
    // Exercise
    CardFactory.createCardFromString(" ");
  }

  @Test
  public void testInvalidInput_tooFewChars() throws CardFormatException {
    // Set up
    final String cardInput = "A";
    // Verify
    String expectedMessage = String.format(CardFormatException.MSG_WITH_INVALID_STRING, cardInput);
    exception.expect(CardFormatException.class);
    exception.expectMessage(expectedMessage);
    // Exercise
    CardFactory.createCardFromString(cardInput);
  }

  @Test
  public void testInvalidInput_tooManyChars() throws CardFormatException {
    // Set up
    final String cardInput = "Ask";
    // Verify
    String expectedMessage = String.format(CardFormatException.MSG_WITH_INVALID_STRING, cardInput);
    exception.expect(CardFormatException.class);
    exception.expectMessage(expectedMessage);
    // Exercise
    CardFactory.createCardFromString(cardInput);
  }

  @Test
  public void testInvalidInput_extraWhitespace() throws CardFormatException {
    // Set up
    final String cardInput = "  Fc ";
    // Verify
    String expectedMessage = String.format(CardFormatException.MSG_WITH_INVALID_STRING, "Fc");
    exception.expect(CardFormatException.class);
    exception.expectMessage(expectedMessage);
    // Exercise
    CardFactory.createCardFromString(cardInput);
  }

  @Test
  public void testInvalidInput_badRank1() throws CardFormatException {
    // Set up
    final String cardInput = "1s";
    // Verify
    String expectedMessage = String.format(CardFormatException.MSG_WITH_INVALID_STRING, cardInput);
    exception.expect(CardFormatException.class);
    exception.expectMessage(expectedMessage);
    // Exercise
    CardFactory.createCardFromString(cardInput);
  }

  @Test
  public void testInvalidInput_badRank2() throws CardFormatException {
    // Set up
    final String cardInput = "as";
    // Verify
    String expectedMessage = String.format(CardFormatException.MSG_WITH_INVALID_STRING, cardInput);
    exception.expect(CardFormatException.class);
    exception.expectMessage(expectedMessage);
    // Exercise
    CardFactory.createCardFromString(cardInput);
  }

  @Test
  public void testInvalidInput_badSuit1() throws CardFormatException {
    // Set up
    final String cardInput = "Kx";
    // Verify
    String expectedMessage = String.format(CardFormatException.MSG_WITH_INVALID_STRING, cardInput);
    exception.expect(CardFormatException.class);
    exception.expectMessage(expectedMessage);
    // Exercise
    CardFactory.createCardFromString(cardInput);
  }

  @Test
  public void testInvalidInput_badSuit2() throws CardFormatException {
    // Set up
    final String cardInput = "KC";
    // Verify
    String expectedMessage = String.format(CardFormatException.MSG_WITH_INVALID_STRING, cardInput);
    exception.expect(CardFormatException.class);
    exception.expectMessage(expectedMessage);
    // Exercise
    CardFactory.createCardFromString(cardInput);
  }

  @Test
  public void testValidInput1() {
    // Exercise
    Card card = null;
    try {
      card = CardFactory.createCardFromString("As");
    } catch (CardFormatException e) {
      fail("An InvalidCardFormatException was thrown for a valid Card input.");
    }
    // Verify
    assertCardRankAndSuit(card, Rank.ACE, Suit.Spades);
  }

  @Test
  public void testValidInput2() {
    // Exercise
    Card card = null;
    try {
      card = CardFactory.createCardFromString("8c");
    } catch (CardFormatException e) {
      fail("An InvalidCardFormatException was thrown for a valid Card input.");
    }
    // Verify
    assertCardRankAndSuit(card, Rank.EIGHT, Suit.Clubs);
  }

  @Test
  public void testValidInput_extraWhitespace() {
    // Exercise
    Card card = null;
    try {
      card = CardFactory.createCardFromString("   As ");
    } catch (CardFormatException e) {
      fail("An InvalidCardFormatException was thrown for a valid Card input.");
    }
    // Verify
    assertCardRankAndSuit(card, Rank.ACE, Suit.Spades);
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
