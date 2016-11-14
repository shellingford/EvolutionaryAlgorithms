package evoalg.genotype.permutation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.mockito.Mockito;

import evoalg.random.IRandomness;

public class PermutationCrxOXTest {

  private final IRandomness random = Mockito.mock(IRandomness.class);

  @Test
  public void correctlyMates() {
    Permutation parent1 = new Permutation(Arrays.asList(1, 2, 3, 5, 4, 6, 7, 8, 9));
    Permutation parent2 = new Permutation(Arrays.asList(4, 5, 2, 1, 8, 7, 6, 9, 3));

    when(random.nextInt(8)).thenReturn(3);
    when(random.nextInt(5)).thenReturn(2);

    PermutationCrxOX pmx = new PermutationCrxOX(random);
    Permutation child = pmx.mate(parent1, parent2);
    assertTrue(child.isValid());

    assertEquals(Arrays.asList(2, 1, 8, 5, 4, 6, 7, 9, 3), child.getData());
  }
}
