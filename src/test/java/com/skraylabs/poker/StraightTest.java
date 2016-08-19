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

public class StraightTest {

  private Collection<Card> cards;
  private OutcomeChecker checker;

  @Before
  public void setUp() throws Exception {
    cards = new ArrayList<Card>();
  }

  @Test
  public void givenLessThanFiveCardsReturnsFalse() {
    cards.add(new Card(Rank.ACE, Suit.CLUBS));
    cards.add(new Card(Rank.TWO, Suit.CLUBS));
    cards.add(new Card(Rank.THREE, Suit.CLUBS));
    checker = new OutcomeChecker(cards);

    boolean result = checker.hasStraight();

    assertThat(result, is(false));
  }

  @Test
  public void givenNotAStraightReturnsFalse() {
    cards.add(new Card(Rank.ACE, Suit.CLUBS));
    cards.add(new Card(Rank.TWO, Suit.CLUBS));
    cards.add(new Card(Rank.THREE, Suit.CLUBS));
    cards.add(new Card(Rank.FOUR, Suit.CLUBS));
    cards.add(new Card(Rank.KING, Suit.CLUBS));
    checker = new OutcomeChecker(cards);

    boolean result = checker.hasStraight();

    assertThat(result, is(false));
  }

  @Test
  public void givenNotAStraightWithDuplicateRanksReturnsFalse() {
    cards.add(new Card(Rank.ACE, Suit.CLUBS));
    cards.add(new Card(Rank.TWO, Suit.CLUBS));
    cards.add(new Card(Rank.THREE, Suit.CLUBS));
    cards.add(new Card(Rank.FOUR, Suit.CLUBS));
    cards.add(new Card(Rank.FOUR, Suit.SPADES));
    checker = new OutcomeChecker(cards);

    boolean result = checker.hasStraight();

    assertThat(result, is(false));
  }

  @Test
  public void givenAStraightReturnsTrue() {
    cards.add(new Card(Rank.ACE, Suit.CLUBS));
    cards.add(new Card(Rank.TWO, Suit.CLUBS));
    cards.add(new Card(Rank.THREE, Suit.CLUBS));
    cards.add(new Card(Rank.FOUR, Suit.CLUBS));
    cards.add(new Card(Rank.FIVE, Suit.CLUBS));
    checker = new OutcomeChecker(cards);

    boolean result = checker.hasStraight();

    assertThat(result, is(true));
  }

  @Test
  public void givenAHighAceStraightReturnsTrue() {
    cards.add(new Card(Rank.ACE, Suit.CLUBS));
    cards.add(new Card(Rank.KING, Suit.CLUBS));
    cards.add(new Card(Rank.QUEEN, Suit.CLUBS));
    cards.add(new Card(Rank.JACK, Suit.CLUBS));
    cards.add(new Card(Rank.TEN, Suit.CLUBS));
    checker = new OutcomeChecker(cards);

    boolean result = checker.hasStraight();

    assertThat(result, is(true));
  }

  @Test
  public void givenABustedStraightReturnsZeroProbability() throws CardFormatException,
      BoardFormatException, PocketFormatException, GameStateFormatException {
    GameState game = GameStateFactory.createGameStateFromString("2s 3s 4s 9c\n 9h 9d");
    ProbabilityCalculator calculator = new ProbabilityCalculator(game);

    double probability = calculator.straightForPlayer(0);

    assertThat(probability, equalTo(0.0));
  }

  @Test
  public void givenAStraightDrawWithOneChanceReturnsCorrectProbability() throws CardFormatException,
      BoardFormatException, PocketFormatException, GameStateFormatException {
    GameState game = GameStateFactory.createGameStateFromString("2s 3s 4s 9c\n 9h 5d");
    ProbabilityCalculator calculator = new ProbabilityCalculator(game);

    double probability = calculator.straightForPlayer(0);

    assertThat(probability, equalTo(8.0 / 46.0));
  }
}
