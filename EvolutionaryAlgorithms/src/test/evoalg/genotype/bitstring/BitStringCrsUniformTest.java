package evoalg.genotype.bitstring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.mockito.Mockito;

import evoalg.State;

public class BitStringCrsUniformTest {

  @Test
  public void matesCorrectlyForEqualIndividuals() {
    State state = Mockito.mock(State.class);

    List<Byte> data = IntStream.range(0, 5).mapToObj(i -> (byte) 1).collect(Collectors.toList());
    BitString ind1 = new BitString(state, data);
    BitString ind2 = new BitString(state, data);

    BitStringCrsUniform crs = new BitStringCrsUniform(state);
    BitString ind3 = crs.mate(ind1, ind2);

    assertEquals(ind1.getData(), ind3.getData());
    assertEquals(ind2.getData(), ind3.getData());
  }

  @Test
  public void matesCorrectlyForDifferentIndividuals() {
    State state = Mockito.mock(State.class);

    List<Byte> data = IntStream.range(0, 5).mapToObj(i -> (byte) 1).collect(Collectors.toList());
    BitString ind1 = new BitString(state, data);
    List<Byte> data2 = IntStream.range(0, 5).mapToObj(i -> (byte) 0).collect(Collectors.toList());
    BitString ind2 = new BitString(state, data2);

    BitStringCrsUniform crs = new BitStringCrsUniform(state);
    boolean indMutated = false;
    // there is always a chance that random mutation 0/1 will result in previous
    // value, so we run it multiple times to have much lower chance for that to
    // happen
    for (int i = 0; i < 3; i++) {
      BitString ind3 = crs.mate(ind1, ind2);
      indMutated = indMutated || !CollectionUtils.isEqualCollection(ind1.getData(), ind3.getData());
      indMutated = indMutated || !CollectionUtils.isEqualCollection(ind2.getData(), ind3.getData());
    }

    assertTrue(indMutated);
  }
}
