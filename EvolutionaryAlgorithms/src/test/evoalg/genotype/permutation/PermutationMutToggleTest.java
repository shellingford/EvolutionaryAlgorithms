package evoalg.genotype.permutation;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.mockito.Mockito;

import evoalg.random.IRandomness;

public class PermutationMutToggleTest {

  private final IRandomness random = Mockito.mock(IRandomness.class);

  @Test
  public void correctlyMutates() {
    Permutation individual = new Permutation(Arrays.asList(1, 2, 3, 5, 4, 6, 7, 8, 9));
    when(random.nextInt(8)).thenReturn(2);
    when(random.nextInt(6)).thenReturn(1);
    PermutationMutToggle mutation = new PermutationMutToggle(random);

    Permutation mutated = mutation.mutate(individual);
    assertEquals(Arrays.asList(1, 2, 4, 5, 3, 6, 7, 8, 9), mutated.getData());
  }

}
