package evoalg.genotype.permutation;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.mockito.Mockito;

import evoalg.random.IRandomness;

public class PermutationMutRotTest {

  private final IRandomness random = Mockito.mock(IRandomness.class);

  @Test
  public void correctlyMutatesWithUnevenSublistSize() {
    Permutation individual = new Permutation(Arrays.asList(1, 2, 3, 4, 5, 6));

    when(random.nextInt(5)).thenReturn(1);
    when(random.nextInt(4)).thenReturn(3);
    PermutationMutRot mutation = new PermutationMutRot(random);

    Permutation mutated = mutation.mutate(individual);
    assertEquals(Arrays.asList(1, 6, 2, 3, 4, 5), mutated.getData());
  }

  @Test
  public void correctlyMutatesWithEvenSublistSize() {
    Permutation individual = new Permutation(Arrays.asList(1, 2, 3, 4, 5));

    when(random.nextInt(4)).thenReturn(1);
    when(random.nextInt(3)).thenReturn(2);

    PermutationMutRot mutation = new PermutationMutRot(random);

    Permutation mutated = mutation.mutate(individual);
    assertEquals(Arrays.asList(1, 5, 2, 3, 4), mutated.getData());
  }

  @Test
  public void correctlyMutatesWhenSublistIsFullList() {
    Permutation individual = new Permutation(Arrays.asList(1, 2, 3, 4, 5));

    when(random.nextInt(4)).thenReturn(0, 3);

    PermutationMutRot mutation = new PermutationMutRot(random);

    Permutation mutated = mutation.mutate(individual);
    assertEquals(Arrays.asList(5, 1, 2, 3, 4), mutated.getData());
  }

  @Test
  public void correctlyMutatesWhenSublistIsOnly2() {
    Permutation individual = new Permutation(Arrays.asList(1, 2, 3, 4, 5));

    when(random.nextInt(4)).thenReturn(1);
    when(random.nextInt(3)).thenReturn(0);

    PermutationMutRot mutation = new PermutationMutRot(random);

    Permutation mutated = mutation.mutate(individual);
    assertEquals(Arrays.asList(1, 3, 2, 4, 5), mutated.getData());
  }

}
