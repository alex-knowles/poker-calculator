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
        allOutcomes.put(Outcome.RoyalFlush, 0.0);
        allOutcomes.put(Outcome.StraightFlush, 0.222222);
        allOutcomes.put(Outcome.FourOfAKind, 0.3333333);
        allOutcomes.put(Outcome.FullHouse, 0.4444);
        allOutcomes.put(Outcome.Flush, 0.5);
        allOutcomes.put(Outcome.Straight, 0.7777);
        allOutcomes.put(Outcome.ThreeOfAKind, 0.8333);
        allOutcomes.put(Outcome.TwoPair, 0.935);
        allOutcomes.put(Outcome.TwoOfAKind, 1.0);
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
