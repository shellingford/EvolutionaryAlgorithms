package evoalg.genotype.permutation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import evoalg.random.IRandomness;

public class PermutationMutInvTest {

  private IRandomness random;

  @Before
  public void setup() {
    random = Mockito.mock(IRandomness.class);
  }

  @Test
  public void correctlyMutatesWithUnevenSublistSize() {
    Permutation individual = new Permutation(Arrays.asList(1, 2, 3, 4, 5, 6));

    when(random.nextInt(5)).thenReturn(1);
    when(random.nextInt(4)).thenReturn(3);

    PermutationMutInv mutation = new PermutationMutInv(random);

    Permutation mutated = mutation.mutate(individual);
    assertEquals(Arrays.asList(1, 6, 5, 4, 3, 2), mutated.getData());
    assertTrue(mutated.isValid());
  }

  @Test
  public void correctlyMutatesWithEvenSublistSize() {
    Permutation individual = new Permutation(Arrays.asList(1, 2, 3, 4, 5));

    when(random.nextInt(4)).thenReturn(1);
    when(random.nextInt(3)).thenReturn(2);

    PermutationMutInv mutation = new PermutationMutInv(random);

    Permutation mutated = mutation.mutate(individual);
    assertEquals(Arrays.asList(1, 5, 4, 3, 2), mutated.getData());
    assertTrue(mutated.isValid());
  }

}
