package evoalg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import com.google.common.collect.ImmutableList;

import evoalg.fitness.FitnessMax;
import evoalg.fitness.IEvaluate;
import evoalg.genotype.bitstring.BitString;

public class DemeTest {

  @SuppressWarnings("unchecked")
  private final IEvaluate<BitString> ievaluate = Mockito.mock(IEvaluate.class);

  @Test
  public void correctlyCreatesDemeWithDefaultSize() {
    int bitStringSize = 1;
    Deme<BitString> deme = new Deme<>(ievaluate, new BitString(bitStringSize));
    assertEquals(1, deme.size());
    assertEquals(bitStringSize, deme.getIndividuals().get(0).getGenotype().size());
  }

  @Test
  public void correctlyCreatesDemeWithSetSize() {
    int bitStringSize = 1;
    int size = 5;
    Deme<BitString> deme = new Deme<>(ievaluate, size, new BitString(bitStringSize));
    assertEquals(size, deme.size());
    assertEquals(bitStringSize, deme.getIndividuals().get(0).getGenotype().size());
  }

  @Test
  public void correctlyCreatesDemeWithSetIndividuals() {
    BitString genotype = new BitString(2);
    List<Individual<BitString>> individuals = Arrays.asList(new Individual<BitString>(ievaluate, genotype),
        new Individual<BitString>(ievaluate, genotype));

    Deme<BitString> deme = new Deme<>(individuals);
    assertEquals(individuals.size(), deme.size());
    assertTrue(deme.getIndividuals().containsAll(individuals));
  }

  @SuppressWarnings("unchecked")
  @Test
  public void correctlyEvaluatesAllIndividuals() {
    BitString genotype = new BitString(2);
    when(ievaluate.evaluate(any(Individual.class))).thenReturn(new FitnessMax<BitString>(1d));
    List<Individual<BitString>> individuals = Arrays.asList(new Individual<BitString>(ievaluate, genotype),
        new Individual<BitString>(ievaluate, genotype));

    Deme<BitString> deme = new Deme<>(individuals);
    deme = deme.evaluate();
    assertEquals(individuals.size(), deme.size());
    //every evaluated individual will have set fitness, so we can just check that if all individuals have been evaluated
    assertEquals(individuals.size(), deme.getIndividuals().stream().filter(ind -> ind.getFitness() != null).count());
  }

  @Test
  public void underalyingListIsImmutable() {
    int bitStringSize = 1;
    Deme<BitString> deme = new Deme<>(ievaluate, new BitString(bitStringSize));
    assertTrue(deme.getIndividuals() instanceof ImmutableList);
  }
}
