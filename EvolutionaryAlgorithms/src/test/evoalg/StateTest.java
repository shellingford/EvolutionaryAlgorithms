package evoalg;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mockito;

import evoalg.algorithm.Algorithm;
import evoalg.algorithm.IMilestone;
import evoalg.fitness.IEvaluate;
import evoalg.genotype.bitstring.BitString;

@SuppressWarnings("unchecked")
public class StateTest {
  private final BitString genotype = new BitString(10);
  private final Algorithm<BitString> algorithm = Mockito.mock(Algorithm.class);
  private final Population<BitString> firstGenPop = Mockito.mock(Population.class);
  private final Population<BitString> secondGenPop = Mockito.mock(Population.class);
  private final Population<BitString> thirdGenPop = Mockito.mock(Population.class);
  private final IMilestone<BitString> milestone = Mockito.mock(IMilestone.class);
  private final IEvaluate<BitString> evaluate = Mockito.mock(IEvaluate.class);
  private final SystemTime systemTime = Mockito.mock(SystemTime.class);

  @Test
  public void correctlyRunsAlgorithm() {
    setupMocks(algorithm, firstGenPop, secondGenPop, thirdGenPop, milestone, systemTime);

    State<BitString> state = new State<BitString>(genotype, algorithm, firstGenPop, milestone, evaluate, systemTime);
    state.run();

    //if it got to the point where it checks milestone with thirdGenPop then it means it executed
    //algorithm as expected
    verify(milestone).reached(thirdGenPop, 2, 0);
  }

  /**
   * Sets up all mocked method calls that are expected to be used by state when executing algorithm.
   */
  private void setupMocks(Algorithm<BitString> algorithm, Population<BitString> firstGenPop,
      Population<BitString> secondGenPop, Population<BitString> thirdGenPop, IMilestone<BitString> milestone,
      SystemTime systemTime) {
    when(firstGenPop.evaluate()).thenReturn(firstGenPop);

    when(algorithm.advanceGeneration(firstGenPop)).thenReturn(secondGenPop);
    when(algorithm.advanceGeneration(secondGenPop)).thenReturn(thirdGenPop);

    when(systemTime.currentTimeMillis()).thenReturn(0L);

    when(milestone.reached(firstGenPop, 0, 0)).thenReturn(false);
    when(milestone.reached(secondGenPop, 1, 0)).thenReturn(false);
    when(milestone.reached(thirdGenPop, 2, 0)).thenReturn(true);
  }

  @Test
  public void correctlyRestartsAlgorithm() {
    Population<BitString> popToBeReset = Mockito.mock(Population.class);
    when(popToBeReset.reset(genotype, evaluate)).thenReturn(firstGenPop);

    setupMocks(algorithm, firstGenPop, secondGenPop, thirdGenPop, milestone, systemTime);

    State<BitString> state = new State<BitString>(genotype, algorithm, firstGenPop, milestone, evaluate, systemTime);
    state.run();

    //if it got to the point where it checks milestone with thirdGenPop then it means it executed
    //algorithm as expected
    verify(milestone).reached(thirdGenPop, 2, 0);
  }
}
