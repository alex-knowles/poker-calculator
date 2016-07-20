package com.skraylabs.poker.model;

/**
 * The state of a Texas Hold 'Em Poker game.
 */
public class GameState {

  public static final int MAX_PLAYERS = 10;

  /**
   * The "board". Community cards.
   */
  private Board board;

  /**
   * Pocket cards. Player cards. There is a maximum of 10 players.
   */
  private Pocket[] pockets = new Pocket[MAX_PLAYERS];

  /**
   * Default constructor.
   */
  GameState() {
  }

  /**
   * Accessor: get a copy of the board cards.
   *
   * @return community cards
   */
  public Board getBoard() {
    Board result = null;
    if (this.board != null) {
      result = new Board(this.board);
    }
    return result;
  }

  /**
   * Modifier: set the board cards.
   *
   * @param board community cards to set
   */
  void setBoard(Board board) {
    this.board = board;
  }

  /**
   * Accessor: get copies of the pocket cards.
   *
   * @return player cards
   */
  public Pocket[] getPockets() {
    Pocket[] result = new Pocket[MAX_PLAYERS];
    for (int i = 0; i < MAX_PLAYERS; ++i) {
      if (this.pockets[i] != null) {
        result[i] = new Pocket(this.pockets[i]);
      }
    }
    return result;
  }

  /**
   * Modifier: set Pocket cards for a specified player.
   *
   * @param playerIndex a value in range [0, 9]. Specify player for which to assign pocket cards.
   * @param pocket Pocket value to assign.
   */
  void setPocketForPlayer(int playerIndex, Pocket pocket) {
    if (playerIndex < 0 || MAX_PLAYERS <= playerIndex) {
      throw new IllegalArgumentException(
          String.format("Unexpected value [%d] for 'playerIndex'. Must be in range [0, %d]",
              playerIndex, MAX_PLAYERS - 1));
    }
    pockets[playerIndex] = pocket;
  }
}
