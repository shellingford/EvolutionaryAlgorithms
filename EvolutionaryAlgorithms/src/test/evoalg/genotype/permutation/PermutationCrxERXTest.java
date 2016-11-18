package evoalg.genotype.permutation;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.mockito.Mockito;

import evoalg.random.IRandomness;

public class PermutationCrxERXTest {

  private final IRandomness random = Mockito.mock(IRandomness.class);

  @Test
  public void correctlyMates() {
    Permutation parent1 = new Permutation(Arrays.asList(0, 1, 5, 4, 3, 6, 2));
    Permutation parent2 = new Permutation(Arrays.asList(6, 5, 0, 1, 2, 3, 4));

    when(random.nextInt(7)).thenReturn(0);
    when(random.nextInt(1)).thenReturn(0);
    when(random.nextInt(2)).thenReturn(1, 1, 0);

    PermutationCrxERX pmx = new PermutationCrxERX(random);
    Permutation child = pmx.mate(parent1, parent2);

    assertEquals(Arrays.asList(0, 1, 5, 4, 6, 2, 3), child.getData());
  }

  @Test
  public void correctlyMates2() {
    Permutation parent1 = new Permutation(Arrays.asList(5, 1, 2, 4, 0, 3, 6));
    Permutation parent2 = new Permutation(Arrays.asList(4, 2, 0, 6, 1, 5, 3));

    when(random.nextInt(7)).thenReturn(4);
    when(random.nextInt(2)).thenReturn(1, 0, 0);

    PermutationCrxERX pmx = new PermutationCrxERX(random);
    Permutation child = pmx.mate(parent1, parent2);

    assertEquals(Arrays.asList(4, 2, 1, 5, 3, 0, 6), child.getData());
  }

}
