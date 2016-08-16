package com.skraylabs.poker;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ApplicationFormatOutputTest {

  @Before
  public void setUp() throws Exception {}

  @Test
  public void probabilityCalculationsAreFormattedNicely() {
    ProbabilityCalculator calculator = new ProbabilityCalculator(null) {
      @Override
      public Map<Outcome, Double> allOutcomesForAPlayer(int playerIndex) {
        HashMap<Outcome, Double> allOutcomes = new HashMap<>();
        allOutcomes.put(Outcome.ROYAL_FLUSH, 0.0);
        allOutcomes.put(Outcome.STRAIGHT_FLUSH, 0.222222);
        allOutcomes.put(Outcome.FOUR_OF_A_KIND, 0.3333333);
        allOutcomes.put(Outcome.FULL_HOUSE, 0.4444);
        allOutcomes.put(Outcome.FLUSH, 0.5);
        allOutcomes.put(Outcome.STRAIGHT, 0.7777);
        allOutcomes.put(Outcome.THREE_OF_A_KIND, 0.8333);
        allOutcomes.put(Outcome.TWO_PAIR, 0.935);
        allOutcomes.put(Outcome.TWO_OF_A_KIND, 1.0);
        return allOutcomes;
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
