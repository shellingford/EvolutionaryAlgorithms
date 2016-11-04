package evoalg.genotype.bitstring;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;
import org.mockito.Mockito;

import evoalg.State;

public class BitStringMutSimpleTest {

  @SuppressWarnings("unchecked")
  private State<BitString> state = Mockito.mock(State.class);

  @Test
  public void mutatesCorrectly() {
    List<Byte> data = IntStream.range(0, 5).mapToObj(i -> (byte) 0).collect(Collectors.toList());
    BitString genotype = new BitString(state, data);

    BitStringMutSimple mutOp = new BitStringMutSimple(state);
    BitString mutatedGenotype = mutOp.mutate(genotype);

    long countMutatedBits = IntStream.range(0, genotype.size())
                                    .filter(i -> genotype.get(i) != mutatedGenotype.get(i)).count();
    assertEquals(1, countMutatedBits);
  }
}
