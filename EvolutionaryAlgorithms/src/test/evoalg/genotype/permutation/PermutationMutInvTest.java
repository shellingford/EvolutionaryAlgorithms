package evoalg.genotype.permutation;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.mockito.Mockito;

import evoalg.random.IRandomness;

public class PermutationMutInvTest {

  private final IRandomness random = Mockito.mock(IRandomness.class);

  @Test
  public void correctlyMutatesWithUnevenSublistSize() {
    Permutation individual = new Permutation(Arrays.asList(1, 2, 3, 4, 5, 6));

    when(random.nextInt(5)).thenReturn(1);
    when(random.nextInt(4)).thenReturn(3);

    PermutationMutInv mutation = new PermutationMutInv(random);

    Permutation mutated = mutation.mutate(individual);
    assertEquals(Arrays.asList(1, 6, 5, 4, 3, 2), mutated.getData());
  }

  @Test
  public void correctlyMutatesWithEvenSublistSize() {
    Permutation individual = new Permutation(Arrays.asList(1, 2, 3, 4, 5));

    when(random.nextInt(4)).thenReturn(1);
    when(random.nextInt(3)).thenReturn(2);

    PermutationMutInv mutation = new PermutationMutInv(random);

    Permutation mutated = mutation.mutate(individual);
    assertEquals(Arrays.asList(1, 5, 4, 3, 2), mutated.getData());
  }

  @Test
  public void correctlyMutatesWhenSublistIsFullList() {
    Permutation individual = new Permutation(Arrays.asList(1, 2, 3, 4, 5));

    when(random.nextInt(4)).thenReturn(0, 3);

    PermutationMutInv mutation = new PermutationMutInv(random);

    Permutation mutated = mutation.mutate(individual);
    assertEquals(Arrays.asList(5, 4, 3, 2, 1), mutated.getData());
  }

  @Test
  public void correctlyMutatesWhenSublistIsOnly2() {
    Permutation individual = new Permutation(Arrays.asList(1, 2, 3, 4, 5));

    when(random.nextInt(4)).thenReturn(1);
    when(random.nextInt(3)).thenReturn(0);

    PermutationMutInv mutation = new PermutationMutInv(random);

    Permutation mutated = mutation.mutate(individual);
    assertEquals(Arrays.asList(1, 3, 2, 4, 5), mutated.getData());
  }

}
