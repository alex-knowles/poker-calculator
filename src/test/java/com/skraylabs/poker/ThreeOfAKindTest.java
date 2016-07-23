package com.skraylabs.poker;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.skraylabs.poker.model.BoardFormatException;
import com.skraylabs.poker.model.Card;
import com.skraylabs.poker.model.CardFormatException;
import com.skraylabs.poker.model.CardUtils;
import com.skraylabs.poker.model.GameState;
import com.skraylabs.poker.model.GameStateFactory;
import com.skraylabs.poker.model.GameStateFormatException;
import com.skraylabs.poker.model.PocketFormatException;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

public class ThreeOfAKindTest {

  /**
   * Test game.
   * <p>
   * Ah Kh Jh<br>
   * 2d 7c<br>
   * Qd Qc<br>
   * As Js
   */
  GameState game;

  /**
   * Test board.
   *
   * <p>
   * Ah Kh Qh Jh
   */
  Collection<Card> boardAceKingQueenJack;

  /**
   * Test pocket. No Three of a Kind with {@link #boardAceKingQueenJack}.
   *
   * <p>
   * 2d 7c
   */
  Collection<Card> pocketTwoSeven;

  /**
   * Test pocket. Makes triple queens with {@link #boardAceKingQueenJack}.
   *
   * <p>
   * Qd Qc
   */
  Collection<Card> pocketQueens;

  /**
   * Test pocket.
   *
   * <p>
   * As Js
   */
  Collection<Card> pocketAceJack;

  /**
   * Set up test fixture.
   */
  @Before
  public void setUp() throws CardFormatException, BoardFormatException,
      PocketFormatException, GameStateFormatException {
    game = GameStateFactory
        .createGameStateFromString("Ah Kh Qh Jh\n" + "2d 7c\n" + "Qd Qc\n" + "As Js");
    boardAceKingQueenJack = CardUtils.collectCards(game.getBoard());
    pocketTwoSeven = CardUtils.collectCards(game.getPockets()[0]);
    pocketQueens = CardUtils.collectCards(game.getPockets()[1]);
    pocketAceJack = CardUtils.collectCards(game.getPockets()[2]);
  }

  @Test
  public void handWithNoThreeOfAKindReturnsFalse() {
    Collection<Card> cards =
        ProbabilityCalculator.collectHandCards(boardAceKingQueenJack, pocketTwoSeven);

    boolean result = ProbabilityCalculator.hasThreeOfAKind(cards);

    assertThat(result, is(false));
  }

  @Test
  public void handWithThreeOfAKindReturnsTrue() throws CardFormatException, BoardFormatException,
      PocketFormatException, GameStateFormatException {
    Collection<Card> cards =
        ProbabilityCalculator.collectHandCards(boardAceKingQueenJack, pocketQueens);

    boolean result = ProbabilityCalculator.hasThreeOfAKind(cards);

    assertThat(result, is(true));
  }

}
