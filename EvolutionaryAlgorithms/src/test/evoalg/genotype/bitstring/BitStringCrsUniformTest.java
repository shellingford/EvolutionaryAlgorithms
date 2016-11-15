package evoalg.genotype.bitstring;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.mockito.Mockito;

import evoalg.random.IRandomness;

public class BitStringCrsUniformTest {

  private final IRandomness random = Mockito.mock(IRandomness.class);

  @Test
  public void matesCorrectly() {
    BitString ind1 = new BitString(Arrays.asList((byte) 1, (byte) 0, (byte) 0, (byte) 1, (byte) 1, (byte) 0));
    BitString ind2 = new BitString(Arrays.asList((byte) 1, (byte) 1, (byte) 0, (byte) 0, (byte) 0, (byte) 0));

    when(random.nextInt(2)).thenReturn(1, 1, 1);

    BitStringCrsUniform crs = new BitStringCrsUniform(random);
    BitString child = crs.mate(ind1, ind2);

    assertEquals(Arrays.asList((byte) 1, (byte) 1, (byte) 0, (byte) 1, (byte) 1, (byte) 0), child.getData());
  }
}
