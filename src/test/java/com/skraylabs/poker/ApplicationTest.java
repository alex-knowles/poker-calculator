package com.skraylabs.poker;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class ApplicationTest {

  /**
   * SUT: Application.
   */
  private Application app;

  /**
   * Error code returned from Application under test.
   */
  private int errorCode;

  /**
   * Output stream from Application.main() for test verification.
   */
  private ByteArrayOutputStream output;

  /**
   * Temporary reference to System.out
   */
  private PrintStream console;

  /**
   * A version of Application that can call exit() without stopping the test framework.
   *
   * <p>
   * Also acts as a Test Spy that allows us to observe the error code upon Application termination.
   */
  class SafeExitApplication extends Application {
    @Override
    public void exit(int errorCode) {
      ApplicationTest.this.errorCode = errorCode;
    }
  }

  /**
   * Set up test fixture.
   */
  @Before
  public void setUp() throws Exception {
    app = new SafeExitApplication();
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
    errorCode = 0;
  }

  @Test
  public void testAbortForTooFewArguments() {
    // Exercise
    app.execute();
    // Verify
    assertAbortBadArgs(Application.MSG_TOO_FEW_ARGS);
  }

  @Test
  public void testAbortForTooManyArguments() {
    // Exercise
    app.execute("1", "2");
    // Verify
    assertAbortBadArgs(Application.MSG_TOO_MANY_ARGS);
  }

  @Test
  public void testValidArguments() {
    // Exercise
    final String filepath = "poker.txt";
    app.execute(filepath);
    // Verify
    String outputString = output.toString();
    assertThat(outputString, not(containsString(Application.MSG_USAGE)));
    assertThat(app.getFilepath(), equalTo(filepath));
    assertThat(errorCode, not(Application.ERROR_CODE_BAD_ARGS));
  }

  @Test
  public void testAbortMalformedInput() {
    // Set up
    final String input = "5h 7d Tr%n";
    app = new SafeExitApplication() {
      @Override
      InputStream createInputStream() {
        return new ByteArrayInputStream(input.getBytes());
      }
    };

    // Exercise
    app.execute("foo");
    // Verify
    String outputString = output.toString();
    assertThat(outputString, allOf(containsString("Tr"),
        containsString(Application.MSG_INVALID_INPUT)));
    assertThat(errorCode, equalTo(Application.ERROR_INVALID_INPUT));
  }

  /**
   * Assert that the Application aborts with an error code indicating invalid arguments.
   *
   * @param errorMessage Detailed error message to check for.
   */
  void assertAbortBadArgs(String errorMessage) {
    String outputString = output.toString();
    assertThat(outputString, allOf(containsString(errorMessage),
        containsString(Application.MSG_USAGE)));
    assertThat(errorCode, equalTo(Application.ERROR_CODE_BAD_ARGS));
  }

}
