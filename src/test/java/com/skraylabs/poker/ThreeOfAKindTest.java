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

import org.junit.Test;

import java.util.Collection;

public class ThreeOfAKindTest {

  @Test
  public void handWithNoThreeOfAKindReturnsFalse() throws CardFormatException, BoardFormatException,
      PocketFormatException, GameStateFormatException {
    GameState state = GameStateFactory.createGameStateFromString("Ah Kh Qh\n" + "2d 7c");
    Collection<Card> board = CardUtils.collectCards(state.getBoard());
    Collection<Card> pocket = CardUtils.collectCards(state.getPockets()[0]);
    Collection<Card> cards = ProbabilityCalculator.collectHandCards(board, pocket);

    boolean result = ProbabilityCalculator.hasThreeOfAKind(cards);

    assertThat(result, is(false));
  }

  @Test
  public void handWithThreeOfAKindReturnsTrue() throws CardFormatException, BoardFormatException,
      PocketFormatException, GameStateFormatException {
    GameState state = GameStateFactory.createGameStateFromString("Ah Kh Qh\n" + "Qd Qc");
    Collection<Card> board = CardUtils.collectCards(state.getBoard());
    Collection<Card> pocket = CardUtils.collectCards(state.getPockets()[0]);
    Collection<Card> cards = ProbabilityCalculator.collectHandCards(board, pocket);

    boolean result = ProbabilityCalculator.hasThreeOfAKind(cards);

    assertThat(result, is(true));
  }

}
