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

  private static final String MSG_TOO_FEW_ARGS = "Too few arguments";
  private static final String MSG_TOO_MANY_ARGS = "Too many arguments";
  private static final String MSG_USAGE = "Usage: PokerCalculator filepath";

  /**
   * Output stream from Application.main() for test verification.
   */
  private ByteArrayOutputStream output;

  /**
   * Temporary reference to System.out
   */
  private PrintStream console;

  @Before
  public void setUp() throws Exception {
    output = new ByteArrayOutputStream();
    console = System.out;
    System.setOut(new PrintStream(output));
  }

  @After
  public void tearDown() throws Exception {
    System.setOut(console);    
  }

  @Test
  public void testShowUsageForTooFewArguments() {
    // Exercise
    Application.main();
    // Verify
    String outputString = output.toString();
    assertThat(outputString, allOf(containsString(MSG_TOO_FEW_ARGS),
        containsString(MSG_USAGE)));
  }

  @Test
  public void testShowUsageForTooManyArguments() {
    // Exercise
    Application.main("1", "2");
    // Verify
    String outputString = output.toString();
    assertThat(outputString, allOf(containsString(MSG_TOO_MANY_ARGS),
        containsString(MSG_USAGE)));
  }

}
