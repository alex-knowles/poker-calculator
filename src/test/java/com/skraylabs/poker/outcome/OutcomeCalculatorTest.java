package com.skraylabs.poker.outcome;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
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
import com.skraylabs.poker.outcome.Outcome;
import com.skraylabs.poker.outcome.OutcomeCalculator;
import com.skraylabs.poker.outcome.WinLossCounter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


public class OutcomeCalculatorTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void outOfRangePlayerIndexCausesException() throws CardFormatException,
      BoardFormatException, PocketFormatException, GameStateFormatException {
    exception.expect(IllegalArgumentException.class);
    GameState state = GameStateFactory.createGameStateFromString("Ah Kh Qh Jh Th\n" + "2d 7c");
    OutcomeCalculator calculator = new OutcomeCalculator(state);

    calculator.twoOfAKindForPlayer(GameState.MAX_PLAYERS);
  }

  @Test
  public void invalidPlayerIndexYieldsCommunityProbabilityCalculation() throws CardFormatException,
      BoardFormatException, PocketFormatException, GameStateFormatException {
    GameState state = GameStateFactory.createGameStateFromString("Ah Kh Qh Jh\n" + "2d 7c");
    OutcomeCalculator calculator = new OutcomeCalculator(state);

    double probability = calculator.twoOfAKindForPlayer(5);

    assertThat(probability, equalTo(12.0 / 46.0));
  }

  @Test
  public void withNoMoreChancesBadHandCalculatesAsZeroProbability() throws CardFormatException,
      BoardFormatException, PocketFormatException, GameStateFormatException {

    GameState state = GameStateFactory.createGameStateFromString("Ah Kh Qh Jh Th\n" + "2d 7c");
    OutcomeCalculator calculator = new OutcomeCalculator(state);

    double probability = calculator.twoOfAKindForPlayer(0);

    assertThat(probability, equalTo(0.0));
  }

  @Test
  public void madeHandCalculatesAsFullProbability() throws CardFormatException,
      BoardFormatException, PocketFormatException, GameStateFormatException {
    GameState state = GameStateFactory.createGameStateFromString("Ah Kh Qh Jh Jd\n" + "2d 7c");
    OutcomeCalculator calculator = new OutcomeCalculator(state);

    double probability = calculator.twoOfAKindForPlayer(0);

    assertThat(probability, equalTo(1.0));
  }

  @Test
  public void withOneChanceBadHandCalculatesAsSomeProbability() throws CardFormatException,
      BoardFormatException, PocketFormatException, GameStateFormatException {
    GameState state = GameStateFactory.createGameStateFromString("Ah Kh Qh Jh\n" + "2d 7c");
    OutcomeCalculator calculator = new OutcomeCalculator(state);

    double probability = calculator.twoOfAKindForPlayer(0);

    assertThat(probability, equalTo(18.0 / 46.0));
  }

  @Test
  public void withTwoChancesBadHandCalculatesAsSomeProbability() throws CardFormatException,
      BoardFormatException, PocketFormatException, GameStateFormatException {
    GameState state = GameStateFactory.createGameStateFromString("Ah Kh Qh\n" + "2d 7c");
    OutcomeCalculator calculator = new OutcomeCalculator(state);

    double probability = calculator.twoOfAKindForPlayer(0);

    assertThat(probability, equalTo(633.0 / 1081.0));
  }

  @Test
  public void withFiveChancesBadHandCalculatesAsSomeProbability() throws CardFormatException,
      BoardFormatException, PocketFormatException, GameStateFormatException {
    GameState state = GameStateFactory.createGameStateFromString("\n" + "2d 7c");
    OutcomeCalculator calculator = new OutcomeCalculator(state);

    double probability = calculator.twoOfAKindForPlayer(0);

    assertTrue(probability > 0);
    assertTrue(probability < 1.0);
  }

  @Test
  public void withTwoChancesAndFourPlayersBadHandCalculatesAsWorseProbability()
      throws CardFormatException, BoardFormatException, PocketFormatException,
      GameStateFormatException {
    GameState state = GameStateFactory
        .createGameStateFromString("Ah Kh Qh\n" + "2d 7c\n" + "7h 7d\n" + "Ad Kc\n" + "8c 8d");
    OutcomeCalculator calculator = new OutcomeCalculator(state);

    double probability = calculator.twoOfAKindForPlayer(0);

    assertThat(probability, equalTo(428.0 / 820.0));
  }

  @Test
  public void withFiveChancesAndBigSlickCalculatingAllOutcomesReturnsProbabilitiesForEachOutcome()
      throws CardFormatException, BoardFormatException, PocketFormatException,
      GameStateFormatException {
    GameState state = GameStateFactory.createGameStateFromString("Qs Js 8h\n" + "As Ks");
    OutcomeCalculator calculator = new OutcomeCalculator(state);

    Map<Outcome, Double> probabilities = calculator.allOutcomesForAPlayer(0);

    assertThat(probabilities.get(Outcome.ROYAL_FLUSH), is(notNullValue()));
    assertThat(probabilities.get(Outcome.STRAIGHT_FLUSH), is(notNullValue()));
    assertThat(probabilities.get(Outcome.FOUR_OF_A_KIND), is(notNullValue()));
    assertThat(probabilities.get(Outcome.FULL_HOUSE), is(notNullValue()));
    assertThat(probabilities.get(Outcome.FLUSH), is(notNullValue()));
    assertThat(probabilities.get(Outcome.STRAIGHT), is(notNullValue()));
    assertThat(probabilities.get(Outcome.THREE_OF_A_KIND), is(notNullValue()));
    assertThat(probabilities.get(Outcome.TWO_PAIR), is(notNullValue()));
    assertThat(probabilities.get(Outcome.TWO_OF_A_KIND), is(notNullValue()));
  }

  @Test
  public void tenChooseTwoYieldsFortyFiveCombinations() {
    Card[] cards = new Card[15];
    for (int i = 0; i < cards.length; ++i) {
      cards[i] = CardUtils.cardFromNumber(i);
    }
    Collection<Card> boardWithThreeCards = new ArrayList<>();
    for (int i = 0; i < 3; ++i) {
      boardWithThreeCards.add(cards[i]);
    }
    Collection<Card> pocket = new ArrayList<>();
    pocket.add(cards[3]);
    pocket.add(cards[4]);
    Collection<Card> deckWithTenCards = new ArrayList<>();
    for (int i = 5; i < cards.length; ++i) {
      deckWithTenCards.add(cards[i]);
    }
    Collection<Outcome> outcomes = new ArrayList<>();
    Outcome arbitraryOutcome = Outcome.FLUSH;
    outcomes.add(arbitraryOutcome);

    Map<Outcome, WinLossCounter> counts = OutcomeCalculator.countOutcomes(outcomes,
        boardWithThreeCards, pocket, deckWithTenCards);

    WinLossCounter count = counts.get(arbitraryOutcome);
    assertThat(count.getCountTotal(), equalTo(45));
  }
}
