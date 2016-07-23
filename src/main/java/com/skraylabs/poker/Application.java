package com.skraylabs.poker;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Application {

  static final String MSG_TOO_FEW_ARGS = "Too few arguments";
  static final String MSG_TOO_MANY_ARGS = "Too many arguments";
  static final String MSG_USAGE = "Usage: PokerCalculator filepath";
  static final String MSG_INVALID_INPUT = "Input is formatted incorrectly";
  static final String MSG_FILE_NOT_OPENED = "File [%s] could not be opened";
  static final int ERROR_CODE_BAD_ARGS = 1;
  static final int ERROR_INVALID_INPUT = 2;
  static final int ERROR_FILE_NOT_OPENED = 3;

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
      return;
    } else {
      // Create input stream from filepath
      this.filepath = args[0];
      InputStream input = null;
      try {
        input = createInputStream();
      } catch (FileNotFoundException e) {
        errorMessage = String.format(MSG_FILE_NOT_OPENED, filepath);
        System.out.println(errorMessage);
        exit(ERROR_FILE_NOT_OPENED);
        return;
      }

      // TODO: process input from file

      // Close input stream
      if (input != null) {
        try {
          input.close();
        } catch (IOException e) {
          // Could not close input stream.
        }
      }
    }
  }

  /**
   * Terminate execution with a given error code.
   *
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

  /**
   * Helper method to create an input stream from which to read game state.
   *
   * @return input stream that can be processed for board and pocket cards.
   * @throws FileNotFoundException if {@link Application#filepath} cannot be found.
   */
  InputStream createInputStream() throws FileNotFoundException {
    InputStream result = null;
    if (!StringUtils.isBlank(filepath)) {
      File inputFile = new File(filepath);
      result = new FileInputStream(inputFile);
    }
    return result;
  }

  /**
   * Helper method to format calculated output values for a specified player.
   *
   * <p>
   * Probability is expressed as percentages rounded to the nearest ones place. -- e.g. 0.855 is
   * formatted as "86%"
   *
   * @param calculator initialized with the {@link GameState} to evaluate.
   * @param playerIndex in the range [0, 9]
   * @return formatted string describing the probability for each poker hand
   */
  static String formatOutputForPlayer(ProbabilityCalculator calculator, int playerIndex) {
    StringBuilder builder = new StringBuilder();

    long probability = Math.round(100 * calculator.royalFlushForPlayer(playerIndex));
    builder.append(String.format("Royal Flush: %d%%\n", probability));

    probability = Math.round(100 * calculator.straightFlushForPlayer(playerIndex));
    builder.append(String.format("Straight Flush: %d%%\n", probability));

    probability = Math.round(100 * calculator.fourOfAKindForPlayer(playerIndex));
    builder.append(String.format("Four of a Kind: %d%%\n", probability));

    probability = Math.round(100 * calculator.fullHouseForPlayer(playerIndex));
    builder.append(String.format("Full House: %d%%\n", probability));

    probability = Math.round(100 * calculator.flushForPlayer(playerIndex));
    builder.append(String.format("Flush: %d%%\n", probability));

    probability = Math.round(100 * calculator.straightForPlayer(playerIndex));
    builder.append(String.format("Straight: %d%%\n", probability));

    probability = Math.round(100 * calculator.threeOfAKindForPlayer(playerIndex));
    builder.append(String.format("Three of a Kind: %d%%\n", probability));

    probability = Math.round(100 * calculator.twoPairForPlayer(playerIndex));
    builder.append(String.format("Two Pair: %d%%\n", probability));

    probability = Math.round(100 * calculator.twoOfAKindForPlayer(playerIndex));
    builder.append(String.format("Two of a Kind: %d%%", probability));


    return builder.toString();
  }
}
