package com.skraylabs.poker.model;

import static com.skraylabs.poker.model.PokerFormatExceptionTest.assertMessageAndInvalidString;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CardFormatExceptionTest {

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testDefaultConstructor() {
    // Exercise
    CardFormatException exception = new CardFormatException();
    // Verify
    assertMessageAndInvalidString(exception, CardFormatException.MSG_DEFAULT, null);
  }

  @Test
  public void testInitializingConstructor() {
    // Exercise
    CardFormatException exception = new CardFormatException("5x");
    // Verify
    String expectedMessage = String.format(CardFormatException.MSG_WITH_INVALID_STRING, "5x");
    assertMessageAndInvalidString(exception, expectedMessage, "5x");
  }

  @Test
  public void testInitializingConstructor_nullInvalidString() {
    // Exercise
    CardFormatException exception = new CardFormatException(null);
    // Verify
    assertMessageAndInvalidString(exception, CardFormatException.MSG_DEFAULT, null);
  }

  @Test
  public void testInitializingConstructor_emptyInvalidString() {
    // Exercise
    CardFormatException exception = new CardFormatException("");
    // Verify
    String expectedMessage = String.format(CardFormatException.MSG_WITH_INVALID_STRING, "");
    assertMessageAndInvalidString(exception, expectedMessage, "");
  }

  @Test
  public void testInitializingConstructor_blankInvalidString() {
    // Exercise
    CardFormatException exception = new CardFormatException(" ");
    // Verify
    String expectedMessage = String.format(CardFormatException.MSG_WITH_INVALID_STRING, " ");
    assertMessageAndInvalidString(exception, expectedMessage, " ");
  }
}
