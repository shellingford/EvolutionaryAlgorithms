package evoalg.genotype.bitstring;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import evoalg.State;
import evoalg.genotype.CrossoverOp;

public class BitStringCrsUniform extends CrossoverOp<BitString> {

  public BitStringCrsUniform(State<BitString> state) {
    super(state);
  }

  @Override
  public BitString mate(BitString ind1, BitString ind2) {
    return new BitString(getState(), IntStream.range(0, ind1.size()).mapToObj(i -> mapNewByte(i, ind1, ind2))
        .collect(Collectors.toList()));
  }

  private byte mapNewByte(int i, BitString p1, BitString p2) {
    if (p1.get(i) == p2.get(i)) {
      return p1.get(i);
    }
    else {
      return (byte) getRandom().nextInt(2);
    }
  }

}
