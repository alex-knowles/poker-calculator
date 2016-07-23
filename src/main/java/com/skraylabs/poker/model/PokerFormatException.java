package com.skraylabs.poker.model;

/**
 * Generic Exception class for input format errors related to poker game states.
 */
public abstract class PokerFormatException extends Exception {
  /**
   * Generated serial ID.
   */
  private static final long serialVersionUID = -419175216012638160L;

  /**
   * Default Exception message. Child classes should override this with a descriptive message.
   */
  public static final String MSG_DEFAULT = "Child classes should override this value.";

  /**
   * More specific Exception message. Expects a single String argument for
   * {@link String#format(String, Object...)}, so that the specific input value that caused the
   * Exception can be reported. Child classes should override this with a descriptive message.
   */
  public static final String MSG_WITH_INVALID_STRING = "Child classes should override this value.";

  /**
   * Malformed input string that caused the Exception.
   */
  protected String invalidString;

  /**
   * Default constructor.
   */
  protected PokerFormatException() {
    super();
  }

  /**
   * Initializing constructor.
   *
   * @param message detail message for Exception
   */
  protected PokerFormatException(String message) {
    super(message);
  }

  /**
   * Initializing constructor.
   *
   * @param message detail message for Exception
   * @param cause the cause (which can be retrieved by {@link #getCause()})
   */
  public PokerFormatException(String message, Throwable cause) {
    super(message, cause);
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
   * @param messageDefault {@link #MSG_DEFAULT} as defined by the concrete calling class
   * @param messageWithInvalidString {@link #MSG_WITH_INVALID_STRING} as defined by the concrete
   *        calling class
   * @param invalidString offending string (which may erroneously be set to null)
   * @return an appropriate exception message
   */
  protected static String formatMessageForInvalidString(String messageDefault,
      String messageWithInvalidString, String invalidString) {
    String result = messageDefault;
    if (invalidString != null) {
      result = String.format(messageWithInvalidString, invalidString);
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
