package evoalg.genotype.bitstring;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import evoalg.genotype.CrossoverOp;
import evoalg.random.IRandomness;

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
 *  random point: 3
 *
 *  Child:   010 | 1111 (first part copied from p1, second from p2)
 */
public class BitStringCrsOnePoint extends CrossoverOp<BitString> {

  public BitStringCrsOnePoint(IRandomness random) {
    super(random);
  }

  @Override
  public BitString mate(BitString parent1, BitString parent2) {
    //when copying from parent we are checking 'ind < point', so to be able to copy all data from p1
    //we need to use random(size+1) so that random can return 'size' value which means for p1 it will
    //always be 'ind < value', as index goes till size-1
    int point = getRandom().nextInt(parent1.size() + 1);

    if (getRandom().nextBoolean()) {
      return new BitString(IntStream.range(0, parent1.size()).mapToObj(i -> mapNewByte(i, point, parent1, parent2))
          .collect(Collectors.toList()));
    }
    else {
      return new BitString(IntStream.range(0, parent1.size()).mapToObj(i -> mapNewByte(i, point, parent2, parent1))
          .collect(Collectors.toList()));
    }
  }

  /**
   * If bit index is before specified point then return first parent's bit, otherwise
   * return second parent's bit.
   *
   * @param i current index of a bit
   * @param point specified point where we check which parent's bit we should take
   * @param p1 parent
   * @param p2 parent
   * @return child's bit
   */
  private byte mapNewByte(int i, int point, BitString p1, BitString p2) {
    if (i < point) {
      return p1.get(i);
    }
    else {
      return p2.get(i);
    }
  }
}
