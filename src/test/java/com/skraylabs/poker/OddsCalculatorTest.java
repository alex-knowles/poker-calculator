package com.skraylabs.poker;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import com.skraylabs.poker.model.BoardFormatException;
import com.skraylabs.poker.model.Card;
import com.skraylabs.poker.model.CardFormatException;
import com.skraylabs.poker.model.CardUtils;
import com.skraylabs.poker.model.GameState;
import com.skraylabs.poker.model.GameStateFactory;
import com.skraylabs.poker.model.GameStateFormatException;
import com.skraylabs.poker.model.PocketFormatException;

import org.junit.Test;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;

public class OddsCalculatorTest {

  @Test
  public void onTheRiverYieldsZeroProbabilityOfTwoOfAKind() throws CardFormatException,
      BoardFormatException, PocketFormatException, GameStateFormatException {

    GameState state = GameStateFactory.createGameStateFromString("Ah Kh Qh Jh Th\n" + "2d 7c");
    OddsCalculator calculator = new OddsCalculator(state);

    double probability = calculator.twoOfAKindForPlayer(0);

    assertThat(probability, equalTo(0.0));
  }

  @Test
  public void onTheRiverYieldsFullProbabilityOfTwoOfAKind() throws CardFormatException,
      BoardFormatException, PocketFormatException, GameStateFormatException {
    GameState state = GameStateFactory.createGameStateFromString("Ah Kh Qh Jh Jd\n" + "2d 7c");
    OddsCalculator calculator = new OddsCalculator(state);

    double probability = calculator.twoOfAKindForPlayer(0);

    assertThat(probability, equalTo(1.0));
  }

  @Test
  public void onTheTurnYieldsPartialProbabilityOfTwoOfAKind() throws CardFormatException,
      BoardFormatException, PocketFormatException, GameStateFormatException {
    GameState state = GameStateFactory.createGameStateFromString("Ah Kh Qh Jh\n" + "2d 7c");
    OddsCalculator calculator = new OddsCalculator(state);

    double probability = calculator.twoOfAKindForPlayer(0);

    assertThat(probability, equalTo(18.0 / 46.0));
  }

  @Test
  public void onTheFlopYieldsPartialProbabilityOfTwoOfAKind() throws CardFormatException,
      BoardFormatException, PocketFormatException, GameStateFormatException {
    GameState state = GameStateFactory.createGameStateFromString("Ah Kh Qh\n" + "2d 7c");
    OddsCalculator calculator = new OddsCalculator(state);

    double probability = calculator.twoOfAKindForPlayer(0);

    assertThat(probability, equalTo(633.0 / 1081.0));
  }

  @Test
  public void preFlopYieldsPartialProbabilityOfTwoOfAKind() throws CardFormatException,
      BoardFormatException, PocketFormatException, GameStateFormatException {
    GameState state = GameStateFactory.createGameStateFromString("\n" + "2d 7c");
    OddsCalculator calculator = new OddsCalculator(state);

    double probability = calculator.twoOfAKindForPlayer(0);

    assertTrue(probability > 0);
    assertTrue(probability < 1.0);
  }

  @Test
  public void tenChooseTwoYieldsFortyFiveCombinations() {
    Card[] cards = new Card[15];
    for (int i = 0; i < cards.length; ++i) {
      cards[i] = CardUtils.cardFromNumber(i);
    }
    Collection<Card> boardWithThreeCards = new ArrayList<Card>();
    for (int i = 0; i < 3; ++i) {
      boardWithThreeCards.add(cards[i]);
    }
    Collection<Card> pocket = new ArrayList<Card>();
    pocket.add(cards[3]);
    pocket.add(cards[4]);
    Collection<Card> deckWithTenCards = new ArrayList<Card>();
    for (int i = 5; i < cards.length; ++i) {
      deckWithTenCards.add(cards[i]);
    }

    Point count =
        OddsCalculator.countTwoOfAKindOutcomes(boardWithThreeCards, pocket, deckWithTenCards);

    assertThat(count.y, equalTo(45));
  }
}
