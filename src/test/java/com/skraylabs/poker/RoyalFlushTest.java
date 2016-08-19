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

public class RoyalFlushTest {

  private Collection<Card> cards;
  private OutcomeChecker checker;

  @Before
  public void setUp() {
    cards = new ArrayList<>();
  }

  @Test
  public void givenNotARoyalFlushReturnsFalse() {
    cards.add(new Card(Rank.ACE, Suit.CLUBS));
    cards.add(new Card(Rank.KING, Suit.CLUBS));
    cards.add(new Card(Rank.QUEEN, Suit.CLUBS));
    cards.add(new Card(Rank.JACK, Suit.CLUBS));
    cards.add(new Card(Rank.TWO, Suit.CLUBS));
    checker = new OutcomeChecker(cards);

    boolean result = checker.hasRoyalFlush();

    assertThat(result, is(false));
  }

  @Test
  public void givenAStraightFlushReturnsFalse() {
    cards.add(new Card(Rank.KING, Suit.CLUBS));
    cards.add(new Card(Rank.QUEEN, Suit.CLUBS));
    cards.add(new Card(Rank.JACK, Suit.CLUBS));
    cards.add(new Card(Rank.TEN, Suit.CLUBS));
    cards.add(new Card(Rank.NINE, Suit.CLUBS));
    checker = new OutcomeChecker(cards);

    boolean result = checker.hasRoyalFlush();

    assertThat(result, is(false));
  }

  @Test
  public void givenARoyalFlushReturnsTrue() {
    cards.add(new Card(Rank.ACE, Suit.CLUBS));
    cards.add(new Card(Rank.KING, Suit.CLUBS));
    cards.add(new Card(Rank.QUEEN, Suit.CLUBS));
    cards.add(new Card(Rank.JACK, Suit.CLUBS));
    cards.add(new Card(Rank.TEN, Suit.CLUBS));
    checker = new OutcomeChecker(cards);

    boolean result = checker.hasRoyalFlush();

    assertThat(result, is(true));
  }

  @Test
  public void givenABustedRoyalFlushReturnsZeroProbability() throws CardFormatException,
      BoardFormatException, PocketFormatException, GameStateFormatException {
    GameState game = GameStateFactory.createGameStateFromString("2s 3s 4s 9c\n Kh Kd");
    ProbabilityCalculator calculator = new ProbabilityCalculator(game);

    double probability = calculator.royalFlushForPlayer(0);

    assertThat(probability, equalTo(0.0));
  }

  @Test
  public void givenARoyalFlushDrawWithOneChanceReturnsCorrectProbability()
      throws CardFormatException, BoardFormatException, PocketFormatException,
      GameStateFormatException {
    GameState game = GameStateFactory.createGameStateFromString("Ks Qs 2h\n As Js");
    ProbabilityCalculator calculator = new ProbabilityCalculator(game);

    double probability = calculator.royalFlushForPlayer(0);

    assertThat(probability, equalTo(46.0 / 1081.0));
  }

}
