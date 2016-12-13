package evoalg.termination;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import evoalg.genotype.permutation.Permutation;

public class MaxDurationTermOpTest {

  @Test
  public void correctlyTerminates() {
    long durationLimit = 1000;
    ITerminationOperator<Permutation> termOp = new MaxDurationTermOp<>(durationLimit);
    assertTrue(termOp.shouldTerminate(null, 0, durationLimit + 1));
    assertFalse(termOp.shouldTerminate(null, 0, durationLimit - 1));
  }
}
