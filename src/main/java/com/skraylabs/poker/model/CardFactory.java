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
   * <li> A (Ace)
   * <li> K (King)
   * <li> Q (Queen)
   * <li> J (Jack)
   * <li> T (Ten)
   * <li> 9 (Nine)
   * <li> 8 (Eight)
   * <li> 7 (Seven)
   * <li> 6 (Six)
   * <li> 5 (Five)
   * <li> 4 (Four)
   * <li> 3 (Three)
   * <li> 2 (Two)
   * </ul>
   *
   * <p>
   * Second character represents suit (must be lower-case):
   * <ul>
   * <li> s (Spades)
   * <li> h (Hearts)
   * <li> d (Diamonds)
   * <li> c (Clubs)
   * </ul>
   *
   * @param card two-character string representation of a card
   * @return a new {@link Card}
   * @throws InvalidCardFormatException if {@code cardString} is formatted incorrectly.
   */
  public static Card createCardFromString(String card) throws InvalidCardFormatException {
    // Sanity checks
    if (card == null) {
      throw new InvalidCardFormatException();
    } else if (StringUtils.isBlank(card)) {
      throw new InvalidCardFormatException(card);
    }
    final String cardStripped = StringUtils.strip(card);
    if (cardStripped.length() != 2) {
      throw new InvalidCardFormatException(cardStripped);
    }

    // It is safe to assume cardStripped is exactly 2 chars long
    // Attempt to process rank and suit
    Rank rank;
    try {
      rank = parseRank(cardStripped.charAt(0));
    } catch (InvalidCardFormatException e) {
      // Re-throw with 2-char string
      throw new InvalidCardFormatException(cardStripped);
    }
    Suit suit;
    try {
      suit = parseSuit(cardStripped.charAt(1));
    } catch (InvalidCardFormatException e) {
      // Re-throw with 2-char string
      throw new InvalidCardFormatException(cardStripped);
    }
    return new Card(rank, suit);
  }

  /**
   * Map a single character to a playing card rank.
   *
   * @param rank playing card rank
   * @return {@link Rank} matching {@code rank}
   * @throws InvalidCardFormatException if {@code rank} is not one of the 13 expected characters
   */
  static Rank parseRank(char rank) throws InvalidCardFormatException {
    switch (rank) {
      case 'A': return Rank.Ace;
      case 'K': return Rank.King;
      case 'Q': return Rank.Queen;
      case 'J': return Rank.Jack;
      case 'T': return Rank.Ten;
      case '9': return Rank.Nine;
      case '8': return Rank.Eight;
      case '7': return Rank.Seven;
      case '6': return Rank.Six;
      case '5': return Rank.Five;
      case '4': return Rank.Four;
      case '3': return Rank.Three;
      case '2': return Rank.Two;
      default:
        throw new InvalidCardFormatException();
    }
  }

  /**
   * Map a single character to a playing card suit.
   *
   * @param suit playing card suit
   * @return {@link Suit} matching {@code suit}
   * @throws InvalidCardFormatException if {@code suit} is not one of the 4 expected characters
   */
  static Suit parseSuit(char suit) throws InvalidCardFormatException {
    switch (suit) {
      case 's': return Suit.Spades;
      case 'h': return Suit.Hearts;
      case 'd': return Suit.Diamonds;
      case 'c': return Suit.Clubs;
      default:
        throw new InvalidCardFormatException();
    }
  }
}
