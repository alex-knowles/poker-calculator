package com.skraylabs.poker.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class GameStateTest {

  @Rule public ExpectedException exception = ExpectedException.none();

  /**
   * Test fixture cards to use in test methods. There are no duplicates in this set.
   */
  Card card1;
  Card card2;
  Card card3;
  Card card4;
  Card card5;

  /**
   * Set up shared test fixture.
   *
   * <p>Establishes some card values to make test methods predictable.
   *
   * @throws Exception shouldn't happen.
   */
  @Before
  public void setUp() throws Exception {
    card1 = new Card(Rank.Ace, Suit.Spades);
    card2 = new Card(Rank.Three, Suit.Clubs);
    card3 = new Card(Rank.Nine, Suit.Diamonds);
    card4 = new Card(Rank.Queen, Suit.Hearts);
    card5 = new Card(Rank.Five, Suit.Clubs);
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testConstructor() {
    // Exercise
    GameState game = new GameState();
    // Verify
    Board board = game.getBoard();
    Pocket[] pockets = game.getPockets();
    assertThat(board, is(nullValue()));
    assertThat(pockets, is(notNullValue()));
    assertThat(pockets.length, is(10));
    for (Pocket pocket : pockets) {
      assertThat(pocket, is(nullValue()));
    }
  }

  @Test
  public void testSetBoard() {
    // Set up
    GameState game = new GameState();
    Board expectedBoard = new Board(card1, card2, card3, card4, card5);
    // Exercise
    game.setBoard(expectedBoard);
    // Verify
    Board board = game.getBoard();
    assertThat(board, is(notNullValue()));
    assertThat(board, equalTo(expectedBoard));
  }

  @Test
  public void testGetBoard_readOnly1() {
    // Set up
    GameState game = new GameState();
    game.setBoard(new Board(card1, card2, card3));
    // Exercise
    // Attempt to assign board card to null
    Board board = game.getBoard();
    board.flopCard1 = null;
    // Verify
    assertThat(game.getBoard(), is(notNullValue()));
    assertThat(game.getBoard().flopCard1, is(card1));
  }

  @Test
  public void testGetBoard_readOnly2() {
    // Set up
    GameState game = new GameState();
    game.setBoard(new Board(card1, card2, card3));
    // Exercise
    // Attempt to assign board card to a different card
    Board board = game.getBoard();
    board.flopCard1 = card4;
    // Verify
    assertThat(game.getBoard(), is(notNullValue()));
    assertThat(game.getBoard().flopCard1, is(card1));
  }

  @Test
  public void testGetBoard_readOnly3() {
    // Set up
    GameState game = new GameState();
    game.setBoard(new Board(card1, card2, card3));
    final Card expectedFlopCard1 = new Card(card1);
    // Exercise
    // Attempt to modify board card
    Board board = game.getBoard();
    board.flopCard1.rank = Rank.Two;
    board.flopCard1.suit = Suit.Clubs;
    assertThat(board.flopCard1, not(expectedFlopCard1)); // guard assertion
    // Verify
    assertThat(game.getBoard(), is(notNullValue()));
    assertThat(game.getBoard().flopCard1, is(expectedFlopCard1));
  }

  @Test
  public void testSetPocket_indexLow() {
    // Set up
    GameState game = new GameState();
    // Verify
    exception.expect(IllegalArgumentException.class);
    // Exercise
    game.setPocketForPlayer(-1, new Pocket(card1, card2));
  }

  @Test
  public void testSetPocket_indexHigh() {
    // Set up
    GameState game = new GameState();
    // Verify
    exception.expect(IllegalArgumentException.class);
    // Exercise
    game.setPocketForPlayer(10, new Pocket(card1, card2));
  }

  @Test
  public void testSetPocket_null() {
    // Set up
    GameState game = new GameState();
    // Exercise
    game.setPocketForPlayer(0, null);
    // Verify
    Pocket[] pockets = game.getPockets();
    assertThat(pockets[0], is(nullValue()));
  }

  @Test
  public void testSetPocket_firstPlayer() {
    // Set up
    GameState game = new GameState();
    Pocket expectedPocket = new Pocket(card1, card2);
    // Exercise
    game.setPocketForPlayer(0, expectedPocket);
    // Verify
    Pocket[] pockets = game.getPockets();
    assertThat(pockets[0], equalTo(expectedPocket));
  }

  @Test
  public void testSetPocket_lastPlayer() {
    // Set up
    GameState game = new GameState();
    Pocket expectedPocket = new Pocket(card3, card4);
    // Exercise
    game.setPocketForPlayer(9, expectedPocket);
    // Verify
    Pocket[] pockets = game.getPockets();
    assertThat(pockets[9], equalTo(expectedPocket));
  }
}
