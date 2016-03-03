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
}