package com.skraylabs.poker.outcome;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import com.skraylabs.poker.model.BoardFormatException;
import com.skraylabs.poker.model.CardFormatException;
import com.skraylabs.poker.model.GameState;
import com.skraylabs.poker.model.GameStateFactory;
import com.skraylabs.poker.model.GameStateFormatException;
import com.skraylabs.poker.model.PocketFormatException;
import com.skraylabs.poker.outcome.OutcomeCalculator;

import org.junit.Test;

public class StraightTest {

  @Test
  public void givenABustedStraightReturnsZeroProbability() throws CardFormatException,
      BoardFormatException, PocketFormatException, GameStateFormatException {
    GameState game = GameStateFactory.createGameStateFromString("2s 3s 4s 9c\n 9h 9d");
    OutcomeCalculator calculator = new OutcomeCalculator(game);

    double probability = calculator.straightForPlayer(0);

    assertThat(probability, equalTo(0.0));
  }

  @Test
  public void givenAStraightDrawWithOneChanceReturnsCorrectProbability() throws CardFormatException,
      BoardFormatException, PocketFormatException, GameStateFormatException {
    GameState game = GameStateFactory.createGameStateFromString("2s 3s 4s 9c\n 9h 5d");
    OutcomeCalculator calculator = new OutcomeCalculator(game);

    double probability = calculator.straightForPlayer(0);

    assertThat(probability, equalTo(8.0 / 46.0));
  }
}
