package com.skraylabs.poker.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CardUtils {

  /**
   * Helper method to collect cards from a given {@link GameState}.
   *
   * @param state from which to collect
   * @return collection of cards from {@code state}
   */
  public static Collection<Card> collectCards(GameState state) {
    Collection<Card> result = CardUtils.collectCards(state.getBoard());
    List<Pocket> pockets = Arrays.asList(state.getPockets());
    for (Pocket pocket : pockets) {
      result.addAll(CardUtils.collectCards(pocket));
    }
    return result;
  }


  /**
   * Helper method to collect all cards from a given {@link Board}.
   *
   * @param board from which to collect
   * @return collection of cards found in {@code board}
   */
  public static Collection<Card> collectCards(Board board) {
    ArrayList<Card> result = new ArrayList<Card>();
    if (board == null) {
      return result;
    }
    for (int i = 0; i < 5; ++i) {
      Card card;
      switch (i) {
        case 0:
          card = board.flopCard1;
          break;
        case 1:
          card = board.flopCard2;
          break;
        case 2:
          card = board.flopCard3;
          break;
        case 3:
          card = board.turnCard;
          break;
        case 4:
          card = board.riverCard;
          break;
        default:
          throw new RuntimeException("Logic error.");
      }
      if (card != null) {
        result.add(card);
      }
    }
    return result;
  }

  /**
   * Helper method to collect cards from a given {@link Pocket}.
   *
   * @param pocket from which to collect
   * @return collection of cards from {@code pocket}
   */
  public static Collection<Card> collectCards(Pocket pocket) {
    ArrayList<Card> result = new ArrayList<Card>();
    if (pocket == null) {
      return result;
    }
    if (pocket.card1 != null) {
      result.add(pocket.card1);
    }
    if (pocket.card2 != null) {
      result.add(pocket.card2);
    }
    return result;
  }

}
