package com.skraylabs.poker.model;

/**
 * The state of a Texas Hold 'Em Poker game.
 */
public class GameState {

  /**
   * The "board". Community cards.
   */
  private Board board;

  /**
   * Pocket cards. Player cards. There is a maximum of 10 players.
   */
  private Pocket[] pockets = new Pocket[10];

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
   * Accessor: get pocket cards.
   *
   * @return player cards
   */
  public Pocket[] getPockets() {
    return pockets;
  }

  /**
   * Modifier: set Pocket cards for a specified player.
   *
   * @param playerIndex a value in range [0, 9]. Specify player for which to assign pocket cards.
   * @param pocket Pocket value to assign.
   */
  void setPocketForPlayer(int playerIndex, Pocket pocket) {
    // TODO: implement me!
  }
}
