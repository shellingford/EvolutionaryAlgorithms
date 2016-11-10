package evoalg.genotype.permutation;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import evoalg.genotype.MutationOp;

/**
 * Simple toggle mutation for permutation genotype.
 *
 * Algorithm: select randomly two indices within the data list and replace those two numbers.
 *
 * For example:
 *  Individual: 1 2 3 4 5 6
 *  random positions to be replaced: 1, 5
 *
 *  Mutated:    1 6 3 4 5 2
 */
public class PermutationMutToggle extends MutationOp<Permutation> {

  @Override
  public Permutation mutate(Permutation individual) {
    int point1 = getRandom().nextInt(individual.size() - 1);
    int point2 = point1 + 1 + getRandom().nextInt(individual.size() - point1 - 1);
    List<Integer> mutatedData = IntStream.range(0, individual.size())
                                         .mapToObj(i -> mapInt(i, individual.getData(), point1, point2))
                                         .collect(Collectors.toList());
    return new Permutation(mutatedData);
  }

  /**
   * Copies from data list as is unless index is point1 or point2, where if it is point1 then it copies
   * number that has index point2, and vice versa.
   *
   * @param i current index within data list
   * @param data data list
   * @param point1 index where number will be replaced with number at index point2
   * @param point2 index where number will be replaced with number at index point1
   * @return number at index i, unless i is point1/point2 as then number is replaced with index point2/point1
   */
  private Integer mapInt(int i, List<Integer> data, int point1, int point2) {
    if (i == point1) {
      return data.get(point2);
    }
    if (i == point2) {
      return data.get(point1);
    }
    return data.get(i);
  }

}
