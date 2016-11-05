package evoalg.fitness;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import evoalg.genotype.bitstring.BitString;

public class FitnessMinTest {

  @Test
  public void correctlyComparesFitnesses() {
    double expectedMinFitnessValue = 1;
    FitnessMin<BitString> fitness1 = new FitnessMin<BitString>(null, 5d);
    FitnessMin<BitString> fitness2 = new FitnessMin<BitString>(null, expectedMinFitnessValue);
    FitnessMin<BitString> fitness3 = new FitnessMin<BitString>(null, 9d);

    List<Fitness<BitString>> list = Arrays.asList(fitness1, fitness3, fitness2);
    Collections.sort(list);
    assertEquals(expectedMinFitnessValue, list.get(0).getValue(), 0.001);
  }
}
