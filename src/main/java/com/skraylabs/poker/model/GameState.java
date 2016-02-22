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
   * Accessor: get the board cards.
   *
   * @return community cards
   */
  public Board getBoard() {
    return board;
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
   * Modifier: set pocket cards.
   *
   * @param pockets player cards to set
   */
  void setPockets(Pocket[] pockets) {
    this.pockets = pockets;
  }
}
