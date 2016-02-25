package com.skraylabs.poker.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GameStateTest {

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

}
