package com.skraylabs.poker;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import com.skraylabs.poker.model.Board;
import com.skraylabs.poker.model.Card;
import com.skraylabs.poker.model.Pocket;

import java.util.ArrayList;

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

  /**
   * Helper method that constructs a {@link Board} from an array of {@link Card} objects.
   *
   * @param cards an array of 0, 3, 4, or 5 Cards
   * @return a board composed from {@code cards}
   */
  static Board toBoard(ArrayList<Card> cards) {
    final int size = cards.size();
    // Sanity check
    if (size > 5) {
      return null;
    }

    Board result = null;
    if (size == 0) {
      result = new Board();
    }
    if (size >= 3) {
      result = new Board(cards.get(0), cards.get(1), cards.get(2));
    }
    if (size >= 4) {
      result.turnCard = cards.get(3);
    }
    if (size == 5) {
      result.riverCard = cards.get(4);
    }
    return result;
  }

  /**
   * Helper method that constructs a {@link Pocket} from an array of {@link Card} objects.
   *
   * @param cards an array of 0 or 2 Cards
   * @return a pocket composed from {@code cards}
   */
  static Pocket toPocket(ArrayList<Card> cards) {
    Pocket result = null;
    if (cards.size() == 0) {
      result = new Pocket();
    }
    if (cards.size() == 2) {
      result = new Pocket(cards.get(0), cards.get(1));
    }
    return result;
  }
}
