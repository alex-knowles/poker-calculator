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
import com.skraylabs.poker.model.Rank;
import com.skraylabs.poker.model.Suit;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;

public class ProbabilityCalculatorTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  Card aceOfSpades;
  Card aceOfHearts;
  Card aceOfClubs;
  Card aceOfDiamonds;

  Card kingOfSpades;
  Card kingOfHearts;
  Card kingOfClubs;
  Card kingOfDiamonds;

  Card queenOfSpades;
  Card queenOfHearts;
  Card queenOfClubs;
  Card queenOfDiamonds;

  Card jackOfSpades;
  Card jackOfHearts;
  Card jackOfClubs;
  Card jackOfDiamonds;

  Card tenOfSpades;
  Card tenOfHearts;
  Card tenOfClubs;
  Card tenOfDiamonds;

  Card nineOfSpades;
  Card nineOfHearts;
  Card nineOfClubs;
  Card nineOfDiamonds;

  Card eightOfSpades;
  Card eightOfHearts;
  Card eightOfClubs;
  Card eightOfDiamonds;

  Card sevenOfSpades;
  Card sevenOfHearts;
  Card sevenOfClubs;
  Card sevenOfDiamonds;

  Card sixOfSpades;
  Card sixOfHearts;
  Card sixOfClubs;
  Card sixOfDiamonds;

  Card fiveOfSpades;
  Card fiveOfHearts;
  Card fiveOfClubs;
  Card fiveOfDiamonds;

  Card fourOfSpades;
  Card fourOfHearts;
  Card fourOfClubs;
  Card fourOfDiamonds;

  Card threeOfSpades;
  Card threeOfHearts;
  Card threeOfClubs;
  Card threeOfDiamonds;

  Card twoOfSpades;
  Card twoOfHearts;
  Card twoOfClubs;
  Card twoOfDiamonds;

  /**
   * Construct all 52 cards.
   */
  @Before
  public void initCards() {
    aceOfSpades = new Card(Rank.Ace, Suit.Spades);
    aceOfHearts = new Card(Rank.Ace, Suit.Hearts);
    aceOfClubs = new Card(Rank.Ace, Suit.Clubs);
    aceOfDiamonds = new Card(Rank.Ace, Suit.Diamonds);

    kingOfSpades = new Card(Rank.King, Suit.Spades);
    kingOfHearts = new Card(Rank.King, Suit.Hearts);
    kingOfClubs = new Card(Rank.King, Suit.Clubs);
    kingOfDiamonds = new Card(Rank.King, Suit.Diamonds);

    kingOfSpades = new Card(Rank.King, Suit.Spades);
    kingOfHearts = new Card(Rank.King, Suit.Hearts);
    kingOfClubs = new Card(Rank.King, Suit.Clubs);
    kingOfDiamonds = new Card(Rank.King, Suit.Diamonds);

    kingOfSpades = new Card(Rank.King, Suit.Spades);
    kingOfHearts = new Card(Rank.King, Suit.Hearts);
    kingOfClubs = new Card(Rank.King, Suit.Clubs);
    kingOfDiamonds = new Card(Rank.King, Suit.Diamonds);

    queenOfSpades = new Card(Rank.Queen, Suit.Spades);
    queenOfHearts = new Card(Rank.Queen, Suit.Hearts);
    queenOfClubs = new Card(Rank.Queen, Suit.Clubs);
    queenOfDiamonds = new Card(Rank.Queen, Suit.Diamonds);

    jackOfSpades = new Card(Rank.Jack, Suit.Spades);
    jackOfHearts = new Card(Rank.Jack, Suit.Hearts);
    jackOfClubs = new Card(Rank.Jack, Suit.Clubs);
    jackOfDiamonds = new Card(Rank.Jack, Suit.Diamonds);

    tenOfSpades = new Card(Rank.Ten, Suit.Spades);
    tenOfHearts = new Card(Rank.Ten, Suit.Hearts);
    tenOfClubs = new Card(Rank.Ten, Suit.Clubs);
    tenOfDiamonds = new Card(Rank.Ten, Suit.Diamonds);

    nineOfSpades = new Card(Rank.Nine, Suit.Spades);
    nineOfHearts = new Card(Rank.Nine, Suit.Hearts);
    nineOfClubs = new Card(Rank.Nine, Suit.Clubs);
    nineOfDiamonds = new Card(Rank.Nine, Suit.Diamonds);

    eightOfSpades = new Card(Rank.Eight, Suit.Spades);
    eightOfHearts = new Card(Rank.Eight, Suit.Hearts);
    eightOfClubs = new Card(Rank.Eight, Suit.Clubs);
    eightOfDiamonds = new Card(Rank.Eight, Suit.Diamonds);

    sevenOfSpades = new Card(Rank.Seven, Suit.Spades);
    sevenOfHearts = new Card(Rank.Seven, Suit.Hearts);
    sevenOfClubs = new Card(Rank.Seven, Suit.Clubs);
    sevenOfDiamonds = new Card(Rank.Seven, Suit.Diamonds);

    sixOfSpades = new Card(Rank.Six, Suit.Spades);
    sixOfHearts = new Card(Rank.Six, Suit.Hearts);
    sixOfClubs = new Card(Rank.Six, Suit.Clubs);
    sixOfDiamonds = new Card(Rank.Six, Suit.Diamonds);

    fiveOfSpades = new Card(Rank.Five, Suit.Spades);
    fiveOfHearts = new Card(Rank.Five, Suit.Hearts);
    fiveOfClubs = new Card(Rank.Five, Suit.Clubs);
    fiveOfDiamonds = new Card(Rank.Five, Suit.Diamonds);

    fourOfSpades = new Card(Rank.Four, Suit.Spades);
    fourOfHearts = new Card(Rank.Four, Suit.Hearts);
    fourOfClubs = new Card(Rank.Four, Suit.Clubs);
    fourOfDiamonds = new Card(Rank.Four, Suit.Diamonds);

    threeOfSpades = new Card(Rank.Three, Suit.Spades);
    threeOfHearts = new Card(Rank.Three, Suit.Hearts);
    threeOfClubs = new Card(Rank.Three, Suit.Clubs);
    threeOfDiamonds = new Card(Rank.Three, Suit.Diamonds);

    twoOfSpades = new Card(Rank.Two, Suit.Spades);
    twoOfHearts = new Card(Rank.Two, Suit.Hearts);
    twoOfClubs = new Card(Rank.Two, Suit.Clubs);
    twoOfDiamonds = new Card(Rank.Two, Suit.Diamonds);
  }

  @Test
  public void outOfRangePlayerIndexCausesException() throws CardFormatException,
      BoardFormatException, PocketFormatException, GameStateFormatException {
    exception.expect(IllegalArgumentException.class);
    GameState state = GameStateFactory.createGameStateFromString("Ah Kh Qh Jh Th\n" + "2d 7c");
    ProbabilityCalculator calculator = new ProbabilityCalculator(state);

    calculator.twoOfAKindForPlayer(GameState.MAX_PLAYERS);
  }

  @Test
  public void invalidPlayerIndexYieldsCommunityProbabilityForTwoOfAKind()
      throws CardFormatException, BoardFormatException, PocketFormatException,
      GameStateFormatException {
    GameState state = GameStateFactory.createGameStateFromString("Ah Kh Qh Jh\n" + "2d 7c");
    ProbabilityCalculator calculator = new ProbabilityCalculator(state);

    double probability = calculator.twoOfAKindForPlayer(5);

    assertThat(probability, equalTo(12.0 / 46.0));
  }

  @Test
  public void onTheRiverYieldsZeroProbabilityOfTwoOfAKind() throws CardFormatException,
      BoardFormatException, PocketFormatException, GameStateFormatException {

    GameState state = GameStateFactory.createGameStateFromString("Ah Kh Qh Jh Th\n" + "2d 7c");
    ProbabilityCalculator calculator = new ProbabilityCalculator(state);

    double probability = calculator.twoOfAKindForPlayer(0);

    assertThat(probability, equalTo(0.0));
  }

  @Test
  public void onTheRiverYieldsFullProbabilityOfTwoOfAKind() throws CardFormatException,
      BoardFormatException, PocketFormatException, GameStateFormatException {
    GameState state = GameStateFactory.createGameStateFromString("Ah Kh Qh Jh Jd\n" + "2d 7c");
    ProbabilityCalculator calculator = new ProbabilityCalculator(state);

    double probability = calculator.twoOfAKindForPlayer(0);

    assertThat(probability, equalTo(1.0));
  }

  @Test
  public void onTheTurnYieldsPartialProbabilityOfTwoOfAKind() throws CardFormatException,
      BoardFormatException, PocketFormatException, GameStateFormatException {
    GameState state = GameStateFactory.createGameStateFromString("Ah Kh Qh Jh\n" + "2d 7c");
    ProbabilityCalculator calculator = new ProbabilityCalculator(state);

    double probability = calculator.twoOfAKindForPlayer(0);

    assertThat(probability, equalTo(18.0 / 46.0));
  }

  @Test
  public void onTheFlopYieldsPartialProbabilityOfTwoOfAKind() throws CardFormatException,
      BoardFormatException, PocketFormatException, GameStateFormatException {
    GameState state = GameStateFactory.createGameStateFromString("Ah Kh Qh\n" + "2d 7c");
    ProbabilityCalculator calculator = new ProbabilityCalculator(state);

    double probability = calculator.twoOfAKindForPlayer(0);

    assertThat(probability, equalTo(633.0 / 1081.0));
  }

  @Test
  public void preFlopYieldsPartialProbabilityOfTwoOfAKind() throws CardFormatException,
      BoardFormatException, PocketFormatException, GameStateFormatException {
    GameState state = GameStateFactory.createGameStateFromString("\n" + "2d 7c");
    ProbabilityCalculator calculator = new ProbabilityCalculator(state);

    double probability = calculator.twoOfAKindForPlayer(0);

    assertTrue(probability > 0);
    assertTrue(probability < 1.0);
  }

  @Test
  public void withMoreThanOnePlayerPreFlopYieldsPartialProbabilityOfTwoOfAKind()
      throws CardFormatException, BoardFormatException, PocketFormatException,
      GameStateFormatException {
    GameState state = GameStateFactory
        .createGameStateFromString("Ah Kh Qh\n" + "2d 7c\n" + "7h 7d\n" + "Ad Kc\n" + "8c 8d");
    ProbabilityCalculator calculator = new ProbabilityCalculator(state);

    double probability = calculator.twoOfAKindForPlayer(0);

    assertThat(probability, equalTo(428.0 / 820.0));
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

    Point count = ProbabilityCalculator.countTwoOfAKindOutcomes(boardWithThreeCards, pocket,
        deckWithTenCards);

    assertThat(count.y, equalTo(45));
  }

  @Test
  public void handWithNoFullHouseReturnsFalse() throws CardFormatException, BoardFormatException,
      PocketFormatException, GameStateFormatException {
    GameState state = GameStateFactory.createGameStateFromString("Ah Kh Qh\n" + "2d 7c\n");
    Collection<Card> board = CardUtils.collectCards(state.getBoard());
    Collection<Card> pocket = CardUtils.collectCards(state.getPockets()[0]);

    boolean result = ProbabilityCalculator.hasFullHouse(board, pocket);

    assertThat(result, equalTo(false));
  }
}
