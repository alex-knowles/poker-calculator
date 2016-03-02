package com.skraylabs.poker.model;

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

}
