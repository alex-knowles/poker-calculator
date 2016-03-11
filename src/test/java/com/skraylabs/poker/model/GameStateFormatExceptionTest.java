package com.skraylabs.poker.model;

import static com.skraylabs.poker.model.PokerFormatExceptionTest.assertMessageAndInvalidString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class GameStateFormatExceptionTest {

  @Test
  public void testDefaultConstructor() {
    // Exercise
    GameStateFormatException exception = new GameStateFormatException();
    // Verify
    assertMessageAndInvalidString(exception, GameStateFormatException.MSG_DEFAULT, null);
  }

  @Test
  public void testInitializingConstructor() {
    // Set up
    String expectedMessage = GameStateFormatException.MSG_MIN_POCKET_NUM;
    // Exercise
    GameStateFormatException exception = new GameStateFormatException(expectedMessage);
    // Verify
    assertMessageAndInvalidString(exception, expectedMessage, null);
  }

  @Test
  public void testInitializingConstructor_nullMessage() {
    // Exercise
    GameStateFormatException exception = new GameStateFormatException(null);
    // Verify
    assertMessageAndInvalidString(exception, null, null);
  }

  @Test
  public void testInitializingConstructor_emptyMessage() {
    // Exercise
    GameStateFormatException exception = new GameStateFormatException("");
    // Verify
    assertMessageAndInvalidString(exception, "", null);
  }

  @Test
  public void testInitializingConstructor_blankMessage() {
    // Exercise
    GameStateFormatException exception = new GameStateFormatException(" ");
    // Verify
    assertMessageAndInvalidString(exception, " ", null);
  }

  @Test
  public void testInitializingConstructor_cause() {
    // Set up
    String expectedMessage = "foo";
    Exception expectedCause = new RuntimeException("bar");
    // Exercise
    GameStateFormatException exception =
        new GameStateFormatException(expectedMessage, expectedCause);
    // Verify
    assertMessageAndInvalidString(exception, expectedMessage, null);
    assertThat(exception.getCause(), equalTo(expectedCause));
  }

}
