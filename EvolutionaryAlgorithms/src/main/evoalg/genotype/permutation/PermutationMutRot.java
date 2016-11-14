package evoalg.genotype.permutation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import evoalg.genotype.MutationOp;
import evoalg.random.IRandomness;
/**
 * Rotation mutation operator for permutation genotype.
 *
 * Algorithm: select randomly two indices within the data list and then rotates right
 * sublist by 1.
 *
 * For example:
 *  Individual: 1 | 2 3 4 5 | 6
 *
 *  Mutated:    1 | 5 2 3 4 | 6
 */
public class PermutationMutRot extends MutationOp<Permutation> {

  public PermutationMutRot(IRandomness random) {
    super(random);
  }

  @Override
  public Permutation mutate(Permutation individual) {
    List<Integer> data = new ArrayList<>(individual.getData());
    data = rotateSublist(data);
    return new Permutation(data);
  }

  /**
   * Randomly selects two points between which we take a sublist and rotate it
   * right by 1.
   *
   * @param data individual's data
   * @return data list with rotated sublist
   */
  private List<Integer> rotateSublist(List<Integer> data) {
    int point1 = getRandom().nextInt(data.size() - 1);
    int point2 = point1 + 1 + getRandom().nextInt(data.size() - point1 - 1);

    List<Integer> sublist = data.subList(point1, point2 + 1);
    Collections.rotate(sublist, 1);

    for (int i = 0, k = point1; k <= point2; k++, i++) {
      data.set(k, sublist.get(i));
    }

    return data;
  }

}
