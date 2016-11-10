package evoalg.genotype.permutation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.stream.IntStream;

import org.junit.Test;

public class PermutationMutToggleTest {

  @Test
  public void correctlyMutates() {
    Permutation individual = new Permutation(5);
    PermutationMutToggle mutation = new PermutationMutToggle();

    Permutation mutated = mutation.mutate(individual);
    //as we only replace two numbers, difference must be exactly 2 numbers
    assertEquals(2, difference(individual, mutated));
    assertTrue(mutated.isValid());
  }

  private int difference(Permutation individual, Permutation mutated) {
    int equalNums = (int) IntStream.range(0, individual.size()).filter(i -> individual.get(i) == mutated.get(i)).count();
    return individual.size() - equalNums;
  }
}
