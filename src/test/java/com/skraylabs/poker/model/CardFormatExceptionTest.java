package com.skraylabs.poker.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

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

  /**
   * Test utility to assert values of {@link CardFormatException#getMessage()} and
   * {@link CardFormatException#getInvalidString()}.
   *
   * @param exception system under test
   * @param message expected value of detail message
   * @param invalidString expected value of invalid string
   */
  static void assertMessageAndInvalidString(CardFormatException exception, String message,
      String invalidString) {
    assertThat(exception.getMessage(), equalTo(message));
    assertThat(exception.getInvalidString(), equalTo(invalidString));
  }
}
