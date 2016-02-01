package com.skraylabs.poker;

/**
 * A version of Application that can call exit() without stopping the test framework.
 *
 * <p>
 * Also acts as a Test Spy that allows us to observe the error code upon Application termination.
 */
class SafeExitApplication extends Application {

  /**
   * Error code returned from Application under test.
   */
  public int errorCode;

  @Override
  public void exit(int errorCode) {
    this.errorCode = errorCode;
  }
}
