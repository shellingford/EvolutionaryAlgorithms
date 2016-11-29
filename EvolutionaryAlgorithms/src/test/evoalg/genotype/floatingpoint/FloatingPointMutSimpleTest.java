package evoalg.genotype.floatingpoint;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.mockito.Mockito;

import evoalg.random.IRandomness;

public class FloatingPointMutSimpleTest {

  private final IRandomness random = Mockito.mock(IRandomness.class);

  @Test
  public void mutatesCorrectly() {
    FloatingPoint genotype = new FloatingPoint(Arrays.asList(5.23d, 8.3d, 9.11d), 5d, 10d);

    when(random.nextInt(3)).thenReturn(1);
    when(random.nextDouble()).thenReturn(0.5d);

    FloatingPointMutSimple mutOp = new FloatingPointMutSimple(random);
    FloatingPoint mutatedGenotype = mutOp.mutate(genotype);

    assertEquals(Arrays.asList(5.23d, 7.5d, 9.11d), mutatedGenotype.getData());
  }
}
