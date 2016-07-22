package com.skraylabs.poker;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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

public class TwoOfAKindTest {

  /**
   * Test board.
   *
   * <p>
   * Ah Kh Qh
   */
  Collection<Card> boardAceKingQueen;

  /**
   * Test pocket. No Two of a Kind with {@link #boardAceKingQueen}.
   *
   * <p>
   * 2d 7c
   */
  Collection<Card> pocketTwoSeven;

  /**
   * Test pocket. Makes pair of jacks with {@link #boardAceKingQueen}.
   *
   * <p>
   * Jd Jc
   */
  Collection<Card> pocketJacks;

  /**
   * Set up test board.
   */
  @Before
  public void setUpBoardAceKingQueen() {
    boardAceKingQueen = new ArrayList<Card>();
    boardAceKingQueen.add(new Card(Rank.Ace, Suit.Hearts));
    boardAceKingQueen.add(new Card(Rank.King, Suit.Hearts));
    boardAceKingQueen.add(new Card(Rank.Queen, Suit.Hearts));
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
  public void setUpPocketJacks() {
    pocketJacks = new ArrayList<Card>();
    pocketJacks.add(new Card(Rank.Jack, Suit.Diamonds));
    pocketJacks.add(new Card(Rank.Jack, Suit.Clubs));
  }

  @Test
  public void handWithNoTwoOfAKindReturnsFalse() {
    Collection<Card> cards =
        ProbabilityCalculator.collectHandCards(boardAceKingQueen, pocketTwoSeven);

    boolean result = ProbabilityCalculator.hasTwoOfAKind(cards);

    assertThat(result, is(false));
  }

  @Test
  public void handWithTwoOfAKindReturnsTrue() throws CardFormatException, BoardFormatException,
      PocketFormatException, GameStateFormatException {
    Collection<Card> cards = ProbabilityCalculator.collectHandCards(boardAceKingQueen, pocketJacks);

    boolean result = ProbabilityCalculator.hasTwoOfAKind(cards);

    assertThat(result, is(true));
  }

}
