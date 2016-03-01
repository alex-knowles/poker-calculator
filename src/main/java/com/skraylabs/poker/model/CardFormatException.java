package com.skraylabs.poker.model;

/**
 * A checked exception thrown when a string cannot be resolved to the expected {@link Card} format.
 */
public class CardFormatException extends Exception {
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
   * Invalid string representation of a {@link Card}.
   */
  private String invalidString;

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
    super(formatMessageForInvalidString(invalidString));
    if (invalidString != null) {
      this.invalidString = invalidString;
    }
  }

  /**
   * Helper method to determine message based on a given invalidString parameter.
   *
   * <p>
   * This is mainly intended to be used by the initializing constructor
   * {@link CardFormatException#CardFormatException(String)}.
   *
   * <p>
   * Although {@code invalidString} is expected to be non-null, it would be undesirable for the
   * constructor to throw an IllegalArgumenException. Instead, this helper method will test for null
   * and return the default exception message. Otherwise, this helper method will insert the
   * {@code invalidString} value into the {@link CardFormatException#MSG_WITH_INVALID_STRING}
   * format.
   *
   * @param invalidString offending string (which may erroneously be set to null)
   * @return an appropriate exception message
   */
  protected static String formatMessageForInvalidString(String invalidString) {
    String result = MSG_DEFAULT;
    if (invalidString != null) {
      result = String.format(MSG_WITH_INVALID_STRING, invalidString);
    }
    return result;
  }

  /**
   * Accessor: string with invalid format.
   *
   * @return the invalidString
   */
  public String getInvalidString() {
    return invalidString;
  }
}
