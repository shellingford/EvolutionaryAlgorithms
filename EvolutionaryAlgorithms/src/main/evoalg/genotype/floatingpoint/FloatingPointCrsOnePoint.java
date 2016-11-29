package evoalg.genotype.floatingpoint;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import evoalg.genotype.CrossoverOp;
import evoalg.random.IRandomness;

/**
 * Single-point floatingpoint crossover operator.
 *
 *  Algorithm: first randomly select a point within floatingpoint of both parents, then randomly select if
 *  data before the point will be copied from parent1 or parent2, and after vice versa. There's 50% chance
 *  that parent1 will be used for first part of the child floatingpoint.
 *
 *  For example:
 *  Parent1: 5.5 4.9 7 | 12.3 4.4 1.1
 *  Parent2: 3.4 7.5 4 | 8.88 8.1 5.3
 *  random point: 3
 *
 *  Child:   5.5 4.9 7 | 8.88 8.1 5.3 (first part copied from p1, second from p2)
 */
public class FloatingPointCrsOnePoint extends CrossoverOp<FloatingPoint> {

  public FloatingPointCrsOnePoint(IRandomness random) {
    super(random);
  }

  @Override
  public FloatingPoint mate(FloatingPoint parent1, FloatingPoint parent2) {
    //when copying from parent we are checking 'ind < point', so to be able to copy all data from p1
    //we need to use random(size+1) so that random can return 'size' value which means for p1 it will
    //always be 'ind < value', as index goes till size-1
    int point = getRandom().nextInt(parent1.size() + 1);

    if (getRandom().nextBoolean()) {
      return new FloatingPoint(IntStream.range(0, parent1.size()).mapToObj(i -> mapNewBit(i, point, parent1, parent2))
          .collect(Collectors.toList()), parent1.getMinValue(), parent1.getMaxValue());
    }
    else {
      return new FloatingPoint(IntStream.range(0, parent1.size()).mapToObj(i -> mapNewBit(i, point, parent2, parent1))
          .collect(Collectors.toList()), parent1.getMinValue(), parent1.getMaxValue());
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
  private double mapNewBit(int i, int point, FloatingPoint p1, FloatingPoint p2) {
    if (i < point) {
      return p1.get(i);
    }
    else {
      return p2.get(i);
    }
  }
}
