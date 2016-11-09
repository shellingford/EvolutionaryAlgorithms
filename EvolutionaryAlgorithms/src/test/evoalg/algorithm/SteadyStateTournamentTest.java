package evoalg.algorithm;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import evoalg.Deme;
import evoalg.Individual;
import evoalg.Population;
import evoalg.genotype.Crossover;
import evoalg.genotype.Mutation;
import evoalg.genotype.bitstring.BitString;
import evoalg.selection.SelRandomOp;
import evoalg.selection.SelWorstOp;

@SuppressWarnings("unchecked")
public class SteadyStateTournamentTest {

  @Test
  public void correctlyAdvancesGeneration() {
    Crossover<BitString> crossover = Mockito.mock(Crossover.class);
    Mutation<BitString> mutation = Mockito.mock(Mutation.class);
    SelRandomOp<BitString> selRandomOp = Mockito.mock(SelRandomOp.class);
    SelWorstOp<BitString> selWorstOp = Mockito.mock(SelWorstOp.class);

    Population<BitString> population = setupPopulationMock();

    List<Individual<BitString>> expectedNextGenIndividuals = setupNextGenIndividuals(population, crossover, mutation,
        selRandomOp, selWorstOp);

    int tournamentSize = 3;
    Algorithm<BitString> algorithm = new SteadyStateTournament<BitString>(mutation, crossover, tournamentSize,
        selRandomOp, selWorstOp);

    Population<BitString> newGenPop = algorithm.advanceGeneration(population);
    assertEquals(expectedNextGenIndividuals, newGenPop.getDemes().get(0).getIndividuals());
  }

  private Population<BitString> setupPopulationMock() {
    Individual<BitString> ind1 = Mockito.mock(Individual.class);
    Individual<BitString> ind2 = Mockito.mock(Individual.class);
    Individual<BitString> ind3 = Mockito.mock(Individual.class);

    Deme<BitString> deme = new Deme<BitString>(Arrays.asList(ind1, ind2, ind3));
    return new Population<BitString>(3, Arrays.asList(deme));
  }

  private List<Individual<BitString>> setupNextGenIndividuals(Population<BitString> population,
      Crossover<BitString> crossover, Mutation<BitString> mutation, SelRandomOp<BitString> selRandomOp,
      SelWorstOp<BitString> selWorstOp) {

    Individual<BitString> randomIndividual = Mockito.mock(Individual.class);
    Individual<BitString> child = Mockito.mock(Individual.class);
    Individual<BitString> mutatedChild = Mockito.mock(Individual.class);
    Individual<BitString> evaluatedChild = Mockito.mock(Individual.class);

    //random selection will always return randomIndividual
    when(selRandomOp.select(population.getDemes().get(0).getIndividuals())).thenReturn(randomIndividual);
    //after random selection there should be 3 randomIndividuals selected and of those select worst
    when(selWorstOp.select(Arrays.asList(randomIndividual, randomIndividual, randomIndividual))).thenReturn(randomIndividual);
    //mate two remaining randomIndividuals and create a new child
    when(crossover.mate(randomIndividual, randomIndividual)).thenReturn(child);
    //mutate child and get mutatedChild
    when(mutation.mutate(child)).thenReturn(mutatedChild);
    //at the end evaluate child
    when(mutatedChild.evaluate()).thenReturn(evaluatedChild);

    //at the end of the generation deme should have 3 individuals, all evaluatedChild
    return Arrays.asList(evaluatedChild, evaluatedChild, evaluatedChild);
  }
}
