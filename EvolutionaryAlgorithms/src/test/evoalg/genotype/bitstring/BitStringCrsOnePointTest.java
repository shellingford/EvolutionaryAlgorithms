package evoalg.genotype.bitstring;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

import evoalg.random.DefaultRandom;
import evoalg.random.IRandomness;

public class BitStringCrsOnePointTest {

  private final IRandomness random = new DefaultRandom();

  @Test
  public void matesCorrectlyForEqualIndividuals() {
    List<Byte> data = IntStream.range(0, 5).mapToObj(i -> (byte) 1).collect(Collectors.toList());
    BitString ind1 = new BitString(data);
    BitString ind2 = new BitString(data);

    BitStringCrsOnePoint crs = new BitStringCrsOnePoint(random);
    BitString ind3 = crs.mate(ind1, ind2);

    assertEquals(ind1.getData(), ind3.getData());
    assertEquals(ind2.getData(), ind3.getData());
  }

  @Test
  public void matesCorrectlyForDifferentIndividuals() {
    List<Byte> data1 = IntStream.range(0, 5).mapToObj(i -> (byte) 0).collect(Collectors.toList());
    BitString parent0 = new BitString(data1);
    List<Byte> data2 = IntStream.range(0, 5).mapToObj(i -> (byte) 1).collect(Collectors.toList());
    BitString parent1 = new BitString(data2);

    BitStringCrsUniform crs = new BitStringCrsUniform(random);
    Set<Byte> parentIds = new HashSet<>();
    // there is always a chance that random selection of the parent will result in
    // taking the same parent 2 times in a row, so we run the test multiple times and
    // check if at least once parent1 and parent2 will be taken for first part of the bitstring
    // random position is calculated within operator so we don't test that (for now)
    for (int i = 0; i < 5; i++) {
      BitString ind3 = crs.mate(parent0, parent1);
      //parent0 is only 0s, and parent1 is only 1s, so if first bit is 0 it means that it has taken
      //bits from parent0, and 1 if it was from parent1
      parentIds.add(ind3.getData().get(0));
    }

    Set<Byte> expectedParentIds = new HashSet<>(Arrays.asList((byte) 0, (byte) 1));
    assertEquals(expectedParentIds, parentIds);
  }

}
