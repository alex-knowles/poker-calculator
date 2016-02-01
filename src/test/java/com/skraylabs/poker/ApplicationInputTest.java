package com.skraylabs.poker;

import org.junit.After;
import org.junit.Before;

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

}
