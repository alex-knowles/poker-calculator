package com.skraylabs.poker.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class InvalidCardFormatExceptionTest {

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testDefaultConstructor() {
    // Exercise
    InvalidCardFormatException exception = new InvalidCardFormatException();
    // Verify
    assertMessageAndInvalidString(exception, InvalidCardFormatException.MSG_DEFAULT, null);
  }

  @Test
  public void testInitializingConstructor() {
    // Exercise
    InvalidCardFormatException exception = new InvalidCardFormatException("5x");
    // Verify
    String expectedMessage =
        String.format(InvalidCardFormatException.MSG_WITH_INVALID_STRING, "5x");
    assertMessageAndInvalidString(exception, expectedMessage, "5x");
  }

  @Test
  public void testInitializingConstructor_emptyInvalidString() {
    // Exercise
    InvalidCardFormatException exception = new InvalidCardFormatException("");
    // Verify
    assertMessageAndInvalidString(exception, InvalidCardFormatException.MSG_DEFAULT, null);
  }

  /**
   * Test utility to assert values of {@link InvalidCardFormatException#getMessage()} and
   * {@link InvalidCardFormatException#getInvalidString()}.
   *
   * @param exception system under test
   * @param message expected value of detail message
   * @param invalidString expected value of invalid string
   */
  static void assertMessageAndInvalidString(InvalidCardFormatException exception, String message,
      String invalidString) {
    assertThat(exception.getMessage(), equalTo(message));
    assertThat(exception.getInvalidString(), equalTo(invalidString));
  }
}
