package com.skraylabs.poker.model;

/**
 * A checked exception thrown when a string cannot be resolved to the expected {@link Card} format.
 */
public class InvalidCardFormatException extends Exception {
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
  public static final String MSG_WITH_SAMPLE =
      "The string <%s> could not be resolved to a Card format.";

  /**
   * Invalid string representation of a {@link Card}.
   */
  private String invalidString;

  /**
   * Default constructor.
   */
  public InvalidCardFormatException() {
  }

  /**
   * Constructor.
   *
   * @param invalidString offending string
   */
  public InvalidCardFormatException(String invalidString) {
    super();
    this.invalidString = invalidString;
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
