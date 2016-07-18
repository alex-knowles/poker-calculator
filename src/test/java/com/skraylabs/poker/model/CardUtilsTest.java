package com.skraylabs.poker.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CardUtilsTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void testCardFromNumber_tooLow() {
    // Verify
    exception.expect(IllegalArgumentException.class);
    // Exercise
    CardUtils.cardFromNumber(-1);
  }

  @Test
  public void testCardFromNumber_tooHigh() {
    // Verify
    exception.expect(IllegalArgumentException.class);
    // Exercise
    CardUtils.cardFromNumber(52);
  }

  @Test
  public void testCardFromNumber_noDuplicates() {
    // Exercise
    Card[] cards = new Card[52];
    for (int i = 0; i < 52; ++i) {
      cards[i] = CardUtils.cardFromNumber(i);
    }
    // Verify
    for (int i = 0; i < 52; ++i) {
      Card card = cards[i];
      assertThat(card.rank, notNullValue());
      assertThat(card.suit, notNullValue());
      int numCardsLikeMe = 0;
      for (int j = 0; j < 52; ++j) {
        if (card.equals(cards[j])) {
          numCardsLikeMe++;
        }
      }
      assertThat(numCardsLikeMe, is(1));
    }
  }
}
