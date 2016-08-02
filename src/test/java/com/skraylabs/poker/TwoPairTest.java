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

public class TwoPairTest {

  @Test
  public void givenLessThanFourCardsReturnsFalse() {
    Collection<Card> cards = new ArrayList<Card>();
    cards.add(new Card(Rank.Ace, Suit.Hearts));
    cards.add(new Card(Rank.Ace, Suit.Spades));
    cards.add(new Card(Rank.King, Suit.Hearts));

    boolean result = ProbabilityCalculator.hasTwoPair(cards);

    assertThat(result, is(false));
  }

  @Test
  public void givenFourUnpairedCardsReturnsFalse() {
    Collection<Card> cards = new ArrayList<Card>();
    cards.add(new Card(Rank.Ace, Suit.Hearts));
    cards.add(new Card(Rank.King, Suit.Hearts));
    cards.add(new Card(Rank.Queen, Suit.Hearts));
    cards.add(new Card(Rank.Jack, Suit.Hearts));

    boolean result = ProbabilityCalculator.hasTwoPair(cards);

    assertThat(result, is(false));
  }

  @Test
  public void givenNotATwoPairReturnsTrue() {
    Collection<Card> cards = new ArrayList<Card>();
    cards.add(new Card(Rank.Ace, Suit.Hearts));
    cards.add(new Card(Rank.Ace, Suit.Spades));
    cards.add(new Card(Rank.King, Suit.Clubs));
    cards.add(new Card(Rank.King, Suit.Diamonds));

    boolean result = ProbabilityCalculator.hasTwoPair(cards);

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
