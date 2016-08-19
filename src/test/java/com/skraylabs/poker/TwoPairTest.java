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

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

public class TwoPairTest {

  private Collection<Card> cards;
  private OutcomeChecker checker;

  @Before
  public void setUp() {
    cards = new ArrayList<>();
  }

  @Test
  public void givenLessThanFourCardsReturnsFalse() {
    cards.add(new Card(Rank.ACE, Suit.HEARTS));
    cards.add(new Card(Rank.ACE, Suit.SPADES));
    cards.add(new Card(Rank.KING, Suit.HEARTS));
    checker = new OutcomeChecker(cards);

    boolean result = checker.hasTwoPair();

    assertThat(result, is(false));
  }

  @Test
  public void givenFourUnpairedCardsReturnsFalse() {
    cards.add(new Card(Rank.ACE, Suit.HEARTS));
    cards.add(new Card(Rank.KING, Suit.HEARTS));
    cards.add(new Card(Rank.QUEEN, Suit.HEARTS));
    cards.add(new Card(Rank.JACK, Suit.HEARTS));
    checker = new OutcomeChecker(cards);

    boolean result = checker.hasTwoPair();

    assertThat(result, is(false));
  }

  @Test
  public void givenNotATwoPairReturnsTrue() {
    cards.add(new Card(Rank.ACE, Suit.HEARTS));
    cards.add(new Card(Rank.ACE, Suit.SPADES));
    cards.add(new Card(Rank.KING, Suit.CLUBS));
    cards.add(new Card(Rank.KING, Suit.DIAMONDS));
    checker = new OutcomeChecker(cards);

    boolean result = checker.hasTwoPair();

    assertThat(result, is(true));
  }

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
