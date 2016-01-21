package com.skraylabs.poker;

public class Application {

  static final String MSG_TOO_FEW_ARGS = "Too few arguments";
  static final String MSG_TOO_MANY_ARGS = "Too many arguments";
  static final String MSG_USAGE = "Usage: PokerCalculator filepath";
  static final int ERROR_CODE_BAD_ARGS = 1;

  private static Application app;

  private String errorMessage;
  private String filepath;

  /**
   * Access the filepath where Application will attempt to read input from.
   *
   * @return relative filepath to input file
   */
  public String getFilepath() {
    return filepath;
  }

  public static void main(String... args) {
    app = new Application();
    app.execute(args);
  }

  /**
   * Execute application
   *
   * @param args should be exactly 1 string specifying the input filepath to read from.
   */
  public void execute(String... args) {
    if (!validate(args)) {
      System.out.println(errorMessage);
      System.out.println(MSG_USAGE);
      exit(ERROR_CODE_BAD_ARGS);
    } else {
      // TODO: process input from file
    }
  }

  /**
   * Terminate execution with a given error code.
   * @param errorCode nonzero for abnormal termination.
   */
  public void exit(int errorCode) {
    System.exit(errorCode);
  }

  /**
   * Validate the command line arguments. Will set errorMessage to an appropriate value if
   * validation fails.
   *
   * @param args command line arguments
   * @return true if valid, false otherwise
   */
  private boolean validate(String[] args) {
    boolean result = true;
    if (args.length < 1) {
      errorMessage = MSG_TOO_FEW_ARGS;
      result = false;
    } else if (args.length > 1) {
      errorMessage = MSG_TOO_MANY_ARGS;
      result = false;
    }
    return result;
  }

}
