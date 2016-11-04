package evoalg.genotype.bitstring;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;
import org.mockito.Mockito;

import evoalg.State;

public class BitStringMutMixTest {

  @SuppressWarnings("unchecked")
  private State<BitString> state = Mockito.mock(State.class);

  @Test
  public void mutatesCorrectly() {
    List<Byte> data = Arrays.asList((byte) 0, (byte) 1);
    List<Byte> expectedMutatedData = Arrays.asList((byte) 1, (byte) 0);
    BitString genotype = new BitString(state, data);

    BitStringMutMix mutOp = new BitStringMutMix(state);
    BitString mutatedGenotype = mutOp.mutate(genotype);

    //as mutation operator will change at least two bits, when we have only two bits it means we expect
    //to get reverse list of bits
    assertEquals(expectedMutatedData, mutatedGenotype.getData());
  }

  @Test
  public void mutatesCorrectlyRegarding01Count() {
    List<Byte> data = Arrays.asList((byte) 0, (byte) 1, (byte) 0, (byte) 1, (byte) 1, (byte) 1, (byte) 0);
    BitString genotype = new BitString(state, data);

    int expected0Count = 3;
    int expected1Count = 4;

    BitStringMutMix mutOp = new BitStringMutMix(state);
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
