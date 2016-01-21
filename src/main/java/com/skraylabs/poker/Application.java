package com.skraylabs.poker;

public class Application {

  static final String MSG_TOO_FEW_ARGS = "Too few arguments";
  static final String MSG_TOO_MANY_ARGS = "Too many arguments";
  static final String MSG_USAGE = "Usage: PokerCalculator filepath";

  private static Application app;

  public static void main(String... args) {
    app = new Application();
    app.execute(args);
  }

  public void execute(String... args) {
    validate(args);
  }

  private void validate(String[] args) {
    if (args.length < 1) {
      System.out.println(MSG_TOO_FEW_ARGS);
      System.out.println(MSG_USAGE);
    } else if (args.length > 1) {
      System.out.println(MSG_TOO_MANY_ARGS);
      System.out.println(MSG_USAGE);
    }
  }

}
