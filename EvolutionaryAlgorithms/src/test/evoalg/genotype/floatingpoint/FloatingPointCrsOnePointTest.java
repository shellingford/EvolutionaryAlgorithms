package evoalg.genotype.floatingpoint;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.mockito.Mockito;

import evoalg.random.IRandomness;

public class FloatingPointCrsOnePointTest {

  private final IRandomness random = Mockito.mock(IRandomness.class);

  @Test
  public void matesCorrectlyP1P2() {
    FloatingPoint ind1 = new FloatingPoint(Arrays.asList(7.1d, 8.8d, 10d), 5d, 10d);
    FloatingPoint ind2 = new FloatingPoint(Arrays.asList(5.5d, 8.1d, 6.66d), 5d, 10d);

    when(random.nextInt(4)).thenReturn(2);
    when(random.nextBoolean()).thenReturn(true);

    FloatingPointCrsOnePoint crs = new FloatingPointCrsOnePoint(random);
    FloatingPoint child = crs.mate(ind1, ind2);

    assertEquals(Arrays.asList(7.1d, 8.8d, 6.66d), child.getData());
  }

  @Test
  public void matesCorrectlyP2P1() {
    FloatingPoint ind1 = new FloatingPoint(Arrays.asList(7.1d, 8.8d, 10d), 5d, 10d);
    FloatingPoint ind2 = new FloatingPoint(Arrays.asList(5.5d, 8.1d, 6.66d), 5d, 10d);

    when(random.nextInt(4)).thenReturn(2);
    when(random.nextBoolean()).thenReturn(false);

    FloatingPointCrsOnePoint crs = new FloatingPointCrsOnePoint(random);
    FloatingPoint child = crs.mate(ind1, ind2);

    assertEquals(Arrays.asList(5.5d, 8.1d, 10d), child.getData());
  }

  @Test
  public void matesCorrectlyAllFromP1() {
    FloatingPoint ind1 = new FloatingPoint(Arrays.asList(7.1d, 8.8d, 10d), 5d, 10d);
    FloatingPoint ind2 = new FloatingPoint(Arrays.asList(5.5d, 8.1d, 6.66d), 5d, 10d);

    when(random.nextInt(4)).thenReturn(3);
    when(random.nextBoolean()).thenReturn(true);

    FloatingPointCrsOnePoint crs = new FloatingPointCrsOnePoint(random);
    FloatingPoint child = crs.mate(ind1, ind2);

    assertEquals(Arrays.asList(7.1d, 8.8d, 10d), child.getData());
  }

  @Test
  public void matesCorrectlyAllFromP2() {
    FloatingPoint ind1 = new FloatingPoint(Arrays.asList(7.1d, 8.8d, 10d), 5d, 10d);
    FloatingPoint ind2 = new FloatingPoint(Arrays.asList(5.5d, 8.1d, 6.66d), 5d, 10d);

    when(random.nextInt(4)).thenReturn(3);
    when(random.nextBoolean()).thenReturn(false);

    FloatingPointCrsOnePoint crs = new FloatingPointCrsOnePoint(random);
    FloatingPoint child = crs.mate(ind1, ind2);

    assertEquals(Arrays.asList(5.5d, 8.1d, 6.66d), child.getData());
  }

}
