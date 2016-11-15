package evoalg.genotype.bitstring;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.mockito.Mockito;

import evoalg.random.IRandomness;

public class BitStringCrsOnePointTest {

  private final IRandomness random = Mockito.mock(IRandomness.class);

  @Test
  public void matesCorrectlyP1P2() {
    BitString ind1 = new BitString(Arrays.asList((byte) 1, (byte) 0, (byte) 0, (byte) 1, (byte) 1, (byte) 0));
    BitString ind2 = new BitString(Arrays.asList((byte) 1, (byte) 1, (byte) 0, (byte) 0, (byte) 0, (byte) 0));

    when(random.nextInt(7)).thenReturn(3);
    when(random.nextBoolean()).thenReturn(true);

    BitStringCrsOnePoint crs = new BitStringCrsOnePoint(random);
    BitString child = crs.mate(ind1, ind2);

    assertEquals(Arrays.asList((byte) 1, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0), child.getData());
  }

  @Test
  public void matesCorrectlyP2P1() {
    BitString ind1 = new BitString(Arrays.asList((byte) 1, (byte) 0, (byte) 0, (byte) 1, (byte) 1, (byte) 0));
    BitString ind2 = new BitString(Arrays.asList((byte) 1, (byte) 1, (byte) 0, (byte) 0, (byte) 0, (byte) 0));

    when(random.nextInt(7)).thenReturn(3);
    when(random.nextBoolean()).thenReturn(false);

    BitStringCrsOnePoint crs = new BitStringCrsOnePoint(random);
    BitString child = crs.mate(ind1, ind2);

    assertEquals(Arrays.asList((byte) 1, (byte) 1, (byte) 0, (byte) 1, (byte) 1, (byte) 0), child.getData());
  }

  @Test
  public void matesCorrectlyAllFromP1() {
    BitString ind1 = new BitString(Arrays.asList((byte) 1, (byte) 0, (byte) 0, (byte) 1, (byte) 1, (byte) 1));
    BitString ind2 = new BitString(Arrays.asList((byte) 1, (byte) 1, (byte) 0, (byte) 0, (byte) 0, (byte) 0));

    when(random.nextInt(7)).thenReturn(6);
    when(random.nextBoolean()).thenReturn(true);

    BitStringCrsOnePoint crs = new BitStringCrsOnePoint(random);
    BitString child = crs.mate(ind1, ind2);

    assertEquals(Arrays.asList((byte) 1, (byte) 0, (byte) 0, (byte) 1, (byte) 1, (byte) 1), child.getData());
  }

  @Test
  public void matesCorrectlyAllFromP2() {
    BitString ind1 = new BitString(Arrays.asList((byte) 1, (byte) 0, (byte) 0, (byte) 1, (byte) 1, (byte) 0));
    BitString ind2 = new BitString(Arrays.asList((byte) 1, (byte) 1, (byte) 0, (byte) 0, (byte) 0, (byte) 0));

    when(random.nextInt(6)).thenReturn(0);
    when(random.nextBoolean()).thenReturn(true);

    BitStringCrsOnePoint crs = new BitStringCrsOnePoint(random);
    BitString child = crs.mate(ind1, ind2);

    assertEquals(Arrays.asList((byte) 1, (byte) 1, (byte) 0, (byte) 0, (byte) 0, (byte) 0), child.getData());
  }

}
