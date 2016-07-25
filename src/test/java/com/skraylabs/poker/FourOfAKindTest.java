package com.skraylabs.poker;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.skraylabs.poker.model.Card;
import com.skraylabs.poker.model.Rank;
import com.skraylabs.poker.model.Suit;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

public class FourOfAKindTest {

  /**
   * Empty board.
   *
   * <p>
   *
   */
  Collection<Card> boardEmpty;

  /**
   * Test board.
   *
   * <p>
   * Ah Jh Jc
   */
  Collection<Card> boardAceJackJack;

  /**
   * Test pocket. Makes four of a kind of jacks with {@link #boardAceJackJack}.
   *
   * <p>
   * Jd Jc
   */
  Collection<Card> pocketJacks;

  /**
   * Set up test board.
   */
  @Before
  public void setUpBoardEmpty() {
    boardEmpty = new ArrayList<Card>();
  }

  /**
   * Set up test board.
   */
  @Before
  public void setUpBoardAceJackJack() {
    boardAceJackJack = new ArrayList<Card>();
    boardAceJackJack.add(new Card(Rank.Ace, Suit.Hearts));
    boardAceJackJack.add(new Card(Rank.Jack, Suit.Hearts));
    boardAceJackJack.add(new Card(Rank.Jack, Suit.Clubs));
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
  public void handWithNoFourOfAKindReturnsFalse() {
    Collection<Card> cards = ProbabilityCalculator.collectHandCards(boardEmpty, pocketJacks);

    boolean result = ProbabilityCalculator.hasFourOfAKind(cards);

    assertThat(result, is(false));
  }

  @Test
  public void handWithFourOfAKindReturnsTrue() {
    Collection<Card> cards = ProbabilityCalculator.collectHandCards(boardAceJackJack, pocketJacks);

    boolean result = ProbabilityCalculator.hasFourOfAKind(cards);

    assertThat(result, is(true));
  }

}
