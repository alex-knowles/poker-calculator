package com.skraylabs.poker.model;

/**
 * Factory class for constructing a {@link GameState} instance from a formatted string/input stream.
 */
public class GameStateFactory {

  /**
   * Creates a {@link GameState object} given a specifically formatted string.
   *
   * <p>
   * A GameState is composed of a {@link Board} and 1 to 10 {@link Pocket} objects. Boards and
   * Pockets are composed of {@link Card} objects. Refer to
   * {@link CardFactory#createCardFromString(String)} for Card formatting rules. Cards on the same
   * line should be separated by a single "space" character.
   *
   * <p>
   * The Board is represented by the first line of {@code input}. It can appear in one of four forms
   * depending on which round of play is represented:
   * <ul>
   * <li>Pre-flop: a blank line (no cards are showing)
   * <li>Flop: 3 cards
   * <li>Turn: 4 cards
   * <li>River: 5 cards
   * </ul>
   *
   * <p>
   * There can be up to 10 players. Each player has 2 Pocket cards. Lines 2-11 can be represented as
   * either:
   * <ul>
   * <li>Unknown: an empty line (0 cards)
   * <li>Known: 2 cards
   * </ul>
   *
   * @param input string representation from which to create a {@link GameState}
   * @return a GameState created from {@code input}
   * @throws CardFormatException if a Card in {@code input} is formatted incorrectly
   * @throws BoardFormatException if a Board in {@code input} is formatted incorrectly
   * @throws PocketFormatException if a Pocket in {@code input} is formatted incorrectly
   * @throws GameStateFormatException if {@code input} has a general formatting issue that is not
   *         covered by any of the above exceptions
   */
  public static GameState createGameStateFromString(String input) throws CardFormatException,
      BoardFormatException, PocketFormatException, GameStateFormatException {
    // TODO: Implement me!
    GameState result = new GameState();
    return result;
  }
}
