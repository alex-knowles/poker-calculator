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

public class FullHouseTest {

  @Test
  public void givenBustedHandReturnsZeroProbability() throws CardFormatException,
      BoardFormatException, PocketFormatException, GameStateFormatException {
    GameState game = GameStateFactory.createGameStateFromString("Ah 2h 3h\n Kc Qc");
    OutcomeCalculator calculator = new OutcomeCalculator(game);

    double probability = calculator.fullHouseForPlayer(0);

    assertThat(probability, equalTo(0.0));
  }

  @Test
  public void givenTwoPairWithTwoChancesReturnsCorrectProbability() throws CardFormatException,
      BoardFormatException, PocketFormatException, GameStateFormatException {
    GameState game = GameStateFactory.createGameStateFromString("Ah Kh Qh\n Kc Qc");
    OutcomeCalculator calculator = new OutcomeCalculator(game);

    double probability = calculator.fullHouseForPlayer(0);

    assertThat(probability, equalTo(181.0 / 1081.0));
  }
}
