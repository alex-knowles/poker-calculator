package com.skraylabs.poker.model;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.StringTokenizer;

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
    if (input == null) {
      throw new GameStateFormatException(GameStateFormatException.MSG_NULL_INPUT);
    } else if (StringUtils.isBlank(input)) {
      throw new GameStateFormatException(GameStateFormatException.MSG_MIN_POCKET_NUM);
    }
    GameState result = new GameState();
    BufferedReader reader = new BufferedReader(new StringReader(input));
    // Create the Board
    String boardInput;
    try {
      boardInput = reader.readLine();
    } catch (IOException e) {
      throw new GameStateFormatException(GameStateFormatException.MSG_DEFAULT, e);
    }
    Board board = parseBoard(boardInput);
    result.setBoard(board);
    // Create Pockets
    int playerIndex = 0;
    try {
      String pocketInput = reader.readLine();
      while (pocketInput != null) {
        if (playerIndex >= GameState.MAX_PLAYERS) {
          throw new GameStateFormatException(GameStateFormatException.MSG_MAX_POCKET_NUM);
        }
        Pocket pocket = parsePocket(pocketInput);
        result.setPocketForPlayer(playerIndex++, pocket);
        pocketInput = reader.readLine();
      }
    } catch (IOException e) {
      throw new GameStateFormatException(GameStateFormatException.MSG_DEFAULT, e);
    }
    if (playerIndex == 0) {
      throw new GameStateFormatException(GameStateFormatException.MSG_MIN_POCKET_NUM);
    }
    // Check for duplicates
    ArrayList<Card> duplicateCards = findDuplicateCards(result);
    if (duplicateCards.size() > 0) {
      String duplicateCardString = CardFactory.createStringFromCard(duplicateCards.get(0));
      String message =
          String.format(GameStateFormatException.MSG_DUPLICATE_CARD, duplicateCardString);
      throw new GameStateFormatException(message);
    }
    return result;
  }

  /**
   * Helper method to parse a line of formatted input into a {@link Board}.
   *
   * @param input formatted String to parse
   * @return a Board
   * @throws CardFormatException if a Card is formatted incorrectly
   * @throws BoardFormatException if the Board is formatted incorrectly
   */
  static Board parseBoard(String input) throws CardFormatException, BoardFormatException {
    Board result = null;
    StringTokenizer tokenizer = new StringTokenizer(input, " ");
    ArrayList<Card> cards = new ArrayList<>();
    while (tokenizer.hasMoreTokens()) {
      String cardInput = tokenizer.nextToken();
      Card card = CardFactory.createCardFromString(cardInput);
      cards.add(card);
    }
    switch (cards.size()) {
      case 0:
        result = new Board();
        break;
      case 3:
        result = new Board(cards.get(0), cards.get(1), cards.get(2));
        break;
      case 4:
        result = new Board(cards.get(0), cards.get(1), cards.get(2), cards.get(3));
        break;
      case 5:
        result = new Board(cards.get(0), cards.get(1), cards.get(2), cards.get(3), cards.get(4));
        break;
      default:
        throw new BoardFormatException(input);
    }
    return result;
  }

  /**
   * Helper method to parse a line of formatted input into a {@link Pocket}.
   *
   * @param input formatted String to parse
   * @return a Pocket
   * @throws CardFormatException if a Card is formatted incorrectly
   * @throws BoardFormatException if the Board is formatted incorrectly
   */
  static Pocket parsePocket(String input) throws CardFormatException, PocketFormatException {
    Pocket result = null;
    StringTokenizer tokenizer = new StringTokenizer(input, " ");
    ArrayList<Card> cards = new ArrayList<>();
    while (tokenizer.hasMoreTokens()) {
      String cardInput = tokenizer.nextToken();
      Card card = CardFactory.createCardFromString(cardInput);
      cards.add(card);
    }
    switch (cards.size()) {
      case 0:
        // 0 cards is acceptable
        result = null;
        break;
      case 2:
        result = new Pocket(cards.get(0), cards.get(1));
        break;
      default:
        throw new PocketFormatException(input);
    }
    return result;
  }

  /**
   * Helper method to scan a GameState for multiple instances of a Card.
   *
   * @param gameState to scan.
   * @return a list of duplicate Cards. Results are kept in the order they were discovered. If Card
   *         appears more than twice in the game state, it will appear multiple times in the result.
   *         For example, if the Ace of Spades appears 4 times, it will be included 3 times in the
   *         results.
   */
  static ArrayList<Card> findDuplicateCards(GameState gameState) {
    Collection<Card> cards = CardUtils.collectCards(gameState);
    HashSet<Card> cardSet = new HashSet<>();
    ArrayList<Card> result = new ArrayList<>();
    for (Card card : cards) {
      boolean firstTimeAdded = cardSet.add(card);
      if (!firstTimeAdded) {
        // Duplicate found!
        result.add(card);
      }
    }
    return result;
  }
}
