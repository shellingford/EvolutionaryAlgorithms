package evoalg.genotype.permutation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import evoalg.genotype.CrossoverOp;
import evoalg.random.IRandomness;

/**
 * Partially matched crossover (PMX) implementation for permutation genotype.
 *
 * Algorithm: two cut points are randomly selected, and the part of the first parent located between those
 * cut points is copied to the child. Then the remaining parts of the child are filled with the remaining
 * vertices so that their absolute positions are inherited as much as possible from the second parent.
 *
 * For example:
 *  Parent1: 1 2 3 | 5 4 6 7 | 8 9
 *  Parent2: 4 5 2 | 1 8 7 6 | 9 3
 *
 *  Child:   8 1 2 | 5 4 6 7 | 9 3
 *
 */
public class PermutationCrxPMX extends CrossoverOp<Permutation> {

  public PermutationCrxPMX(IRandomness random) {
    super(random);
  }

  @Override
  public Permutation mate(Permutation parent1, Permutation parent2) {
    int point1 = getRandom().nextInt(parent1.size() - 1);
    int point2 = point1 + 1 + getRandom().nextInt(parent1.size() - point1 - 1);

    Map<Integer, Integer> subSetMap = new HashMap<Integer, Integer>();
    List<Integer> childData = copyDataFromparent1(parent1, parent2, point1, point2, subSetMap);
    childData = copyRemainingDataFromParent2(subSetMap, parent2, childData, point1, point2);

    return new Permutation(childData);
  }

  /**
   * Part between two points is copied into child. Values between two points are mapped as map where
   * key is value from parent1, and value is value from parent2. That map will be used when copying
   * data from parent2 later on.
   *
   * @param parent1 parent1
   * @param parent2 parent2
   * @param point1 starting point of sublist from parent1 in child
   * @param point2 ending point of sublist from parent1 in child
   * @param subSetMap mapping of parent1 -> parent2 data between points
   * @return child data
   */
  private List<Integer> copyDataFromparent1(Permutation parent1, Permutation parent2, int point1, int point2,
      Map<Integer, Integer> subSetMap) {
    List<Integer> childData = IntStream.range(0, parent1.size()).boxed().collect(Collectors.toList());
    for (int i = point1; i <= point2; i++) {
      childData.set(i, parent1.get(i));
      subSetMap.put(parent1.get(i), parent2.get(i));
    }

    return childData;
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
