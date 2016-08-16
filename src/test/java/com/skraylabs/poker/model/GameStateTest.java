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

  @Rule
  public ExpectedException exception = ExpectedException.none();

  /**
   * Test fixture cards to use in test methods. There are no duplicates in this set.
   */
  Card cardA;
  Card cardB;
  Card cardC;
  Card cardD;
  Card cardE;

  /**
   * Set up shared test fixture.
   *
   * <p>
   * Establishes some card values to make test methods predictable.
   *
   * @throws Exception shouldn't happen.
   */
  @Before
  public void setUp() throws Exception {
    cardA = new Card(Rank.ACE, Suit.Spades);
    cardB = new Card(Rank.THREE, Suit.Clubs);
    cardC = new Card(Rank.NINE, Suit.Diamonds);
    cardD = new Card(Rank.QUEEN, Suit.Hearts);
    cardE = new Card(Rank.FIVE, Suit.Clubs);
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
    Board expectedBoard = new Board(cardA, cardB, cardC, cardD, cardE);
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
    game.setBoard(new Board(cardA, cardB, cardC));
    // Exercise
    // Attempt to assign board card to null
    Board board = game.getBoard();
    board.flopCard1 = null;
    // Verify
    assertThat(game.getBoard(), is(notNullValue()));
    assertThat(game.getBoard().flopCard1, is(cardA));
  }

  @Test
  public void testGetBoard_readOnly2() {
    // Set up
    GameState game = new GameState();
    game.setBoard(new Board(cardA, cardB, cardC));
    // Exercise
    // Attempt to assign board card to a different card
    Board board = game.getBoard();
    board.flopCard1 = cardD;
    // Verify
    assertThat(game.getBoard(), is(notNullValue()));
    assertThat(game.getBoard().flopCard1, is(cardA));
  }

  @Test
  public void testGetBoard_readOnly3() {
    // Set up
    GameState game = new GameState();
    game.setBoard(new Board(cardA, cardB, cardC));
    final Card expectedFlopCard1 = new Card(cardA);
    // Exercise
    // Attempt to modify board card
    Board board = game.getBoard();
    board.flopCard1.rank = Rank.TWO;
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
    game.setPocketForPlayer(-1, new Pocket(cardA, cardB));
  }

  @Test
  public void testSetPocket_indexHigh() {
    // Set up
    GameState game = new GameState();
    // Verify
    exception.expect(IllegalArgumentException.class);
    // Exercise
    game.setPocketForPlayer(10, new Pocket(cardA, cardB));
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
    Pocket expectedPocket = new Pocket(cardA, cardB);
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
    Pocket expectedPocket = new Pocket(cardC, cardD);
    // Exercise
    game.setPocketForPlayer(9, expectedPocket);
    // Verify
    Pocket[] pockets = game.getPockets();
    assertThat(pockets[9], equalTo(expectedPocket));
  }

  @Test
  public void testGetPocket_readOnly1() {
    // Set up
    GameState game = new GameState();
    game.setPocketForPlayer(0, new Pocket(cardA, cardB));
    // Exercise
    // Attempt to assign pocket card to null
    Pocket pocket = game.getPockets()[0];
    pocket.card1 = null;
    // Verify
    assertThat(game.getPockets()[0].card1, is(cardA));
  }

  @Test
  public void testGetPocket_readOnly2() {
    // Set up
    GameState game = new GameState();
    game.setPocketForPlayer(0, new Pocket(cardA, cardB));
    // Exercise
    // Attempt to assign pocket card to a different card
    Pocket pocket = game.getPockets()[0];
    pocket.card1 = cardC;
    // Verify
    assertThat(game.getPockets()[0].card1, is(cardA));
  }

  @Test
  public void testGetPocket_readOnly3() {
    // Set up
    GameState game = new GameState();
    game.setPocketForPlayer(0, new Pocket(cardA, cardB));
    final Card expectedCard1 = new Card(cardA);
    // Exercise
    // Attempt to modify pocket card
    Pocket pocket = game.getPockets()[0];
    pocket.card1.rank = Rank.NINE;
    pocket.card1.suit = Suit.Diamonds;
    // Verify
    assertThat(game.getPockets()[0].card1, is(expectedCard1));
  }
}
