package com.skraylabs.poker.model;

/**
 * The state of a Texas Hold 'Em Poker game.
 */
public class GameState {

  /**
   * The "board" is the set of 5 community cards shared by all players in a game Texas Hold 'Em
   * Poker.
   */
  public class Board {
    /**
     * First of three cards dealt in the second round (the "flop").
     */
    public Card flopCard1;
    /**
     * Second of three cards dealt in the second round (the "flop").
     */
    public Card flopCard2;
    /**
     * Third of three cards dealt in the second round (the "flop").
     */
    public Card flopCard3;
    /**
     * Card dealt in the third round (the "turn").
     */
    public Card turnCard;
    /**
     * Card dealt in the fourth and final round (the "river").
     */
    public Card riverCard;
  } // end of class Board

  /**
   * Pair of face-down cards held by each player in a game of Texas Hold 'Em poker.
   */
  public class Pocket {
    /**
     * First of two pocket cards.
     */
    public Card card1;
    /**
     * Second of two pocket cards.
     */
    public Card card2;
  } // end of class Pocket

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
