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

public class RoyalFlushTest {

  @Test
  public void givenNotARoyalFlushReturnsFalse() {
    ArrayList<Card> cards = new ArrayList<>();
    cards.add(new Card(Rank.Ace, Suit.Clubs));
    cards.add(new Card(Rank.King, Suit.Clubs));
    cards.add(new Card(Rank.Queen, Suit.Clubs));
    cards.add(new Card(Rank.Jack, Suit.Clubs));
    cards.add(new Card(Rank.Two, Suit.Clubs));

    boolean result = ProbabilityCalculator.hasRoyalFlush(cards);

    assertThat(result, is(false));
  }

  @Test
  public void givenAStraightFlushReturnsFalse() {
    ArrayList<Card> cards = new ArrayList<>();
    cards.add(new Card(Rank.King, Suit.Clubs));
    cards.add(new Card(Rank.Queen, Suit.Clubs));
    cards.add(new Card(Rank.Jack, Suit.Clubs));
    cards.add(new Card(Rank.Ten, Suit.Clubs));
    cards.add(new Card(Rank.Nine, Suit.Clubs));

    boolean result = ProbabilityCalculator.hasRoyalFlush(cards);

    assertThat(result, is(false));
  }

  @Test
  public void givenARoyalFlushReturnsTrue() {
    ArrayList<Card> cards = new ArrayList<>();
    cards.add(new Card(Rank.Ace, Suit.Clubs));
    cards.add(new Card(Rank.King, Suit.Clubs));
    cards.add(new Card(Rank.Queen, Suit.Clubs));
    cards.add(new Card(Rank.Jack, Suit.Clubs));
    cards.add(new Card(Rank.Ten, Suit.Clubs));

    boolean result = ProbabilityCalculator.hasRoyalFlush(cards);

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
