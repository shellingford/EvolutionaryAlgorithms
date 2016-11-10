package evoalg.genotype.permutation;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class PermutationCrxPMXTest {

  @Test
  public void correctlyMates() {
    Permutation parent1 = new Permutation(Arrays.asList(1, 2, 3, 4, 5));
    Permutation parent2 = new Permutation(Arrays.asList(5, 4, 3, 2, 1));

    PermutationCrxPMX pmx = new PermutationCrxPMX();
    Permutation child = pmx.mate(parent1, parent2);
    assertTrue(child.isValid());

    checkIntPermutations(parent1, parent2, child);
  }

  /**
   * Checks if permutation data (numbers) are correctly copied from parent1 and parent2.
   *
   * @param parent1 parent that child copied sublist data between two random points from
   * @param parent2 parent that child copied remaining numbers outside random points from
   * @param child child
   */
  private void checkIntPermutations(Permutation parent1, Permutation parent2, Permutation child) {
    boolean foundSubList = false;
    int point1 = -1;
    int point2 = -1;
    Set<Integer> usedInts = new HashSet<>();
    //find sublist within child from parent1
    for (int i = 0; i < parent1.size() - 1; i++) {
      //at least two elements must be equal for a sublist
      if (child.get(i) == parent1.get(i) && child.get(i + 1) == parent1.get(i + 1)) {
        usedInts.add(child.get(i));
        point1 = i;
        foundSubList = true;
        for (int j = i + 1; j < parent1.size(); j++) {
          if (child.get(j) == parent1.get(j)) {
            point2 = j;
            usedInts.add(child.get(j));
          }
        }
      }
      if (foundSubList) {
        break;
      }
    }
    assertTrue(foundSubList);

    int remainingNumCount = parent2.size() - usedInts.size();
    checkRemainingIntsFromP2(child, parent2, remainingNumCount, point1, point2);
  }

  /**
   * After copying sublist from parent1, child should copy all other numbers from parent2 in
   * the order it is in parent2.
   *
   * @param child child
   * @param parent2 parent2
   * @param remainingNumCount remaining count of numbers we must find from parent2
   * @param point1 starting point of sublist from parent1 in child
   * @param point2 ending point of sublist from parent1 in child
   */
  private void checkRemainingIntsFromP2(Permutation child, Permutation parent2, int remainingNumCount, int point1,
      int point2) {
    int chInd = 0;
    int p2Ind = 0;
    while (chInd < child.size() && p2Ind < parent2.size() && remainingNumCount > 0) {
      if (chInd >= point1 && chInd <= point2) {
        chInd = point2 + 1;
      }
      if (child.get(chInd) != parent2.get(p2Ind)) {
        p2Ind++;
      }
      else {
        chInd++;
        p2Ind++;
        remainingNumCount--;
      }
    }

    //found all remaining integers from parent2 in expected order
    assertTrue(remainingNumCount == 0);
  }
}
