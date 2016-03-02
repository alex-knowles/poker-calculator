package com.skraylabs.poker.model;

import static com.skraylabs.poker.model.PokerFormatExceptionTest.assertMessageAndInvalidString;

import org.junit.Test;

public class BoardFormatExceptionTest {

  @Test
  public void testDefaultConstructor() {
    // Exercise
    BoardFormatException exception = new BoardFormatException();
    // Verify
    assertMessageAndInvalidString(exception, BoardFormatException.MSG_DEFAULT, null);
  }

  @Test
  public void testInitializingConstructor() {
    // Exercise
    BoardFormatException exception = new BoardFormatException("Ah Qs");
    // Verify
    String expectedMessage = String.format(BoardFormatException.MSG_WITH_INVALID_STRING, "Ah Qs");
    assertMessageAndInvalidString(exception, expectedMessage, "Ah Qs");
  }

  @Test
  public void testInitializingConstructor_nullInvalidString() {
    // Exercise
    BoardFormatException exception = new BoardFormatException(null);
    // Verify
    assertMessageAndInvalidString(exception, BoardFormatException.MSG_DEFAULT, null);
  }

  @Test
  public void testInitializingConstructor_emptyInvalidString() {
    // Exercise
    BoardFormatException exception = new BoardFormatException("");
    // Verify
    String expectedMessage = String.format(BoardFormatException.MSG_WITH_INVALID_STRING, "");
    assertMessageAndInvalidString(exception, expectedMessage, "");
  }

  @Test
  public void testInitializingConstructor_blankInvalidString() {
    // Exercise
    BoardFormatException exception = new BoardFormatException(" ");
    // Verify
    String expectedMessage = String.format(BoardFormatException.MSG_WITH_INVALID_STRING, " ");
    assertMessageAndInvalidString(exception, expectedMessage, " ");
  }

}
