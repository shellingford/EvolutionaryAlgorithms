package evoalg.genotype;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import com.google.common.collect.ImmutableList;

import evoalg.Individual;
import evoalg.fitness.IEvaluate;
import evoalg.genotype.bitstring.BitString;
import evoalg.genotype.bitstring.BitStringCrsOnePoint;
import evoalg.genotype.bitstring.BitStringCrsUniform;
import evoalg.random.DefaultRandom;
import evoalg.random.IRandomness;

public class CrossoverTest {

  @SuppressWarnings("unchecked")
  private final IEvaluate<BitString> ievaluate = Mockito.mock(IEvaluate.class);

  private final IRandomness random = new DefaultRandom();

  @Test
  public void crossoverOperatorListIsImmutable() {
    List<CrossoverOp<BitString>> operators = Arrays.asList(new BitStringCrsOnePoint(random));
    Crossover<BitString> crossover = new Crossover<BitString>(operators, random);
    assertTrue(crossover.getCrossoverOp() instanceof ImmutableList);
  }

  @Test
  public void correctlyMatesIndividuals() {
    BitString genotype = new BitString(7);
    Individual<BitString> ind1 = new Individual<BitString>(ievaluate, genotype.initializeData());
    Individual<BitString> ind2 = new Individual<BitString>(ievaluate, genotype.initializeData());
    Individual<BitString> expectedChild = new Individual<BitString>(ievaluate, genotype);

    List<CrossoverOp<BitString>> operators = setupOperators(ind1, ind2, expectedChild);
    Crossover<BitString> crossover = new Crossover<BitString>(operators, random);

    Individual<BitString> child = crossover.mate(ind1, ind2);

    assertEquals(expectedChild, child);
  }

  private List<CrossoverOp<BitString>> setupOperators(Individual<BitString> ind1, Individual<BitString> ind2,
      Individual<BitString> expectedChild) {
    BitStringCrsOnePoint crsOnePoint = Mockito.mock(BitStringCrsOnePoint.class);
    when(crsOnePoint.mate(ind1.getGenotype(), ind2.getGenotype())).thenReturn(expectedChild.getGenotype());

    BitStringCrsUniform crsUniform = Mockito.mock(BitStringCrsUniform.class);
    when(crsUniform.mate(ind1.getGenotype(), ind2.getGenotype())).thenReturn(expectedChild.getGenotype());

    return Arrays.asList(crsOnePoint, crsUniform);
  }
}
