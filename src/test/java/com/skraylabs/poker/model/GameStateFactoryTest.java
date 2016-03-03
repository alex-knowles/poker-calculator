package com.skraylabs.poker.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class GameStateFactoryTest {
  
  @Rule public ExpectedException exception = ExpectedException.none();

  static final String validBoard_3Cards = "As 5d 7h";

  @Test
  public void testInvalidInput_null() throws PokerFormatException {
    // Verify
    exception.expect(GameStateFormatException.class);
    exception.expectMessage(GameStateFormatException.MSG_NULL_INPUT);
    // Exercise
    GameStateFactory.createGameStateFromString(null);
  }

  @Test
  public void testInvalidInput_empty() throws PokerFormatException {
    // Verify
    exception.expect(GameStateFormatException.class);
    exception.expectMessage(GameStateFormatException.MSG_MIN_POCKET_NUM);
    // Exercise
    GameStateFactory.createGameStateFromString("");
  }

  @Test
  public void testInvalidInput_blank() throws PokerFormatException {
    // Verify
    exception.expect(GameStateFormatException.class);
    exception.expectMessage(GameStateFormatException.MSG_MIN_POCKET_NUM);
    // Exercise
    GameStateFactory.createGameStateFromString(" ");
  }

  @Test
  public void testInvalidInput_noPockets() throws PokerFormatException {
    // Setup
    String input = String.format("%s%n", validBoard_3Cards);
    // Verify
    exception.expect(GameStateFormatException.class);
    exception.expectMessage(GameStateFormatException.MSG_MIN_POCKET_NUM);
    // Exercise
    GameStateFactory.createGameStateFromString(input);
  }
}
