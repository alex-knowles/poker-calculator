package com.skraylabs.poker.model;

import static com.skraylabs.poker.model.PokerFormatExceptionTest.assertMessageAndInvalidString;

import org.junit.Test;

public class PocketFormatExceptionTest {

  @Test
  public void testDefaultConstructor() {
    // Exercise
    PocketFormatException exception = new PocketFormatException();
    // Verify
    assertMessageAndInvalidString(exception, PocketFormatException.MSG_DEFAULT, null);
  }

  @Test
  public void testInitializingConstructor() {
    // Exercise
    PocketFormatException exception = new PocketFormatException("Ah");
    // Verify
    String expectedMessage = String.format(PocketFormatException.MSG_WITH_INVALID_STRING, "Ah");
    assertMessageAndInvalidString(exception, expectedMessage, "Ah");
  }

  @Test
  public void testInitializingConstructor_nullInvalidString() {
    // Exercise
    PocketFormatException exception = new PocketFormatException(null);
    // Verify
    assertMessageAndInvalidString(exception, PocketFormatException.MSG_DEFAULT, null);
  }

  @Test
  public void testInitializingConstructor_emptyInvalidString() {
    // Exercise
    PocketFormatException exception = new PocketFormatException("");
    // Verify
    String expectedMessage = String.format(PocketFormatException.MSG_WITH_INVALID_STRING, "");
    assertMessageAndInvalidString(exception, expectedMessage, "");
  }

  @Test
  public void testInitializingConstructor_blankInvalidString() {
    // Exercise
    PocketFormatException exception = new PocketFormatException(" ");
    // Verify
    String expectedMessage = String.format(PocketFormatException.MSG_WITH_INVALID_STRING, " ");
    assertMessageAndInvalidString(exception, expectedMessage, " ");
  }

}
