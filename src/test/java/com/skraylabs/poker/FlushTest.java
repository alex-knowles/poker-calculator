package com.skraylabs.poker;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.skraylabs.poker.model.BoardFormatException;
import com.skraylabs.poker.model.Card;
import com.skraylabs.poker.model.CardFormatException;
import com.skraylabs.poker.model.GameState;
import com.skraylabs.poker.model.GameStateFactory;
import com.skraylabs.poker.model.GameStateFormatException;
import com.skraylabs.poker.model.PocketFormatException;
import com.skraylabs.poker.model.Rank;
import com.skraylabs.poker.model.Suit;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

public class FlushTest {

  @Test
  public void givenLessThanFiveOfASuitReturnsFalse() {
    Collection<Card> cards = new ArrayList<>();
    cards.add(new Card(Rank.ACE, Suit.HEARTS));
    cards.add(new Card(Rank.TWO, Suit.HEARTS));
    cards.add(new Card(Rank.THREE, Suit.HEARTS));
    cards.add(new Card(Rank.FOUR, Suit.HEARTS));

    boolean result = ProbabilityCalculator.hasFlush(cards);

    assertThat(result, is(false));
  }

  @Test
  public void givenFiveOfASuitReturnsTrue() {
    Collection<Card> cards = new ArrayList<>();
    cards.add(new Card(Rank.ACE, Suit.HEARTS));
    cards.add(new Card(Rank.TWO, Suit.HEARTS));
    cards.add(new Card(Rank.THREE, Suit.HEARTS));
    cards.add(new Card(Rank.FOUR, Suit.HEARTS));
    cards.add(new Card(Rank.FIVE, Suit.HEARTS));

    boolean result = ProbabilityCalculator.hasFlush(cards);

    assertThat(result, is(true));
  }

  @Test
  public void givenBustedHandReturnsZeroProbability() throws BoardFormatException,
      PocketFormatException, GameStateFormatException, CardFormatException {
    GameState game = GameStateFactory.createGameStateFromString("As Ac Ah Kh\n Ts Js");
    ProbabilityCalculator calculator = new ProbabilityCalculator(game);

    double probability = calculator.flushForPlayer(0);

    assertThat(probability, equalTo(0.0));
  }

  @Test
  public void givenFlushDrawAndTwoChancesReturnsCorrectProbability() throws BoardFormatException,
      PocketFormatException, GameStateFormatException, CardFormatException {
    GameState game = GameStateFactory.createGameStateFromString("As 2s Kh\n 4s 5s");
    ProbabilityCalculator calculator = new ProbabilityCalculator(game);

    double probability = calculator.flushForPlayer(0);

    assertThat(probability, equalTo(378.0 / 1081.0));
  }
}
