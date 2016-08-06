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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class ProbabilityCalculatorTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void outOfRangePlayerIndexCausesException() throws CardFormatException,
      BoardFormatException, PocketFormatException, GameStateFormatException {
    exception.expect(IllegalArgumentException.class);
    GameState state = GameStateFactory.createGameStateFromString("Ah Kh Qh Jh Th\n" + "2d 7c");
    ProbabilityCalculator calculator = new ProbabilityCalculator(state);

    calculator.twoOfAKindForPlayer(GameState.MAX_PLAYERS);
  }

  @Test
  public void invalidPlayerIndexYieldsCommunityProbabilityCalculation() throws CardFormatException,
      BoardFormatException, PocketFormatException, GameStateFormatException {
    GameState state = GameStateFactory.createGameStateFromString("Ah Kh Qh Jh\n" + "2d 7c");
    ProbabilityCalculator calculator = new ProbabilityCalculator(state);

    double probability = calculator.twoOfAKindForPlayer(5);

    assertThat(probability, equalTo(12.0 / 46.0));
  }

  @Test
  public void withNoMoreChancesBadHandCalculatesAsZeroProbability() throws CardFormatException,
      BoardFormatException, PocketFormatException, GameStateFormatException {

    GameState state = GameStateFactory.createGameStateFromString("Ah Kh Qh Jh Th\n" + "2d 7c");
    ProbabilityCalculator calculator = new ProbabilityCalculator(state);

    double probability = calculator.twoOfAKindForPlayer(0);

    assertThat(probability, equalTo(0.0));
  }

  @Test
  public void madeHandCalculatesAsFullProbability() throws CardFormatException,
      BoardFormatException, PocketFormatException, GameStateFormatException {
    GameState state = GameStateFactory.createGameStateFromString("Ah Kh Qh Jh Jd\n" + "2d 7c");
    ProbabilityCalculator calculator = new ProbabilityCalculator(state);

    double probability = calculator.twoOfAKindForPlayer(0);

    assertThat(probability, equalTo(1.0));
  }

  @Test
  public void withOneChanceBadHandCalculatesAsSomeProbability() throws CardFormatException,
      BoardFormatException, PocketFormatException, GameStateFormatException {
    GameState state = GameStateFactory.createGameStateFromString("Ah Kh Qh Jh\n" + "2d 7c");
    ProbabilityCalculator calculator = new ProbabilityCalculator(state);

    double probability = calculator.twoOfAKindForPlayer(0);

    assertThat(probability, equalTo(18.0 / 46.0));
  }

  @Test
  public void withTwoChancesBadHandCalculatesAsSomeProbability() throws CardFormatException,
      BoardFormatException, PocketFormatException, GameStateFormatException {
    GameState state = GameStateFactory.createGameStateFromString("Ah Kh Qh\n" + "2d 7c");
    ProbabilityCalculator calculator = new ProbabilityCalculator(state);

    double probability = calculator.twoOfAKindForPlayer(0);

    assertThat(probability, equalTo(633.0 / 1081.0));
  }

  @Test
  public void withFiveChancesBadHandCalculatesAsSomeProbability() throws CardFormatException,
      BoardFormatException, PocketFormatException, GameStateFormatException {
    GameState state = GameStateFactory.createGameStateFromString("\n" + "2d 7c");
    ProbabilityCalculator calculator = new ProbabilityCalculator(state);

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
    ProbabilityCalculator calculator = new ProbabilityCalculator(state);

    double probability = calculator.twoOfAKindForPlayer(0);

    assertThat(probability, equalTo(428.0 / 820.0));
  }

  @Test
  public void withFiveChancesAndBigSlickCalculatingAllOutcomesReturnsCorrectProbabilities()
      throws CardFormatException, BoardFormatException, PocketFormatException,
      GameStateFormatException {
    GameState state = GameStateFactory.createGameStateFromString("\n" + "As Ks");
    ProbabilityCalculator calculator = new ProbabilityCalculator(state);
    final double expectedTotalOutcomes = 2118760;

    Map<Outcome, Double> probabilities = calculator.allOutcomesForAPlayer(0);

    assertThat(probabilities.get(Outcome.RoyalFlush), equalTo(1084 / expectedTotalOutcomes));
    assertThat(probabilities.get(Outcome.StraightFlush), equalTo(1162 / expectedTotalOutcomes));
    assertThat(probabilities.get(Outcome.FourOfAKind), equalTo(2668 / expectedTotalOutcomes));
    assertThat(probabilities.get(Outcome.FullHouse), equalTo(47592 / expectedTotalOutcomes));
    assertThat(probabilities.get(Outcome.Flush), equalTo(139458 / expectedTotalOutcomes));
    assertThat(probabilities.get(Outcome.Straight), equalTo(71920 / expectedTotalOutcomes));
    assertThat(probabilities.get(Outcome.ThreeOfAKind), equalTo(144832 / expectedTotalOutcomes));
    assertThat(probabilities.get(Outcome.TwoPair), equalTo(534672 / expectedTotalOutcomes));
    assertThat(probabilities.get(Outcome.TwoOfAKind), equalTo(1645672 / expectedTotalOutcomes));
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
    ProbabilityCalculator.HandEvaluator evaluator = (someCards) -> false;
    Map<Outcome, ProbabilityCalculator.HandEvaluator> evaluators =
        new HashMap<Outcome, ProbabilityCalculator.HandEvaluator>();
    Outcome arbitraryKey = Outcome.Flush;
    evaluators.put(arbitraryKey, evaluator);

    Map<Outcome, Point> counts = ProbabilityCalculator.countOutcomes(evaluators,
        boardWithThreeCards, pocket, deckWithTenCards);

    Point count = counts.get(arbitraryKey);
    assertThat(count.y, equalTo(45));
  }
}
