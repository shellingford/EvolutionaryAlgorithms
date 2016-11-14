package evoalg.genotype.permutation;

import java.util.ArrayList;
import java.util.List;

import evoalg.genotype.MutationOp;
import evoalg.random.IRandomness;

/**
 * Inversion mutation operator for permutation genotype.
 *
 * Algorithm: select randomly two indices within the data list and replace first and last
 * elements of the sublist, second and second last and so on.
 *
 * For example:
 *  Individual: 1 | 2 3 4 5 6 |
 *
 *  Mutated:    1 | 6 5 4 3 2 |
 */
public class PermutationMutInv extends MutationOp<Permutation> {

  public PermutationMutInv(IRandomness random) {
    super(random);
  }

  @Override
  public Permutation mutate(Permutation individual) {
    int point1 = getRandom().nextInt(individual.size() - 1);
    int point2 = point1 + 1 + getRandom().nextInt(individual.size() - point1 - 1);

    List<Integer> newData = invertSublist(new ArrayList<>(individual.getData()), point1, point2);
    return new Permutation(newData);
  }

  /**
   * Inverts sublist from the newData between indices point1 and point2 as explained
   * by the algorithm.
   *
   * @param newData individual's data list
   * @param point1 left point of the sublist
   * @param ind2 right index of the sublist
   * @return data list with inverted sublist
   */
  private List<Integer> invertSublist(List<Integer> newData, int point1, int point2) {
    int tmp;
    int distance = point2 - point1;

    for (int i = point1; i <= (distance + point1) / 2; i++) {
      tmp = newData.get(i);
      newData.set(i, newData.get(point1 + point2 - i));
      newData.set(point1 + point2 - i, tmp);
    }
    return newData;
  }

}
