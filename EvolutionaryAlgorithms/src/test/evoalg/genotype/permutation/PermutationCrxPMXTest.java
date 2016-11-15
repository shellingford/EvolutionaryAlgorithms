package evoalg.genotype.permutation;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.mockito.Mockito;

import evoalg.random.IRandomness;

public class PermutationCrxPMXTest {

  private final IRandomness random = Mockito.mock(IRandomness.class);

  @Test
  public void correctlyMates() {
    Permutation parent1 = new Permutation(Arrays.asList(1, 2, 3, 5, 4, 6, 7, 8, 9));
    Permutation parent2 = new Permutation(Arrays.asList(4, 5, 2, 1, 8, 7, 6, 9, 3));

    when(random.nextInt(8)).thenReturn(3);
    when(random.nextInt(5)).thenReturn(2);

    PermutationCrxPMX pmx = new PermutationCrxPMX(random);
    Permutation child = pmx.mate(parent1, parent2);

    assertEquals(Arrays.asList(8, 1, 2, 5, 4, 6, 7, 9, 3), child.getData());
  }

  @Test
  public void correctlyMutatesWhenSublistIsFullList() {
    Permutation parent1 = new Permutation(Arrays.asList(1, 2, 3, 5, 4, 6, 7, 8, 9));
    Permutation parent2 = new Permutation(Arrays.asList(4, 5, 2, 1, 8, 7, 6, 9, 3));

    when(random.nextInt(8)).thenReturn(0, 7);

    PermutationCrxPMX pmx = new PermutationCrxPMX(random);
    Permutation child = pmx.mate(parent1, parent2);

    assertEquals(Arrays.asList(1, 2, 3, 5, 4, 6, 7, 8, 9), child.getData());
  }
}
