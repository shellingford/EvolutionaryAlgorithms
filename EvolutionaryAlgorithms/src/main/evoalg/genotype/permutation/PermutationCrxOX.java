package evoalg.genotype.permutation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import evoalg.genotype.CrossoverOp;
import evoalg.random.IRandomness;

/**
 * Partially matched crossover (PMX) implementation for permutation genotype.
 *
 * Algorithm: two cut points are randomly selected, and the part of the first parent located between those
 * cut points is copied to the child. The remaining positions in the child are then filled one at a time,
 * starting after the second cut point, by considering the customer vertices in order found in the second
 * parent (wrapping around when the end of the list is reached)
 *
 * For example:
 *  Parent1: 1 2 3 | 5 4 6 7 | 8 9
 *  Parent2: 4 5 2 | 1 8 7 6 | 9 3
 *
 *  Child:   2 1 8 | 5 4 6 7 | 9 3
 *
 */
public class PermutationCrxOX extends CrossoverOp<Permutation> {

  public PermutationCrxOX(IRandomness random) {
    super(random);
  }

  @Override
  public Permutation mate(Permutation parent1, Permutation parent2) {
    int point1 = getRandom().nextInt(parent1.size() - 1);
    int point2 = point1 + 1 + getRandom().nextInt(parent1.size() - point1 - 1);

    Set<Integer> usedInts = new HashSet<>();
    List<Integer> childData = copyDataFromparent1(parent1, parent2, point1, point2, usedInts);
    childData = copyRemainingDataFromParent2(usedInts, parent2, childData, point1, point2);

    return new Permutation(childData);
  }

  /**
   * Part between two points is copied into child. Values copied from parent1 are added
   * to set usedInts so we can check faster later on which integers from parent2 we can
   * copy.
   *
   * @param parent1 parent1
   * @param parent2 parent2
   * @param point1 starting point of sublist from parent1 in child
   * @param point2 ending point of sublist from parent1 in child
   * @param usedInts set where we store data copied from parent1
   * @return child data
   */
  private List<Integer> copyDataFromparent1(Permutation parent1, Permutation parent2, int point1, int point2,
      Set<Integer> usedInts) {
    List<Integer> childData = IntStream.range(0, parent1.size()).mapToObj(i -> -1).collect(Collectors.toList());
    for (int i = point1; i <= point2; i++) {
      childData.set(i, parent1.get(i));
      usedInts.add(parent1.get(i));
    }

    return childData;
  }

  /**
   * Copies remaining data from parent2 while trying to maintain the order from parent2 and keeping
   * data valid.
   *
   * @param usedInts values copied from parent1 that cannot be copied from parent2
   * @param parent2 parent2
   * @param childData current child data
   * @param point1 starting point of sublist from parent1 in child
   * @param point2 ending point of sublist from parent1 in child
   * @return full child data copied from both parents
   */
  private List<Integer> copyRemainingDataFromParent2(Set<Integer> usedInts, Permutation parent2,
      List<Integer> childData, int point1, int point2) {
    List<Integer> filteredParent2Data = parent2.getData().stream()
                                               .filter(elem -> !usedInts.contains(elem))
                                               .collect(Collectors.toList());
    int p2Ind = 0;
    for (int i = 0; i < childData.size(); i++) {
      if (i >= point1 && i <= point2) {
        continue;
      }
      childData.set(i, filteredParent2Data.get(p2Ind));
      p2Ind++;
    }
    return childData;
  }
}
