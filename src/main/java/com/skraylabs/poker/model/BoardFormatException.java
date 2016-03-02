package com.skraylabs.poker.model;

/**
 * A checked exception thrown when a string cannot be resolved to the expected {@link Board}
 * format.
 */
public class BoardFormatException extends PokerFormatException {

  /**
   * Generated serial ID.
   */
  private static final long serialVersionUID = -2202025646209124131L;

  /**
   * Default detail message used when the invalid string sample is not provided.
   */
  public static final String MSG_DEFAULT =
      "The provided string could not be resolved to a Board format.";

  /**
   * Detail message used when the invalid string sample is provided. Expects one String argument.
   */
  public static final String MSG_WITH_INVALID_STRING =
      "The string <\"%s\"> could not be resolved to a Board format.";

  /**
   * Default constructor.
   */
  public BoardFormatException() {
    super(MSG_DEFAULT);
  }

  /**
   * Initializing constructor.
   *
   * @param invalidString offending string
   */
  public BoardFormatException(String invalidString) {
    super(formatMessageForInvalidString(MSG_DEFAULT, MSG_WITH_INVALID_STRING, invalidString));
    if (invalidString != null) {
      this.invalidString = invalidString;
    }
  }
}
