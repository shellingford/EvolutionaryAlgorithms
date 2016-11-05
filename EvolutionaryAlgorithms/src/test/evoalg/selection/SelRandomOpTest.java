package evoalg.selection;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.mockito.Mockito;

import evoalg.Individual;
import evoalg.genotype.bitstring.BitString;

public class SelRandomOpTest {

  @SuppressWarnings("unchecked")
  @Test
  public void correctlySelectsRandomIndividual() {
    Individual<BitString> individual1 = Mockito.mock(Individual.class);
    Individual<BitString> individual2 = Mockito.mock(Individual.class);
    Individual<BitString> individual3 = Mockito.mock(Individual.class);
    List<Individual<BitString>> individuals = Arrays.asList(individual1, individual2, individual3);

    SelRandomOp<BitString> selector = new SelRandomOp<BitString>();
    Set<Individual<BitString>> selected = new HashSet<>();
    //as selection is random, we test a few times to increase probability of different selections
    for (int i = 0; i < 5; i++) {
      selected.add(selector.select(individuals));
    }

    assertTrue(selected.size() >= 2);
  }

}
