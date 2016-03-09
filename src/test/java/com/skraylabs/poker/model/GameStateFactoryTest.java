package com.skraylabs.poker.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class GameStateFactoryTest {
  
  @Rule public ExpectedException exception = ExpectedException.none();

  /**
   * Formatted string for a valid Board with 3 Card values. Consists of card #s 49, 50, and 51 --
   * see {@link #cardFromNumber(int)}.
   */
  String validBoard3Cards;

  /**
   * Set up shared text fixture.
   *
   * @throws Exception shouldn't happen.
   */
  @Before
  public void setUp() throws Exception {
    Card card49 = cardFromNumber(49);
    Card card50 = cardFromNumber(50);
    Card card51 = cardFromNumber(51);
    String input1 = CardFactory.createStringFromCard(card49);
    String input2 = CardFactory.createStringFromCard(card50);
    String input3 = CardFactory.createStringFromCard(card51);
    validBoard3Cards = String.format("%s %s %s", input1, input2, input3);
  }

  @Test
  public void testInvalidInput_null() throws PokerFormatException {
    // Verify
    exception.expect(GameStateFormatException.class);
    exception.expectMessage(GameStateFormatException.MSG_NULL_INPUT);
    // Exercise
    GameStateFactory.createGameStateFromString(null);
  }

  @Test
  public void testInvalidInput_empty() throws PokerFormatException {
    // Verify
    exception.expect(GameStateFormatException.class);
    exception.expectMessage(GameStateFormatException.MSG_MIN_POCKET_NUM);
    // Exercise
    GameStateFactory.createGameStateFromString("");
  }

  @Test
  public void testInvalidInput_blank() throws PokerFormatException {
    // Verify
    exception.expect(GameStateFormatException.class);
    exception.expectMessage(GameStateFormatException.MSG_MIN_POCKET_NUM);
    // Exercise
    GameStateFactory.createGameStateFromString(" ");
  }

  @Test
  public void testInvalidInput_noPockets() throws PokerFormatException {
    // Setup
    String input = String.format("%s%n", validBoard3Cards);
    // Verify
    exception.expect(GameStateFormatException.class);
    exception.expectMessage(GameStateFormatException.MSG_MIN_POCKET_NUM);
    // Exercise
    GameStateFactory.createGameStateFromString(input);
  }

  @Test
  public void testInvalidInput_tooManyPockets() throws PokerFormatException {
    // Setup
    // Create a game state with 11 pockets
    StringBuilder builder = new StringBuilder();
    builder.append(String.format("%s%n", validBoard3Cards));
    for (int i = 0; i < 11; ++i) {
      Card card1 = cardFromNumber(i * 2);
      Card card2 = cardFromNumber(i * 2 + 1 );
      String cardInput1 = CardFactory.createStringFromCard(card1);
      String cardInput2 = CardFactory.createStringFromCard(card2);
      builder.append(String.format("%s %s%n", cardInput1, cardInput2));
    }
    String input = builder.toString();
    // Verify
    exception.expect(GameStateFormatException.class);
    exception.expectMessage(GameStateFormatException.MSG_MAX_POCKET_NUM);
    // Exercise
    GameStateFactory.createGameStateFromString(input);
  }

  @Test
  public void testInvalidInput_duplicateCards() throws PokerFormatException {
    // Setup
    String input = "2c 5h 7s Qc%n";
    input += "8s 8h%n";
    input += "%n";
    input += "As 2c";
    String duplicate = "2c";
    // Verify
    String expectedMessage = String.format(GameStateFormatException.MSG_DUPLICATE_CARD, duplicate);
    exception.expect(GameStateFormatException.class);
    exception.expectMessage(expectedMessage);
    // Exercise
    GameStateFactory.createGameStateFromString(input);
  }

  @Test
  public void testInvalidInput_board() throws PokerFormatException {
    // Set up
    String input = "5h 7s";
    // Verify
    exception.expect(BoardFormatException.class);
    // Exercise
    GameStateFactory.createGameStateFromString(input);
  }

  @Test
  public void testInvalidInput_pocket() throws PokerFormatException {
    // Set up
    String input = "5h 7s Th%n";
    input += "As";
    // Verify
    exception.expect(PocketFormatException.class);
    // Exercise
    GameStateFactory.createGameStateFromString(input);
  }

  /**
   * Test helper that returns a card based on a number in the range [0, 51].
   *
   * @param number integer in range [0, 51]
   * @return a Card
   */
  static Card cardFromNumber(int number) {
    if (number < 0 || number > 51) {
      throw new IllegalArgumentException("Parameter \"number\" must be in range [0, 51]");
    }
    Card result  = new Card(null, null);
    // Assign suit
    switch (number / 13) {
      case 0:
        result.suit = Suit.Spades;
        break;
      case 1:
        result.suit = Suit.Hearts;
        break;
      case 2:
        result.suit = Suit.Diamonds;
        break;
      case 3:
        result.suit = Suit.Clubs;
        break;
      default:
        throw new RuntimeException("Logic error!");
    }
    // Assign rank
    switch (number % 13) {
      case 0:
        result.rank = Rank.Ace;
        break;
      case 1:
        result.rank = Rank.King;
        break;
      case 2:
        result.rank = Rank.Queen;
        break;
      case 3:
        result.rank = Rank.Jack;
        break;
      case 4:
        result.rank = Rank.Ten;
        break;
      case 5:
        result.rank = Rank.Nine;
        break;
      case 6:
        result.rank = Rank.Eight;
        break;
      case 7:
        result.rank = Rank.Seven;
        break;
      case 8:
        result.rank = Rank.Six;
        break;
      case 9:
        result.rank = Rank.Five;
        break;
      case 10:
        result.rank = Rank.Four;
        break;
      case 11:
        result.rank = Rank.Three;
        break;
      case 12:
        result.rank = Rank.Two;
        break;
      default:
        throw new RuntimeException("Logic error!");
    }
    return result;
  }

  @Test
  public void testCardFromNumber_tooLow() {
    // Verify
    exception.expect(IllegalArgumentException.class);
    // Exercise
    cardFromNumber(-1);
  }

  @Test
  public void testCardFromNumber_tooHigh() {
    // Verify
    exception.expect(IllegalArgumentException.class);
    // Exercise
    cardFromNumber(52);
  }

  @Test
  public void testCardFromNumber_noDuplicates() {
    // Exercise
    Card[] cards = new Card[52];
    for (int i = 0; i < 52; ++i) {
      cards[i] = cardFromNumber(i);
    }
    // Verify
    for (int i = 0; i < 52; ++i) {
      Card card = cards[i];
      assertThat(card.rank, notNullValue());
      assertThat(card.suit, notNullValue());
      int numCardsLikeMe = 0;
      for (int j = 0; j < 52; ++j) {
        if (card.equals(cards[j])) {
          numCardsLikeMe++;
        }
      }
      assertThat(numCardsLikeMe, is(1));
    }
  }
}
