package com.skraylabs.poker;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ApplicationTest {

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testShowUsageForTooFewArguments() {
    // Set up
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    PrintStream console = System.out;
    try {
      System.setOut(new PrintStream(output));
      String[] args = {""};
      // Exercise
      Application.main(args);
    } finally {
      // Tear down
      System.setOut(console);
    }
    // Verify
    String outputString = output.toString();
    assertThat(outputString, allOf(containsString("Too few arguments"),
        containsString("Usage: PokerCalculator filepath")));
  }

}
