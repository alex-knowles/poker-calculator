package com.skraylabs.poker.model;

import org.apache.commons.lang3.StringUtils;

/**
 * Factory class for constructing {@link Card} objects from String values.
 */
public class CardFactory {

  /**
   * Creates a {@link Card} object given a two-character string (e.g. "2c" for the Two of Clubs).
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
   * </ul><p>
   * Second character represents suit (must be lower-case):
   * <ul>
   * <li> s (Spades)
   * <li> h (Hearts)
   * <li> d (Diamonds)
   * <li> c (Clubs)
   * </ul>
   * 
   * @param cardString two-character string representation of a card
   * @return a new {@link Card}
   * @throws InvalidCardFormatException if {@code cardString} is formatted incorrectly.
   */
  public static Card createCardFromString(String cardString) throws InvalidCardFormatException {
    // TODO: implement me!
    Card result = null;
    if (cardString == null) {
      throw new InvalidCardFormatException();
    } else if (StringUtils.isEmpty(cardString)) {
      throw new InvalidCardFormatException(cardString);
    }
    return result;
  }

}
