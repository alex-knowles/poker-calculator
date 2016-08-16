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

public class StraightTest {

  @Before
  public void setUp() throws Exception {}

  @Test
  public void givenLessThanFiveCardsReturnsFalse() {
    ArrayList<Card> cards = new ArrayList<>();
    cards.add(new Card(Rank.ACE, Suit.Clubs));
    cards.add(new Card(Rank.TWO, Suit.Clubs));
    cards.add(new Card(Rank.THREE, Suit.Clubs));

    boolean result = ProbabilityCalculator.hasStraight(cards);

    assertThat(result, is(false));
  }

  @Test
  public void givenNotAStraightReturnsFalse() {
    ArrayList<Card> cards = new ArrayList<>();
    cards.add(new Card(Rank.ACE, Suit.Clubs));
    cards.add(new Card(Rank.TWO, Suit.Clubs));
    cards.add(new Card(Rank.THREE, Suit.Clubs));
    cards.add(new Card(Rank.FOUR, Suit.Clubs));
    cards.add(new Card(Rank.KING, Suit.Clubs));

    boolean result = ProbabilityCalculator.hasStraight(cards);

    assertThat(result, is(false));
  }

  @Test
  public void givenAStraightReturnsTrue() {
    ArrayList<Card> cards = new ArrayList<>();
    cards.add(new Card(Rank.ACE, Suit.Clubs));
    cards.add(new Card(Rank.TWO, Suit.Clubs));
    cards.add(new Card(Rank.THREE, Suit.Clubs));
    cards.add(new Card(Rank.FOUR, Suit.Clubs));
    cards.add(new Card(Rank.FIVE, Suit.Clubs));

    boolean result = ProbabilityCalculator.hasStraight(cards);

    assertThat(result, is(true));
  }

  @Test
  public void givenAHighAceStraightReturnsTrue() {
    ArrayList<Card> cards = new ArrayList<>();
    cards.add(new Card(Rank.ACE, Suit.Clubs));
    cards.add(new Card(Rank.KING, Suit.Clubs));
    cards.add(new Card(Rank.QUEEN, Suit.Clubs));
    cards.add(new Card(Rank.JACK, Suit.Clubs));
    cards.add(new Card(Rank.TEN, Suit.Clubs));

    boolean result = ProbabilityCalculator.hasStraight(cards);

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
