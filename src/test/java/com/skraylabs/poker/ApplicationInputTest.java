package com.skraylabs.poker;

import static com.skraylabs.poker.TestUtils.assertAbort;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class ApplicationInputTest implements ApplicationTestInterface {

  /**
   * Subclass of {@link Application} that can use a String object as a Test Double for an input
   * file.
   */
  class StringInputApplication extends SafeExitApplication {
    /**
     * Input to use for loading poker game state. Substitutes information usually read from an input
     * file.
     */
    public String inputString = "";

    @Override
    InputStream createInputStream() {
      return new ByteArrayInputStream(inputString.getBytes());
    }
  }

  /**
   * SUT: Application.
   */
  private StringInputApplication app;

  /**
   * Output stream from Application.main() for test verification.
   */
  private ByteArrayOutputStream outputStream;

  /**
   * Temporary reference to System.out
   */
  private PrintStream console;

  @Override
  public SafeExitApplication getApp() {
    return app;
  }

  @Override
  public OutputStream getOutputStream() {
    return outputStream;
  }

  /**
   * Set up test fixture.
   */
  @Before
  public void setUp() throws Exception {
    app = new StringInputApplication();
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
  public void testAbortMalformedInput() {
    // Set up
    final String input = "5h 7d Tr%n";
    app.inputString = input;
    // Exercise
    app.execute("foo");
    // Verify
    assertAbort(this, Application.ERROR_INVALID_INPUT, "Tr", Application.MSG_INVALID_INPUT);
  }

  @Test
  public void testValidInput() {
    // Set up
    final String input = "5h 7d Ts Kc 2d%n"
        + "5d 5s%n";
    app.inputString = input;
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

}
