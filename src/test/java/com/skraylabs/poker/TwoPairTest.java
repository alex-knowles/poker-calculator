package com.skraylabs.poker;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import com.skraylabs.poker.model.BoardFormatException;
import com.skraylabs.poker.model.CardFormatException;
import com.skraylabs.poker.model.GameState;
import com.skraylabs.poker.model.GameStateFactory;
import com.skraylabs.poker.model.GameStateFormatException;
import com.skraylabs.poker.model.PocketFormatException;

import org.junit.Test;

public class TwoPairTest {

  @Test
  public void givenBustedHandReturnsZeroProbability() throws BoardFormatException,
      PocketFormatException, GameStateFormatException, CardFormatException {
    GameState game = GameStateFactory.createGameStateFromString("As Kc Qh 9h\n 5s 2c");
    ProbabilityCalculator calculator = new ProbabilityCalculator(game);

    double probability = calculator.twoPairForPlayer(0);

    assertThat(probability, equalTo(0.0));
  }

  @Test
  public void givenAPairAndOneChanceReturnsCorrectProbability() throws BoardFormatException,
      PocketFormatException, GameStateFormatException, CardFormatException {
    GameState game = GameStateFactory.createGameStateFromString("As Kc Qh 9h\n 5s 5c");
    ProbabilityCalculator calculator = new ProbabilityCalculator(game);

    double probability = calculator.twoPairForPlayer(0);

    assertThat(probability, equalTo(12.0 / 46.0));
  }
}
