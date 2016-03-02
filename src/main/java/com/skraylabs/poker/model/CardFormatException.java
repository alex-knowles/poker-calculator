package com.skraylabs.poker.model;

/**
 * A checked exception thrown when a string cannot be resolved to the expected {@link Card} format.
 */
public class CardFormatException extends PokerFormatException {
  /**
   * Generated serial ID.
   */
  private static final long serialVersionUID = -1704270302225109151L;

  /**
   * Default detail message used when the invalid string sample is not provided.
   */
  public static final String MSG_DEFAULT =
      "The provided string could not be resolved to a Card format.";

  /**
   * Detail message used when the invalid string sample is provided. Expects one String argument.
   */
  public static final String MSG_WITH_INVALID_STRING =
      "The string <\"%s\"> could not be resolved to a Card format.";

  /**
   * Default constructor.
   */
  public CardFormatException() {
    super(MSG_DEFAULT);
  }

  /**
   * Constructor.
   *
   * @param invalidString offending string
   */
  public CardFormatException(String invalidString) {
    super(formatMessageForInvalidString(MSG_DEFAULT, MSG_WITH_INVALID_STRING, invalidString));
    if (invalidString != null) {
      this.invalidString = invalidString;
    }
  }
}
