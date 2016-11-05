package evoalg.genotype.bitstring;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import evoalg.genotype.CrossoverOp;

/**
 *  Bitstring crossover uniform operator.
 *
 *  Algorithm: check every bit by bit of parents, and if both parents have the same value
 *  at the same space then copy that value, otherwise choose a random bit value.
 *
 *  For example:
 *  Parent1: 0100101
 *  Parent2: 0011111
 *
 *  Child:   0001101 (same parent values are copied, others are random)
 */
public class BitStringCrsUniform extends CrossoverOp<BitString> {

  @Override
  public BitString mate(BitString ind1, BitString ind2) {
    return new BitString(IntStream.range(0, ind1.size()).mapToObj(i -> mapNewByte(i, ind1, ind2))
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
