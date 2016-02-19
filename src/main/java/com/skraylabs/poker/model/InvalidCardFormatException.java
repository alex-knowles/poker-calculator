package com.skraylabs.poker.model;

import org.apache.commons.lang3.StringUtils;

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
  public static final String MSG_WITH_INVALID_STRING =
      "The string <\"%s\"> could not be resolved to a Card format.";

  /**
   * Invalid string representation of a {@link Card}.
   */
  private String invalidString;

  /**
   * Default constructor.
   */
  public InvalidCardFormatException() {
    super(MSG_DEFAULT);
  }

  /**
   * Constructor.
   *
   * @param invalidString offending string
   */
  public InvalidCardFormatException(String invalidString) {
    // Use 1 of 2 message formats depending on whether or not invalidString argument is empty/null.
    super(StringUtils.isBlank(invalidString) ? MSG_DEFAULT
        : String.format(MSG_WITH_INVALID_STRING, invalidString));
    if (StringUtils.isBlank(invalidString)) {
      this.invalidString = null;
    } else {
      this.invalidString = invalidString;
    }
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
