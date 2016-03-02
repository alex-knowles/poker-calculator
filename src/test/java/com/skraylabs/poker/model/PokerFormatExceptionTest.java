package com.skraylabs.poker.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class PokerFormatExceptionTest {

  @Test
  public void testGetInvalidString() {
    // Set up
    final String expectedInvalidString = "foo";
    @SuppressWarnings("serial")
    PokerFormatException sut = new PokerFormatException() {};
    sut.invalidString = expectedInvalidString;
    // Exercise
    String invalidString = sut.getInvalidString();
    // Verify
    assertThat(invalidString, is(expectedInvalidString));
  }

  /**
   * Test utility to assert values of {@link PokerFormatException#getMessage()} and
   * {@link PokerFormatException#getInvalidString()}.
   *
   * @param exception system under test
   * @param message expected value of detail message
   * @param invalidString expected value of invalid string
   */
  static void assertMessageAndInvalidString(PokerFormatException exception, String message,
      String invalidString) {
    assertThat(exception.getMessage(), equalTo(message));
    assertThat(exception.getInvalidString(), equalTo(invalidString));
  }

}
