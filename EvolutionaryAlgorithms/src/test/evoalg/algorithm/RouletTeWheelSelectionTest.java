package evoalg.algorithm;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import evoalg.Deme;
import evoalg.Individual;
import evoalg.Population;
import evoalg.fitness.Fitness;
import evoalg.fitness.FitnessMax;
import evoalg.fitness.FitnessMin;
import evoalg.genotype.Crossover;
import evoalg.genotype.Mutation;
import evoalg.genotype.bitstring.BitString;
import evoalg.random.IRandomness;

@SuppressWarnings("unchecked")
public class RouletTeWheelSelectionTest {

  @Test
  public void correctlyAdvancesGenerationFitnessMax() {
    Crossover<BitString> crossover = Mockito.mock(Crossover.class);
    Mutation<BitString> mutation = Mockito.mock(Mutation.class);
    IRandomness random = Mockito.mock(IRandomness.class);

    Population<BitString> population = setupPopulationMock(Mockito.mock(FitnessMax.class),
        Mockito.mock(FitnessMax.class));

    Individual<BitString> child = setupCrossoverChildWithFitnessMax(population.getDemes().get(0), crossover, random);
    List<Individual<BitString>> expectedNextGenIndividuals = setupNextGenIndividuals(child, mutation);

    Algorithm<BitString> algorithm = new RouletTeWheelSelection<BitString>(mutation, crossover, random);

    Population<BitString> newGenPop = algorithm.advanceGeneration(population);
    assertEquals(expectedNextGenIndividuals, newGenPop.getDemes().get(0).getIndividuals());
  }

  private Population<BitString> setupPopulationMock(Fitness<BitString> fitness1,
      Fitness<BitString> fitness2) {
    Individual<BitString> ind1 = Mockito.mock(Individual.class);
    Individual<BitString> ind2 = Mockito.mock(Individual.class);

    when(ind1.getFitness()).thenReturn(fitness1);
    when(ind2.getFitness()).thenReturn(fitness2);

    when(fitness1.getValue()).thenReturn(5d);
    when(fitness2.getValue()).thenReturn(10d);

    Deme<BitString> deme = new Deme<BitString>(Arrays.asList(ind1, ind2));
    return new Population<BitString>(3, Arrays.asList(deme));
  }

  private Individual<BitString> setupCrossoverChildWithFitnessMax(Deme<BitString> deme,
      Crossover<BitString> crossover, IRandomness random) {

    Individual<BitString> child = Mockito.mock(Individual.class);

    when(random.nextDouble()).thenReturn(0.2d);
    when(random.nextDouble()).thenReturn(0.5d);
    //if random values will be 0.2 and 0.5 then expected parents should be both individuals from deme
    when(crossover.mate(deme.getIndividuals().get(0), deme.getIndividuals().get(1))).thenReturn(child);

    when(random.nextDouble()).thenReturn(0.7d);
    when(random.nextDouble()).thenReturn(0.4d);
    //if random values will be 0.7 and 0.4 then expected parents should be both second individual from deme
    when(crossover.mate(deme.getIndividuals().get(1), deme.getIndividuals().get(1))).thenReturn(child);

    return child;
  }

  private List<Individual<BitString>> setupNextGenIndividuals(Individual<BitString> child,
      Mutation<BitString> mutation) {

    Individual<BitString> mutatedChild = Mockito.mock(Individual.class);
    Individual<BitString> evaluatedChild = Mockito.mock(Individual.class);

    //mutate child and get mutatedChild
    when(mutation.mutate(child)).thenReturn(mutatedChild);
    //at the end evaluate child
    when(mutatedChild.evaluate()).thenReturn(evaluatedChild);

    //at the end of the generation deme should have 2 individuals, all evaluatedChild
    return Arrays.asList(evaluatedChild, evaluatedChild);
  }

  @Test
  public void correctlyAdvancesGenerationFitnessMin() {
    Crossover<BitString> crossover = Mockito.mock(Crossover.class);
    Mutation<BitString> mutation = Mockito.mock(Mutation.class);
    IRandomness random = Mockito.mock(IRandomness.class);

    Population<BitString> population = setupPopulationMock(Mockito.mock(FitnessMin.class),
        Mockito.mock(FitnessMin.class));

    Individual<BitString> child = setupCrossoverChildWithFitnessMin(population.getDemes().get(0), crossover, random);
    List<Individual<BitString>> expectedNextGenIndividuals = setupNextGenIndividuals(child, mutation);

    Algorithm<BitString> algorithm = new RouletTeWheelSelection<BitString>(mutation, crossover, random);

    Population<BitString> newGenPop = algorithm.advanceGeneration(population);
    assertEquals(expectedNextGenIndividuals, newGenPop.getDemes().get(0).getIndividuals());
  }

  private Individual<BitString> setupCrossoverChildWithFitnessMin(Deme<BitString> deme,
      Crossover<BitString> crossover, IRandomness random) {

    Individual<BitString> child = Mockito.mock(Individual.class);

    when(random.nextDouble()).thenReturn(0.2d);
    when(random.nextDouble()).thenReturn(0.5d);
    //if random values will be 0.2 and 0.5 then expected parents should be both first individual from deme
    when(crossover.mate(deme.getIndividuals().get(0), deme.getIndividuals().get(0))).thenReturn(child);

    when(random.nextDouble()).thenReturn(0.7d);
    when(random.nextDouble()).thenReturn(0.4d);
    //if random values will be 0.7 and 0.4 then expected parents should be both individuals from deme
    when(crossover.mate(deme.getIndividuals().get(1), deme.getIndividuals().get(0))).thenReturn(child);

    return child;
  }

}
