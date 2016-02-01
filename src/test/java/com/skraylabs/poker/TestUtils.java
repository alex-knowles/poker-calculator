package com.skraylabs.poker;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Collection of useful Test Helpers.
 */
class TestUtils {

  /**
   * Assert that the Application aborts with an error code indicating invalid arguments.
   *
   * @param testClass Application test class
   * @param errorCode sent upon exit()
   * @param errorMessages Detailed error message(s) to check for.
   */
  static void assertAbort(ApplicationTestInterface testClass, int errorCode,
      String... errorMessages) {
    assertThat(testClass.getApp().errorCode, equalTo(errorCode));
    String output = testClass.getOutputStream().toString();
    for (String s : errorMessages) {
      assertThat(output, containsString(s));
    }
  }

  /**
   * Assert that the Application aborts with an error code indicating invalid arguments.
   *
   * @param testClass Application test class
   * @param errorMessage Detail error message (in addition to
   *        {@link Application#ERROR_CODE_BAD_ARGS}).
   */
  static void assertAbortBadArgs(ApplicationTestInterface testClass, String errorMessage) {
    assertAbort(testClass, Application.ERROR_CODE_BAD_ARGS, errorMessage);
  }

}
