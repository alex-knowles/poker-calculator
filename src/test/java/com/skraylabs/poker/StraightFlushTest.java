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

public class StraightFlushTest {

  @Test
  public void givenABustedStraightFlushReturnsZeroProbability() throws CardFormatException,
      BoardFormatException, PocketFormatException, GameStateFormatException {
    GameState game = GameStateFactory.createGameStateFromString("2s 3s 4s 9c\n 9h 9d");
    OutcomeCalculator calculator = new OutcomeCalculator(game);

    double probability = calculator.straightFlushForPlayer(0);

    assertThat(probability, equalTo(0.0));
  }

  @Test
  public void givenAStraightFlushDrawWithOneChanceReturnsCorrectProbability()
      throws CardFormatException, BoardFormatException, PocketFormatException,
      GameStateFormatException {
    GameState game = GameStateFactory.createGameStateFromString("2s 3s 4s 9c\n 9h 5s");
    OutcomeCalculator calculator = new OutcomeCalculator(game);

    double probability = calculator.straightFlushForPlayer(0);

    assertThat(probability, equalTo(2.0 / 46.0));
  }
}
