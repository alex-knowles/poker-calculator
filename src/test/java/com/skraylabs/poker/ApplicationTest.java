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

  /**
   * Output stream from Application.main() for test verification.
   */
  private ByteArrayOutputStream output;

  /**
   * Temporary reference to System.out
   */
  private PrintStream console;

  /**
   * Set up test fixture.
   */
  @Before
  public void setUp() throws Exception {
    output = new ByteArrayOutputStream();
    console = System.out;
    System.setOut(new PrintStream(output));
  }

  /**
   * Tear down test fixture.
   */
  @After
  public void tearDown() throws Exception {
    System.setOut(console);    
  }

  @Test
  public void testAbortForTooFewArguments() {
    // Exercise
    app.execute();
    // Verify
    String outputString = output.toString();
    assertThat(outputString, allOf(containsString(Application.MSG_TOO_FEW_ARGS),
        containsString(Application.MSG_USAGE)));
  }

  @Test
  public void testAbortForTooManyArguments() {
    // Exercise
    app.execute("1", "2");
    // Verify
    String outputString = output.toString();
    assertThat(outputString, allOf(containsString(Application.MSG_TOO_MANY_ARGS),
        containsString(Application.MSG_USAGE)));
  }

}
