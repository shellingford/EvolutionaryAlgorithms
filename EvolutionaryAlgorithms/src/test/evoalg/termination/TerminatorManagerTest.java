package evoalg.termination;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import evoalg.genotype.bitstring.BitString;
import evoalg.genotype.permutation.Permutation;

@SuppressWarnings("unchecked")
public class TerminatorManagerTest {

  @Test
  public void correctlyTerminatesSingleTermOp() {
    int generationLimit = 1000;

    TerminatorManager<Permutation> terminatorManager = new TerminatorManager<>(
        new MaxGenTermOp<Permutation>(generationLimit));

    assertTrue(terminatorManager.shouldTerminate(null, generationLimit + 1, 0));
    assertFalse(terminatorManager.shouldTerminate(null, generationLimit - 1, 0));
  }

  @Test
  public void correctlyIteratesTermOpsUntillFirstTrue() {
    final AtomicInteger callCountermaxGen = new AtomicInteger();
    ITerminationOperator<BitString> maxGenTermOp = Mockito.mock(MaxGenTermOp.class);

    final AtomicInteger callCountermaxDuration = new AtomicInteger();
    ITerminationOperator<BitString> maxDurationTermOp = Mockito.mock(MaxDurationTermOp.class);

    setupMethodCallCounter(callCountermaxGen, maxGenTermOp);
    setupMethodCallCounter(callCountermaxDuration, maxDurationTermOp);

    TerminatorManager<BitString> terminatorManager = new TerminatorManager<>(
        maxGenTermOp, maxDurationTermOp);

    assertTrue(terminatorManager.shouldTerminate(null, 0, 0));
    //as both operators return true it means only one method needs to be called, and as manager uses Set
    //there's no guaranteed order, so we check for both possible combinations
    assertTrue(callCountermaxGen.get() == 0  && callCountermaxDuration.get() == 1 ||
        callCountermaxDuration.get() == 0  && callCountermaxGen.get() == 1);
  }

  /**
   * Sets up method (shouldTerminate(...)) call counter for <code>termOp</code> and stores it
   * in <code>callCounter</code>.
   *
   * @param callCounter counter used to count how many times specified method was called
   * @param termOp termination operator for which we are counting number of calls to method shouldTerminate
   */
  private void setupMethodCallCounter(final AtomicInteger callCounter, ITerminationOperator<BitString> termOp) {
    Mockito.doAnswer(new Answer<Boolean>() {
      @Override
      public Boolean answer(InvocationOnMock invocation) throws Throwable {
        callCounter.incrementAndGet();
          return true;
      }
    }).when(termOp).shouldTerminate(null, 0, 0);
  }
}
