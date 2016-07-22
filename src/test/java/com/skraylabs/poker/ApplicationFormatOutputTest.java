package com.skraylabs.poker;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class ApplicationFormatOutputTest {

  @Before
  public void setUp() throws Exception {}

  @Test
  public void probabilityCalculationsAreFormattedNicely() {
    ProbabilityCalculator calculator = new ProbabilityCalculator(null) {
      @Override
      public double royalFlushForPlayer(int playerIndex) {
        return 0.0;
      }

      @Override
      public double straightFlushForPlayer(int playerIndex) {
        return 0.222222;
      }

      @Override
      public double fourOfAKindForPlayer(int playerIndex) {
        return 0.3333333;
      }

      @Override
      public double fullHouseForPlayer(int playerIndex) {
        return 0.4444;
      }

      @Override
      public double flushForPlayer(int playerIndex) {
        return 0.5;
      }

      @Override
      public double straightForPlayer(int playerIndex) {
        return 0.7777;
      }

      @Override
      public double threeOfAKindForPlayer(int playerIndex) {
        return 0.8333;
      }

      @Override
      public double twoPairForPlayer(int playerIndex) {
        return 0.935;
      }

      @Override
      public double twoOfAKindForPlayer(int playerIndex) {
        return 1.0;
      }
    };
    String output = Application.formatOutputForPlayer(calculator, 0);

    String expectedOutput = "Royal Flush: 0%\n"
        + "Straight Flush: 22%\n"
        + "Four of a Kind: 33%\n"
        + "Full House: 44%\n"
        + "Flush: 50%\n"
        + "Straight: 78%\n"
        + "Three of a Kind: 83%\n"
        + "Two Pair: 94%\n"
        + "Two of a Kind: 100%";
    assertThat(output, equalTo(expectedOutput));
  }

}
