package evoalg.genotype.bitstring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;

public class BitStringMutMixTest {

  @Test
  public void mutatesCorrectly() {
    List<Byte> data = Arrays.asList((byte) 0, (byte) 1);
    List<Byte> expectedMutatedData = Arrays.asList((byte) 1, (byte) 0);
    BitString genotype = new BitString(data);

    BitStringMutMix mutOp = new BitStringMutMix();

    //as mutation operator will change at least two bits, when we have only two bits it means we expect
    //to get reverse list of bits, but as it is random shuffle it can happen that we get the same list
    //again so we need to repeat the test few times to have higher chance for it to not fail
    boolean expectedRandomBehaviour = false;
    for (int i = 0; i < 5; i++) {
      BitString mutatedGenotype = mutOp.mutate(genotype);
      expectedRandomBehaviour = expectedRandomBehaviour ||
          CollectionUtils.isEqualCollection(expectedMutatedData, mutatedGenotype.getData());
    }

    assertTrue(expectedRandomBehaviour);
  }

  @Test
  public void mutatesCorrectlyRegarding01Count() {
    List<Byte> data = Arrays.asList((byte) 0, (byte) 1, (byte) 0, (byte) 1, (byte) 1, (byte) 1, (byte) 0);
    BitString genotype = new BitString(data);

    int expected0Count = 3;
    int expected1Count = 4;

    BitStringMutMix mutOp = new BitStringMutMix();
    BitString mutatedGenotype = mutOp.mutate(genotype);

    checkCounts(expected0Count, expected1Count, mutatedGenotype.getData());
  }

  /**
   * Checks if count of 0s and 1s didn't change.
   */
  private void checkCounts(int expected0Count, int expected1Count, List<Byte> data) {
    int count0s = (int) IntStream.range(0, data.size()).filter(i -> data.get(i) == (byte) 0).count();
    int count1s = (int) IntStream.range(0, data.size()).filter(i -> data.get(i) == (byte) 1).count();

    assertEquals(expected0Count, count0s);
    assertEquals(expected1Count, count1s);
  }

}
