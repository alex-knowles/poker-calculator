package com.skraylabs.poker.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BoardTest {

  Board fixedBoard;
  Card card1;
  Card card2;
  Card card3;
  Card card4;

  /**
   * Set up shared test fixture.
   *
   * <p>Use common Board object with predictable Card objects populated.
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

  @After
  public void tearDown() throws Exception {}

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

}
