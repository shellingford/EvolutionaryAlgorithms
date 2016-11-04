package evoalg.genotype.bitstring;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import evoalg.State;
import evoalg.genotype.CrossoverOp;

/**
 * Single-point bitstring crossover operator.
 *
 *  Algorithm: first randomly select a point within bitstring of both parents, then randomly select if
 *  data before the point will be copied from parent1 or parent2, and after vice versa. There's 50% chance
 *  that parent1 will be used for first part of the child bitstring.
 *
 *  For example:
 *  Parent1: 010 | 0101
 *  Parent2: 001 | 1111
 *
 *  Child:   0101111 (first part copied from p1, second from p2)
 */
public class BitStringCrsOnePoint extends CrossoverOp<BitString> {

  public BitStringCrsOnePoint(State<BitString> state) {
    super(state);
  }

  @Override
  public BitString mate(BitString ind1, BitString ind2) {
    int point = getRandom().nextInt(ind1.size());

    if (getRandom().nextBoolean()) {
      return new BitString(getState(), IntStream.range(0, ind1.size()).mapToObj(i -> mapNewByte(i, point, ind1, ind2))
          .collect(Collectors.toList()));
    }
    else {
      return new BitString(getState(), IntStream.range(0, ind1.size()).mapToObj(i -> mapNewByte(i, point, ind2, ind1))
          .collect(Collectors.toList()));
    }
  }

  private byte mapNewByte(int i, int point, BitString p1, BitString p2) {
    if (i < point) {
      return p1.get(i);
    }
    else {
      return p2.get(i);
    }
  }
}
