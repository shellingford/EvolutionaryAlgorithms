package evoalg.genotype.permutation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import evoalg.genotype.CrossoverOp;

/**
 * Partially matched crossover (PMX) implementation for permutation genotype.
 *
 * Algorithm: first select two points in the permutation and then between those points (inclusive both)
 * copy data from parent1, and for other part of the data copy it from the parent2 while trying to
 * maintain parent2's order of numbers and keeping the data valid (numbers cannot repeat itself).
 *
 * For example:
 *  Parent1: 1 | 2 3 4 | 5
 *  Parent2: 5 | 4 3 2 | 1
 *
 *  Child:   5 | 2 3 4 | 1
 *
 */
public class PermutationCrxPMX extends CrossoverOp<Permutation> {

  @Override
  public Permutation mate(Permutation parent1, Permutation parent2) {
    int point1 = getRandom().nextInt(parent1.size() - 1);
    int point2 = point1 + 1 + getRandom().nextInt(parent1.size() - point1 - 1);

    List<Integer> childData = IntStream.range(0, parent1.size()).boxed().collect(Collectors.toList());
    Map<Integer, Integer> subSetMap = new HashMap<Integer, Integer>();
    for (int i = point1; i <= point2; i++) {
      childData.set(i, parent1.get(i));
      subSetMap.put(parent1.get(i), parent2.get(i));
    }

    childData = copyRemainingDataFromParent2(subSetMap, parent2, childData, point1, point2);
    return new Permutation(childData);
  }

  /**
   * Copies remaining data from parent2 while trying to maintain the order from parent2 and keeping
   * data valid.
   *
   * @param subSetMap mapped values copied from parent1
   * @param parent2 parent2
   * @param childData current child data
   * @param point1 starting point of sublist from parent1 in child
   * @param point2 ending point of sublist from parent1 in child
   * @return full child data copied from both parents
   */
  private List<Integer> copyRemainingDataFromParent2(Map<Integer, Integer> subSetMap, Permutation parent2,
      List<Integer> childData, int point1, int point2) {
    for (int i = 0; i < parent2.size(); i++) {
      if (i >= point1 && i <= point2) {
        continue;
      }
      int value = parent2.get(i);
      for (int j = 0; j < subSetMap.size(); j++) {
        if (!subSetMap.containsKey(value)) {
          break;
        }
        else {
          value = subSetMap.get(value);
        }
      }
      childData.set(i, value);
    }
    return childData;
  }
}
