package evoalg.termination;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import evoalg.genotype.permutation.Permutation;

public class MaxGenTermOpTest {

  @Test
  public void correctlyTerminates() {
    int generationLimit = 1000;
    ITerminationOperator<Permutation> termOp = new MaxGenTermOp<>(generationLimit);
    assertTrue(termOp.shouldTerminate(null, generationLimit + 1, 0));
    assertFalse(termOp.shouldTerminate(null, generationLimit - 1, 0));
  }

}
