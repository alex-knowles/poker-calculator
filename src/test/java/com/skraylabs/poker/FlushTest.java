package com.skraylabs.poker;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.skraylabs.poker.model.Card;
import com.skraylabs.poker.model.Rank;
import com.skraylabs.poker.model.Suit;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

public class FlushTest {

  @Test
  public void givenLessThanFiveOfASuitReturnsFalse() {
    Collection<Card> cards = new ArrayList<Card>();
    cards.add(new Card(Rank.Ace, Suit.Hearts));
    cards.add(new Card(Rank.Two, Suit.Hearts));
    cards.add(new Card(Rank.Three, Suit.Hearts));
    cards.add(new Card(Rank.Four, Suit.Hearts));

    boolean result = ProbabilityCalculator.hasFlush(cards);

    assertThat(result, is(false));
  }

}
