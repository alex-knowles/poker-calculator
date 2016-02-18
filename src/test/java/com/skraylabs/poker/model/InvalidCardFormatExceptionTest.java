package com.skraylabs.poker.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
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
    String message = exception.getMessage();
    String invalidString = exception.getInvalidString();
    assertThat(message, equalTo(InvalidCardFormatException.MSG_DEFAULT));
    assertThat(invalidString, is(nullValue()));
  }

  @Test
  public void testInitializingConstructor() {
    // Exercise
    InvalidCardFormatException exception = new InvalidCardFormatException("5x");
    // Verify
    String message = exception.getMessage();
    String invalidString = exception.getInvalidString();
    String expectedMessage =
        String.format(InvalidCardFormatException.MSG_WITH_INVALID_STRING, "5x");
    assertThat(message, equalTo(expectedMessage));
    assertThat(invalidString, equalTo("5x"));
  }

  @Test
  public void testInitializingConstructor_emptyInvalidString() {
    // Exercise
    InvalidCardFormatException exception = new InvalidCardFormatException("");
    // Verify
    String message = exception.getMessage();
    String invalidString = exception.getInvalidString();
    assertThat(message, equalTo(InvalidCardFormatException.MSG_DEFAULT));
    assertThat(invalidString, is(nullValue()));
  }
}
