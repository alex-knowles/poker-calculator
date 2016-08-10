package com.skraylabs.poker.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BoardTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  Board fixedBoard;
  Card card1;
  Card card2;
  Card card3;
  Card card4;

  /**
   * Set up shared test fixture.
   *
   * <p>
   * Use common Board object with predictable Card objects populated.
   *
   * @throws Exception shouldn't happen.
   */
  @Before
  public void setUp() throws Exception {
    card1 = new Card(Rank.Jack, Suit.Diamonds);
    card2 = new Card(Rank.Queen, Suit.Diamonds);
    card3 = new Card(Rank.Three, Suit.Hearts);
    card4 = new Card(Rank.Jack, Suit.Spades);
    fixedBoard = new Board(card1, card2, card3, card4);
  }

  @Test
  public void testCopyConstructor_success() {
    // Exercise
    Board board = new Board(fixedBoard);
    // Verify
    assertThat(board, equalTo(fixedBoard));
  }

  @Test
  public void testCopyConstructor_null() {
    // Verify
    exception.expect(NullPointerException.class);
    // Exercise
    @SuppressWarnings("unused")
    Board board = new Board(null);
  }

  @Test
  public void testEquals_same() {
    // Set up
    Board equivalentBoard = new Board(card1, card2, card3, card4);
    // Exercise
    boolean equals = fixedBoard.equals(equivalentBoard);
    // Verify
    assertThat(equals, is(true));
  }

  @Test
  public void testEquals_null() {
    // Exercise
    boolean equals = fixedBoard.equals(null);
    // Verify
    assertThat(equals, is(false));
  }

  @Test
  public void testEquals_different1() {
    // Set up
    Board differentBoard = new Board();
    // Exercise
    boolean equals = fixedBoard.equals(differentBoard);
    // Verify
    assertThat(equals, is(false));
  }

  @Test
  public void testEquals_different2() {
    // Set up
    Card riverCard = new Card(Rank.Eight, Suit.Clubs);
    Board differentBoard = new Board(card1, card2, card3, card4, riverCard);
    // Exercise
    boolean equals = fixedBoard.equals(differentBoard);
    // Verify
    assertThat(equals, is(false));
  }

  @Test
  public void testEquals_different3() {
    // Set up
    Board differentBoard = new Board(card3, card1, card4, card2);
    // Exercise
    boolean equals = fixedBoard.equals(differentBoard);
    // Verify
    assertThat(equals, is(false));
  }

}
