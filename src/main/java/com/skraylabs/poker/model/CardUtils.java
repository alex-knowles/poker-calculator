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
    ArrayList<Card> result = new ArrayList<>();
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
    ArrayList<Card> result = new ArrayList<>();
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


  /**
   * Helper that returns a card based on a number in the range [0, 51].
   *
   * @param number integer in range [0, 51]
   * @return a Card
   */
  public static Card cardFromNumber(int number) {
    if (number < 0 || number > 51) {
      throw new IllegalArgumentException("Parameter \"number\" must be in range [0, 51]");
    }
    Card result = new Card(null, null);
    // Assign suit
    switch (number / 13) {
      case 0:
        result.suit = Suit.SPADES;
        break;
      case 1:
        result.suit = Suit.HEARTS;
        break;
      case 2:
        result.suit = Suit.DIAMONDS;
        break;
      case 3:
        result.suit = Suit.CLUBS;
        break;
      default:
        throw new RuntimeException("Logic error!");
    }
    // Assign rank
    switch (number % 13) {
      case 0:
        result.rank = Rank.ACE;
        break;
      case 1:
        result.rank = Rank.KING;
        break;
      case 2:
        result.rank = Rank.QUEEN;
        break;
      case 3:
        result.rank = Rank.JACK;
        break;
      case 4:
        result.rank = Rank.TEN;
        break;
      case 5:
        result.rank = Rank.NINE;
        break;
      case 6:
        result.rank = Rank.EIGHT;
        break;
      case 7:
        result.rank = Rank.SEVEN;
        break;
      case 8:
        result.rank = Rank.SIX;
        break;
      case 9:
        result.rank = Rank.FIVE;
        break;
      case 10:
        result.rank = Rank.FOUR;
        break;
      case 11:
        result.rank = Rank.THREE;
        break;
      case 12:
        result.rank = Rank.TWO;
        break;
      default:
        throw new RuntimeException("Logic error!");
    }
    return result;
  }

  /**
   * Helper method that does the inverse of {@link #cardFromNumber(int)}.
   *
   * @param card to convert to a number
   * @return number corresponding to card; integer in range [0, 51]
   */
  public static int numberFromCard(Card card) {
    if (card == null) {
      throw new IllegalArgumentException("Parameter \"card\" must be non-null!");
    } else if (card.rank == null || card.suit == null) {
      throw new IllegalArgumentException("Parameter \"card\" must have non-null rank and suit!");
    }
    int number = 0;
    switch (card.suit) {
      case SPADES:
        number = 13 * 0;
        break;
      case HEARTS:
        number = 13 * 1;
        break;
      case DIAMONDS:
        number = 13 * 2;
        break;
      case CLUBS:
        number = 13 * 3;
        break;
      default:
        throw new RuntimeException("Logic error!");
    }
    switch (card.rank) {
      case ACE:
        number += 0;
        break;
      case KING:
        number += 1;
        break;
      case QUEEN:
        number += 2;
        break;
      case JACK:
        number += 3;
        break;
      case TEN:
        number += 4;
        break;
      case NINE:
        number += 5;
        break;
      case EIGHT:
        number += 6;
        break;
      case SEVEN:
        number += 7;
        break;
      case SIX:
        number += 8;
        break;
      case FIVE:
        number += 9;
        break;
      case FOUR:
        number += 10;
        break;
      case THREE:
        number += 11;
        break;
      case TWO:
        number += 12;
        break;
      default:
        throw new RuntimeException("Logic error!");
    }
    return number;
  }

}
