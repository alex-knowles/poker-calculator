package com.skraylabs.poker;

import java.io.OutputStream;

/**
 * General interface for Application Test Classes.
 */
interface ApplicationTestInterface {

  /**
   * Get application under test.
   *
   * @return {@link Application} instance under test
   */
  SafeExitApplication getApp();

  /**
   * Get console output from application under test.
   *
   * @return output stream set to System.out to capture console input from an {@link Application}
   */
  OutputStream getOutputStream();
}
