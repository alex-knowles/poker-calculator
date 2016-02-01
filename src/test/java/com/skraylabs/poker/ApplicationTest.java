package com.skraylabs.poker;

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
  private SafeExitApplication app;

  /**
   * Output stream from Application.main() for test verification.
   */
  private ByteArrayOutputStream outputStream;

  /**
   * Temporary reference to System.out
   */
  private PrintStream console;

  /**
   * Set up test fixture.
   */
  @Before
  public void setUp() throws Exception {
    app = new SafeExitApplication();
    outputStream = new ByteArrayOutputStream();
    console = System.out;
    System.setOut(new PrintStream(outputStream));
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
    String output = outputStream.toString();
    assertThat(output, not(containsString(Application.MSG_USAGE)));
    assertThat(app.getFilepath(), equalTo(filepath));
    assertThat(app.errorCode, not(Application.ERROR_CODE_BAD_ARGS));
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
    assertAbort(Application.ERROR_INVALID_INPUT, "Tr", Application.MSG_INVALID_INPUT);
  }

  @Test
  public void testValidInput() {
    // Set up
    final String input = "5h 7d Ts Kc 2d%n"
        + "5d 5s%n";
    app = new SafeExitApplication() {
      @Override
      InputStream createInputStream() {
        return new ByteArrayInputStream(input.getBytes());
      }
    };

    // Exercise
    app.execute("foo.txt");
    // Verify
    assertThat(app.errorCode, equalTo(0));
    String output = outputStream.toString();
    assertThat(output, containsString("Royal Flush: 0%"));
    assertThat(output, containsString("Straight Flush: 0%"));
    assertThat(output, containsString("Four of a Kind: 0%"));
    assertThat(output, containsString("Full House: 0%"));
    assertThat(output, containsString("Flush: 0%"));
    assertThat(output, containsString("Straight: 0%"));
    assertThat(output, containsString("Three of a Kind: 100%"));
    assertThat(output, containsString("Two Pair: 0%"));
    assertThat(output, containsString("Two of a Kind: 100%"));
  }

  @Test
  public void testAbortInputFileNotOpened() {
    // Exercise
    final String filepath = "absent_file.txt";
    app.execute(filepath);
    // Verify
    assertAbort(Application.ERROR_FILE_NOT_OPENED,
        String.format(Application.MSG_FILE_NOT_OPENED, filepath));
  }

  /**
   * Assert that the Application aborts with an error code indicating invalid arguments.
   *
   * @param errorCode sent upon exit()
   * @param errorMessages Detailed error message(s) to check for.
   */
  void assertAbort(int errorCode, String... errorMessages) {
    assertThat(app.errorCode, equalTo(errorCode));
    String output = outputStream.toString();
    for (String s : errorMessages) {
      assertThat(output, containsString(s));
    }
  }

  /**
   * Assert that the Application aborts with an error code indicating invalid arguments.
   *
   * @param errorMessage Detail error message (in addition to
   *        {@link Application#ERROR_CODE_BAD_ARGS}).
   */
  void assertAbortBadArgs(String errorMessage) {
    assertAbort(Application.ERROR_CODE_BAD_ARGS, errorMessage);
  }

}
