package evoalg.selection;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import evoalg.Individual;
import evoalg.fitness.Fitness;
import evoalg.fitness.FitnessMax;
import evoalg.genotype.bitstring.BitString;

public class SelBestOpTest {

  @Test
  public void correctlySelectsBestIndividual() {
    List<Individual<BitString>> individuals = createTestIndividuals();

    SelBestOp<BitString> selector = new SelBestOp<BitString>();
    Individual<BitString> selected = selector.select(individuals);

    //using FitnessMax so the best individual will have highest fitness value
    assertEquals(3d, selected.getFitness().getValue(), 0.001);
  }

  @SuppressWarnings("unchecked")
  private List<Individual<BitString>> createTestIndividuals() {
    Individual<BitString> individual1 = Mockito.mock(Individual.class);
    Individual<BitString> individual2 = Mockito.mock(Individual.class);
    Individual<BitString> individual3 = Mockito.mock(Individual.class);

    Fitness<BitString> fitness1 = new FitnessMax<BitString>(individual1, 1d);
    Fitness<BitString> fitness2 = new FitnessMax<BitString>(individual2, 0d);
    Fitness<BitString> fitness3 = new FitnessMax<BitString>(individual3, 3d);

    when(individual1.getFitness()).thenReturn(fitness1);
    when(individual2.getFitness()).thenReturn(fitness2);
    when(individual3.getFitness()).thenReturn(fitness3);

    return Arrays.asList(individual1, individual2, individual3);
  }
}
