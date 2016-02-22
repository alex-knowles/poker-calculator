package com.skraylabs.poker.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GameStateTest {

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testConstructor() {
    // Exercise
    GameState game = new GameState();
    // Verify
    GameState.Board board = game.getBoard();
    GameState.Pocket[] pockets = game.getPockets();
    assertThat(board, is(nullValue()));
    assertThat(pockets, is(notNullValue()));
    assertThat(pockets.length, is(10));
    for (GameState.Pocket pocket : pockets) {
      assertThat(pocket, is(nullValue()));
    }
  }

}
