package evoalg.algorithm;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import evoalg.Individual;
import evoalg.genotype.Crossover;
import evoalg.genotype.Mutation;
import evoalg.genotype.bitstring.BitString;

@SuppressWarnings("unchecked")
public class AlgorithmTest {

  private final Crossover<BitString> crossover = Mockito.mock(Crossover.class);
  private final Mutation<BitString> mutation = Mockito.mock(Mutation.class);

  private final Algorithm<BitString> algorithm = new AlgorithmMock<BitString>(mutation, crossover);

  @Test
  public void correctlyEvaluatesIndividual() {
    Individual<BitString> ind = Mockito.mock(Individual.class);
    Individual<BitString> expectedEvalInd = Mockito.mock(Individual.class);
    when(ind.evaluate()).thenReturn(expectedEvalInd);

    Individual<BitString> evaluatedInd = algorithm.evaluate(ind);

    verify(ind).evaluate();
    assertEquals(expectedEvalInd, evaluatedInd);
  }

  @Test
  public void correctlyMutatesIndividual() {
    Individual<BitString> ind = Mockito.mock(Individual.class);
    Individual<BitString> expectedMutatedInd = Mockito.mock(Individual.class);
    when(mutation.mutate(ind)).thenReturn(expectedMutatedInd);

    Individual<BitString> mutatedInd = algorithm.mutate(ind);

    verify(mutation).mutate(ind);
    assertEquals(expectedMutatedInd, mutatedInd);
  }

  @Test
  public void correctlyMutatesPool() {
    Individual<BitString> ind = Mockito.mock(Individual.class);
    List<Individual<BitString>> pool = Arrays.asList(ind);

    Individual<BitString> expectedMutatedInd = Mockito.mock(Individual.class);
    List<Individual<BitString>> expectedMutatedPool = Arrays.asList(expectedMutatedInd);
    when(mutation.mutate(pool)).thenReturn(expectedMutatedPool);

    List<Individual<BitString>> mutatedPool = algorithm.mutate(pool);

    verify(mutation).mutate(pool);
    assertEquals(expectedMutatedPool, mutatedPool);
  }

  @Test
  public void correctlyMates() {
    Individual<BitString> parent1 = Mockito.mock(Individual.class);
    Individual<BitString> parent2 = Mockito.mock(Individual.class);

    Individual<BitString> expectedChild = Mockito.mock(Individual.class);
    when(crossover.mate(parent1, parent2)).thenReturn(expectedChild);

    Individual<BitString> child = algorithm.mate(parent1, parent2);

    verify(crossover).mate(parent1, parent2);
    assertEquals(expectedChild, child);
  }
}
