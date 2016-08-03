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

public class StraightFlushTest {

  @Test
  public void givenLessThanFiveCardsReturnsFalse() {
    ArrayList<Card> cards = new ArrayList<Card>();
    cards.add(new Card(Rank.Ace, Suit.Clubs));
    cards.add(new Card(Rank.Two, Suit.Clubs));
    cards.add(new Card(Rank.Three, Suit.Clubs));

    boolean result = ProbabilityCalculator.hasStraightFlush(cards);

    assertThat(result, is(false));
  }

  @Test
  public void givenAFlushReturnsFalse() {
    ArrayList<Card> cards = new ArrayList<Card>();
    cards.add(new Card(Rank.Ace, Suit.Clubs));
    cards.add(new Card(Rank.Two, Suit.Clubs));
    cards.add(new Card(Rank.Three, Suit.Clubs));
    cards.add(new Card(Rank.Four, Suit.Clubs));
    cards.add(new Card(Rank.King, Suit.Clubs));

    boolean result = ProbabilityCalculator.hasStraightFlush(cards);

    assertThat(result, is(false));
  }

  @Test
  public void givenAStraightReturnsFalse() {
    ArrayList<Card> cards = new ArrayList<Card>();
    cards.add(new Card(Rank.Ace, Suit.Clubs));
    cards.add(new Card(Rank.Two, Suit.Clubs));
    cards.add(new Card(Rank.Three, Suit.Clubs));
    cards.add(new Card(Rank.Four, Suit.Clubs));
    cards.add(new Card(Rank.Five, Suit.Hearts));

    boolean result = ProbabilityCalculator.hasStraightFlush(cards);

    assertThat(result, is(false));
  }

  @Test
  public void givenAStraightFlushReturnsTrue() {
    ArrayList<Card> cards = new ArrayList<Card>();
    cards.add(new Card(Rank.Ace, Suit.Clubs));
    cards.add(new Card(Rank.Two, Suit.Clubs));
    cards.add(new Card(Rank.Three, Suit.Clubs));
    cards.add(new Card(Rank.Four, Suit.Clubs));
    cards.add(new Card(Rank.Five, Suit.Clubs));

    boolean result = ProbabilityCalculator.hasStraightFlush(cards);

    assertThat(result, is(true));
  }

  @Test
  public void givenABustedStraightFlushReturnsZeroProbability() throws CardFormatException,
      BoardFormatException, PocketFormatException, GameStateFormatException {
    GameState game = GameStateFactory.createGameStateFromString("2s 3s 4s 9c\n 9h 9d");
    ProbabilityCalculator calculator = new ProbabilityCalculator(game);

    double probability = calculator.straightFlushForPlayer(0);

    assertThat(probability, equalTo(0.0));
  }

  @Test
  public void givenAStraightFlushDrawWithOneChanceReturnsCorrectProbability()
      throws CardFormatException, BoardFormatException, PocketFormatException,
      GameStateFormatException {
    GameState game = GameStateFactory.createGameStateFromString("2s 3s 4s 9c\n 9h 5s");
    ProbabilityCalculator calculator = new ProbabilityCalculator(game);

    double probability = calculator.straightFlushForPlayer(0);

    assertThat(probability, equalTo(2.0 / 46.0));
  }
}
