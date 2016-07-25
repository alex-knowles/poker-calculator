package com.skraylabs.poker;

import com.skraylabs.poker.model.Card;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Collection;

public class NOfAKindTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void negativeNumberCausesException() {
    exception.expect(IllegalArgumentException.class);
    Collection<Card> cards = new ArrayList<Card>();

    ProbabilityCalculator.hasNOfAKind(cards, -1);
  }

  @Test
  public void zeroNumberCausesException() {
    exception.expect(IllegalArgumentException.class);
    Collection<Card> cards = new ArrayList<Card>();

    ProbabilityCalculator.hasNOfAKind(cards, 0);
  }
}
