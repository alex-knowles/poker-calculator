package com.skraylabs.poker;

import static com.skraylabs.poker.TestUtils.assertAbort;
import static com.skraylabs.poker.TestUtils.assertAbortBadArgs;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class ApplicationArgumentsTest implements ApplicationTestInterface {

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

  public SafeExitApplication getApp() {
    return this.app;
  }

  public OutputStream getOutputStream() {
    return this.outputStream;
  }

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
    assertAbortBadArgs(this, Application.MSG_TOO_FEW_ARGS);
  }

  @Test
  public void testAbortForTooManyArguments() {
    // Exercise
    app.execute("1", "2");
    // Verify
    assertAbortBadArgs(this, Application.MSG_TOO_MANY_ARGS);
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
  public void testAbortInputFileNotOpened() {
    // Exercise
    final String filepath = "absent_file.txt";
    app.execute(filepath);
    // Verify
    assertAbort(this, Application.ERROR_FILE_NOT_OPENED,
        String.format(Application.MSG_FILE_NOT_OPENED, filepath));
  }

}
