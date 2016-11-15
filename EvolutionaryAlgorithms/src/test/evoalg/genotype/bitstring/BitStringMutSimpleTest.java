package evoalg.genotype.bitstring;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.mockito.Mockito;

import evoalg.random.IRandomness;

public class BitStringMutSimpleTest {

  private final IRandomness random = Mockito.mock(IRandomness.class);

  @Test
  public void mutatesCorrectly() {
    BitString genotype = new BitString(Arrays.asList((byte) 1, (byte) 0, (byte) 0, (byte) 1, (byte) 1, (byte) 0));

    when(random.nextInt(6)).thenReturn(1);

    BitStringMutSimple mutOp = new BitStringMutSimple(random);
    BitString mutatedGenotype = mutOp.mutate(genotype);

    assertEquals(Arrays.asList((byte) 1, (byte) 1, (byte) 0, (byte) 1, (byte) 1, (byte) 0), mutatedGenotype.getData());
  }
}
