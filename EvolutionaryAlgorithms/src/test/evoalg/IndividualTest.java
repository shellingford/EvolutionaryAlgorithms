package evoalg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mockito;

import evoalg.fitness.FitnessMax;
import evoalg.fitness.IEvaluate;
import evoalg.genotype.bitstring.BitString;

public class IndividualTest {

  @SuppressWarnings("unchecked")
  private final IEvaluate<BitString> ievaluate = Mockito.mock(IEvaluate.class);

  @Test
  public void correctlyEvaluatesIndividual() {
    BitString genotype = new BitString(1);
    Individual<BitString> individual = new Individual<BitString>(ievaluate, genotype);
    FitnessMax<BitString> fitness = new FitnessMax<>(1d);
    when(ievaluate.evaluate(individual)).thenReturn(fitness);

    individual = individual.evaluate();
    assertTrue(individual.getFitness() != null);
    assertEquals(fitness, individual.getFitness());
  }

  @Test
  public void correctlyCopiesIndividual() {
    BitString genotype = new BitString(1);
    FitnessMax<BitString> fitness = new FitnessMax<>(1d);
    Individual<BitString> individual = new Individual<BitString>(fitness, ievaluate, genotype);
    Individual<BitString> copy = individual.copy();
    assertEquals(individual, copy);
  }
}
