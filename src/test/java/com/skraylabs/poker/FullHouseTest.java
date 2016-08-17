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

public class FullHouseTest {

  @Test
  public void givenLessThanFiveCardsReturnsFalse() {
    ArrayList<Card> cards = new ArrayList<>();
    cards.add(new Card(Rank.ACE, Suit.CLUBS));
    cards.add(new Card(Rank.TWO, Suit.CLUBS));
    cards.add(new Card(Rank.THREE, Suit.CLUBS));

    boolean result = ProbabilityCalculator.hasFullHouse(cards);

    assertThat(result, is(false));
  }

  @Test
  public void givenNotAFullHouseReturnsFalse() {
    ArrayList<Card> cards = new ArrayList<>();
    cards.add(new Card(Rank.ACE, Suit.CLUBS));
    cards.add(new Card(Rank.ACE, Suit.SPADES));
    cards.add(new Card(Rank.THREE, Suit.CLUBS));
    cards.add(new Card(Rank.THREE, Suit.SPADES));
    cards.add(new Card(Rank.TEN, Suit.SPADES));
    cards.add(new Card(Rank.JACK, Suit.SPADES));
    cards.add(new Card(Rank.KING, Suit.SPADES));

    boolean result = ProbabilityCalculator.hasFullHouse(cards);

    assertThat(result, is(false));
  }

  @Test
  public void givenAFullHouseReturnsTrue() {
    ArrayList<Card> cards = new ArrayList<>();
    cards.add(new Card(Rank.ACE, Suit.CLUBS));
    cards.add(new Card(Rank.ACE, Suit.SPADES));
    cards.add(new Card(Rank.THREE, Suit.CLUBS));
    cards.add(new Card(Rank.THREE, Suit.SPADES));
    cards.add(new Card(Rank.TEN, Suit.SPADES));
    cards.add(new Card(Rank.JACK, Suit.SPADES));
    cards.add(new Card(Rank.ACE, Suit.HEARTS));

    boolean result = ProbabilityCalculator.hasFullHouse(cards);

    assertThat(result, is(true));
  }

  @Test
  public void givenBustedHandReturnsZeroProbability() throws CardFormatException,
      BoardFormatException, PocketFormatException, GameStateFormatException {
    GameState game = GameStateFactory.createGameStateFromString("Ah 2h 3h\n Kc Qc");
    ProbabilityCalculator calculator = new ProbabilityCalculator(game);

    double probability = calculator.fullHouseForPlayer(0);

    assertThat(probability, equalTo(0.0));
  }

  @Test
  public void givenTwoPairWithTwoChancesReturnsCorrectProbability() throws CardFormatException,
      BoardFormatException, PocketFormatException, GameStateFormatException {
    GameState game = GameStateFactory.createGameStateFromString("Ah Kh Qh\n Kc Qc");
    ProbabilityCalculator calculator = new ProbabilityCalculator(game);

    double probability = calculator.fullHouseForPlayer(0);

    assertThat(probability, equalTo(181.0 / 1081.0));
  }
}
