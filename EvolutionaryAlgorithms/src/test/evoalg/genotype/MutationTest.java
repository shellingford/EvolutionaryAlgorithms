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
import evoalg.genotype.bitstring.BitStringMutMix;
import evoalg.genotype.bitstring.BitStringMutSimple;
import evoalg.random.IRandomness;

public class MutationTest {

  @SuppressWarnings("unchecked")
  private final IEvaluate<BitString> ievaluate = Mockito.mock(IEvaluate.class);
  private final BitString genotype = new BitString(7);

  private final IRandomness random = Mockito.mock(IRandomness.class);

  @Test
  public void mutationOperatorListIsImmutable() {
    List<MutationOp<BitString>> operators = Arrays.asList(new BitStringMutSimple(random));
    Mutation<BitString> mutation = new Mutation<BitString>(operators, 0d, random);
    assertTrue(mutation.getMutationOp() instanceof ImmutableList);
  }

  @Test
  public void correctlyMutatesIndividuals() {
    when(random.nextDouble()).thenReturn(0.3d);
    when(random.nextInt(2)).thenReturn(1);

    Individual<BitString> ind = new Individual<BitString>(ievaluate, genotype.initializeData());
    Individual<BitString> expectedMutatedInd = new Individual<BitString>(ievaluate, genotype);

    List<MutationOp<BitString>> operators = setupOperators(ind, expectedMutatedInd);
    Mutation<BitString> mutation = new Mutation<BitString>(operators, 0.5d, random);

    Individual<BitString> mutated = mutation.mutate(ind);

    assertEquals(expectedMutatedInd, mutated);
  }

  private List<MutationOp<BitString>> setupOperators(Individual<BitString> ind, Individual<BitString> expectedMutatedInd) {
    BitStringMutSimple mutSimple = Mockito.mock(BitStringMutSimple.class);
    Individual<BitString> expectedMutatedInd2 = new Individual<BitString>(ievaluate, genotype.initializeData());
    when(mutSimple.mutate(ind.getGenotype())).thenReturn(expectedMutatedInd2.getGenotype());

    BitStringMutMix mutMix = Mockito.mock(BitStringMutMix.class);
    when(mutMix.mutate(ind.getGenotype())).thenReturn(expectedMutatedInd.getGenotype());

    return Arrays.asList(mutSimple, mutMix);
  }

  @Test
  public void doesntMutate() {
    when(random.nextDouble()).thenReturn(0.5d);

    Individual<BitString> ind = new Individual<BitString>(ievaluate, genotype);
    Individual<BitString> expectedMutatedInd = new Individual<BitString>(ievaluate, genotype);

    List<MutationOp<BitString>> operators = setupOperators(ind, expectedMutatedInd);
    Mutation<BitString> mutation = new Mutation<BitString>(operators, 0.4d, random);

    Individual<BitString> mutated = mutation.mutate(ind);

    assertEquals(ind, mutated);
  }

}
