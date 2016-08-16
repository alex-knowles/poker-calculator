package com.skraylabs.poker.model;

import org.apache.commons.lang3.StringUtils;

/**
 * Factory class for constructing {@link Card} objects from String values.
 */
public class CardFactory {

  /**
   * Creates a {@link Card} object given a two-character string (e.g. "2c" for the Two of Clubs).
   *
   * <p>
   * First character represents rank:
   * <ul>
   * <li>A (Ace)
   * <li>K (King)
   * <li>Q (Queen)
   * <li>J (Jack)
   * <li>T (Ten)
   * <li>9 (Nine)
   * <li>8 (Eight)
   * <li>7 (Seven)
   * <li>6 (Six)
   * <li>5 (Five)
   * <li>4 (Four)
   * <li>3 (Three)
   * <li>2 (Two)
   * </ul>
   *
   * <p>
   * Second character represents suit (must be lower-case):
   * <ul>
   * <li>s (Spades)
   * <li>h (Hearts)
   * <li>d (Diamonds)
   * <li>c (Clubs)
   * </ul>
   *
   * @param card two-character string representation of a card
   * @return a new {@link Card}
   * @throws CardFormatException if {@code cardString} is formatted incorrectly.
   */
  public static Card createCardFromString(String card) throws CardFormatException {
    // Sanity checks
    if (card == null) {
      throw new CardFormatException();
    } else if (StringUtils.isBlank(card)) {
      throw new CardFormatException(card);
    }
    final String cardStripped = StringUtils.strip(card);
    if (cardStripped.length() != 2) {
      throw new CardFormatException(cardStripped);
    }

    // It is safe to assume cardStripped is exactly 2 chars long
    // Attempt to process rank and suit
    Rank rank;
    try {
      rank = parseRank(cardStripped.charAt(0));
    } catch (CardFormatException e) {
      // Re-throw with 2-char string
      throw new CardFormatException(cardStripped);
    }
    Suit suit;
    try {
      suit = parseSuit(cardStripped.charAt(1));
    } catch (CardFormatException e) {
      // Re-throw with 2-char string
      throw new CardFormatException(cardStripped);
    }
    return new Card(rank, suit);
  }

  /**
   * Serializes a {@link Card} into a String representation.
   *
   * @param card non-null instance to serialize with non-null {@link Rank} and {@link Suit} values
   * @return a String representation of {@code card}
   */
  public static String createStringFromCard(Card card) {
    if (card == null || card.rank == null || card.suit == null) {
      throw new IllegalArgumentException();
    }
    // Assign rank
    char rank = toChar(card.rank);
    // Assign suit
    char suit = toChar(card.suit);
    return String.format("%s%s", rank, suit);
  }

  /**
   * Map a single character to a playing card rank.
   *
   * @param rank playing card rank
   * @return {@link Rank} matching {@code rank}
   * @throws CardFormatException if {@code rank} is not one of the 13 expected characters
   */
  static Rank parseRank(char rank) throws CardFormatException {
    switch (rank) {
      case 'A':
        return Rank.ACE;
      case 'K':
        return Rank.KING;
      case 'Q':
        return Rank.QUEEN;
      case 'J':
        return Rank.JACK;
      case 'T':
        return Rank.TEN;
      case '9':
        return Rank.NINE;
      case '8':
        return Rank.EIGHT;
      case '7':
        return Rank.SEVEN;
      case '6':
        return Rank.SIX;
      case '5':
        return Rank.FIVE;
      case '4':
        return Rank.FOUR;
      case '3':
        return Rank.THREE;
      case '2':
        return Rank.TWO;
      default:
        throw new CardFormatException();
    }
  }

  /**
   * Map a single character to a playing card suit.
   *
   * @param suit playing card suit
   * @return {@link Suit} matching {@code suit}
   * @throws CardFormatException if {@code suit} is not one of the 4 expected characters
   */
  static Suit parseSuit(char suit) throws CardFormatException {
    switch (suit) {
      case 's':
        return Suit.Spades;
      case 'h':
        return Suit.Hearts;
      case 'd':
        return Suit.Diamonds;
      case 'c':
        return Suit.Clubs;
      default:
        throw new CardFormatException();
    }
  }

  /**
   * Represent a {@link Rank} value as a single character.
   *
   * @param rank instance to convert
   * @return a single character based on {@code rank}
   */
  static char toChar(Rank rank) {
    if (rank == null) {
      throw new IllegalArgumentException("Argument must be non-null");
    }
    char result = '?';
    switch (rank) {
      case ACE:
        result = 'A';
        break;
      case KING:
        result = 'K';
        break;
      case QUEEN:
        result = 'Q';
        break;
      case JACK:
        result = 'J';
        break;
      case TEN:
        result = 'T';
        break;
      case NINE:
        result = '9';
        break;
      case EIGHT:
        result = '8';
        break;
      case SEVEN:
        result = '7';
        break;
      case SIX:
        result = '6';
        break;
      case FIVE:
        result = '5';
        break;
      case FOUR:
        result = '4';
        break;
      case THREE:
        result = '3';
        break;
      case TWO:
        result = '2';
        break;
      default:
        throw new RuntimeException("Logic error");
    }
    return result;
  }


  /**
   * Represent a {@link Suit} value as a single character.
   *
   * @param suit instance to convert
   * @return a single character based on {@code suit}
   */
  static char toChar(Suit suit) {
    if (suit == null) {
      throw new IllegalArgumentException("Argument must be non-null");
    }
    char result = '?';
    switch (suit) {
      case Spades:
        result = 's';
        break;
      case Hearts:
        result = 'h';
        break;
      case Diamonds:
        result = 'd';
        break;
      case Clubs:
        result = 'c';
        break;
      default:
        throw new RuntimeException("Logic error");
    }
    return result;
  }
}
