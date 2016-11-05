package evoalg.genotype.bitstring;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

public class BitStringMutSimpleTest {

  @Test
  public void mutatesCorrectly() {
    List<Byte> data = IntStream.range(0, 5).mapToObj(i -> (byte) 0).collect(Collectors.toList());
    BitString genotype = new BitString(data);

    BitStringMutSimple mutOp = new BitStringMutSimple();
    BitString mutatedGenotype = mutOp.mutate(genotype);

    long countMutatedBits = IntStream.range(0, genotype.size())
                                    .filter(i -> genotype.get(i) != mutatedGenotype.get(i)).count();
    assertEquals(1, countMutatedBits);
  }
}
