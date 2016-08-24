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

public class RoyalFlushTest {

  @Test
  public void givenABustedRoyalFlushReturnsZeroProbability() throws CardFormatException,
      BoardFormatException, PocketFormatException, GameStateFormatException {
    GameState game = GameStateFactory.createGameStateFromString("2s 3s 4s 9c\n Kh Kd");
    OutcomeCalculator calculator = new OutcomeCalculator(game);

    double probability = calculator.royalFlushForPlayer(0);

    assertThat(probability, equalTo(0.0));
  }

  @Test
  public void givenARoyalFlushDrawWithOneChanceReturnsCorrectProbability()
      throws CardFormatException, BoardFormatException, PocketFormatException,
      GameStateFormatException {
    GameState game = GameStateFactory.createGameStateFromString("Ks Qs 2h\n As Js");
    OutcomeCalculator calculator = new OutcomeCalculator(game);

    double probability = calculator.royalFlushForPlayer(0);

    assertThat(probability, equalTo(46.0 / 1081.0));
  }

}
