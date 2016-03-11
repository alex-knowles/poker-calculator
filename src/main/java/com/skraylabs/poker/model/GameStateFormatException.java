package com.skraylabs.poker.model;

/**
 * Checked exception thrown when a string representing a {@link GameState} is formatted incorrectly.
 * This applies to general syntax errors that don't qualify for a more specific exception (
 * {@link CardFormatException}, {@link BoardFormatException}, {@link GameStateFormatException}).
 */
public class GameStateFormatException extends PokerFormatException {

  /**
   * Generated serial ID.
   */
  private static final long serialVersionUID = -3555305759559275725L;

  /**
   * Default detail message used when the invalid string sample is not provided.
   */
  public static final String MSG_DEFAULT =
      "The provided string could not be resolved to a GameState format.";

  /**
   * Detail message used when the invalid string sample is provided. Expects one String argument.
   */
  public static final String MSG_WITH_INVALID_STRING =
      "The string <\"%s\"> could not be resolved to a GameState format.";

  /**
   * Detail message used when the input has zero Pockets. At least one Pocket must be provided.
   */
  public static final String MSG_MIN_POCKET_NUM = "A GameState must have at least 1 Pocket.";

  /**
   * Detail message used when the input has too many Pockets."
   */
  public static final String MSG_MAX_POCKET_NUM = "A GameState must have no more than 10 Pockets.";

  /**
   * Detail message used when the input is null.
   */
  public static final String MSG_NULL_INPUT = "A GameState cannot be created from null input.";

  /**
   * Detail message used when the input contains more than one copy of a Card. Expects one String
   * argument.
   */
  public static final String MSG_DUPLICATE_CARD =
      "All cards in a GameState must be unique. Duplicate card value: <\"%s\">.";

  /**
   * Default constructor.
   */
  public GameStateFormatException() {
    super(MSG_DEFAULT);
  }

  /**
   * Initializing constructor.
   *
   * @param message detail message
   */
  public GameStateFormatException(String message) {
    super(message);
  }

  /**
   * Initializing constructor.
   *
   * @param message detail message
   * @param cause the cause (which can be retrieved by {@link #getCause()})
   */
  public GameStateFormatException(String message, Throwable cause) {
    super(message, cause);
  }
}