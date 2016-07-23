package com.skraylabs.poker;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.skraylabs.poker.model.BoardFormatException;
import com.skraylabs.poker.model.Card;
import com.skraylabs.poker.model.CardFormatException;
import com.skraylabs.poker.model.GameStateFormatException;
import com.skraylabs.poker.model.PocketFormatException;
import com.skraylabs.poker.model.Rank;
import com.skraylabs.poker.model.Suit;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

public class ThreeOfAKindTest {

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
   * Set up test board.
   */
  @Before
  public void setUpBoardAceKingQueenJack() {
    boardAceKingQueenJack = new ArrayList<Card>();
    boardAceKingQueenJack.add(new Card(Rank.Ace, Suit.Hearts));
    boardAceKingQueenJack.add(new Card(Rank.King, Suit.Hearts));
    boardAceKingQueenJack.add(new Card(Rank.Queen, Suit.Hearts));
    boardAceKingQueenJack.add(new Card(Rank.Jack, Suit.Hearts));
  }

  /**
   * Set up test pocket.
   */
  @Before
  public void setUpPocketTwoSeven() {
    pocketTwoSeven = new ArrayList<Card>();
    pocketTwoSeven.add(new Card(Rank.Two, Suit.Diamonds));
    pocketTwoSeven.add(new Card(Rank.Seven, Suit.Clubs));
  }

  /**
   * Set up test pocket.
   */
  @Before
  public void setUpPocketQueens() {
    pocketQueens = new ArrayList<Card>();
    pocketQueens.add(new Card(Rank.Queen, Suit.Diamonds));
    pocketQueens.add(new Card(Rank.Queen, Suit.Clubs));
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
