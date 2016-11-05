package evoalg.fitness;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import evoalg.genotype.bitstring.BitString;

public class FitnessMaxTest {

  @Test
  public void correctlyComparesFitnesses() {
    double expectedMaxFitnessValue = 10;
    FitnessMax<BitString> fitness1 = new FitnessMax<BitString>(null, 5d);
    FitnessMax<BitString> fitness2 = new FitnessMax<BitString>(null, expectedMaxFitnessValue);
    FitnessMax<BitString> fitness3 = new FitnessMax<BitString>(null, 2d);

    List<Fitness<BitString>> list = Arrays.asList(fitness1, fitness2, fitness3);
    Collections.sort(list);
    assertEquals(expectedMaxFitnessValue, list.get(0).getValue(), 0.001);
  }

}
